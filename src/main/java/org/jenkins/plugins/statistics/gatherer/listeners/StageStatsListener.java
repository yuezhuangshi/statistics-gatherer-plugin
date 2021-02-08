package org.jenkins.plugins.statistics.gatherer.listeners;

import hudson.Extension;
import hudson.model.Queue;
import hudson.model.Result;
import hudson.model.Run;
import jenkins.YesNoMaybe;
import org.jenkins.plugins.statistics.gatherer.model.stage.StageStats;
import org.jenkins.plugins.statistics.gatherer.util.PropertyLoader;
import org.jenkinsci.plugins.pipeline.StageStatus;
import org.jenkinsci.plugins.pipeline.modeldefinition.actions.ExecutionModelAction;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTParallel;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTStage;
import org.jenkinsci.plugins.pipeline.modeldefinition.ast.ModelASTStages;
import org.jenkinsci.plugins.workflow.actions.*;
import org.jenkinsci.plugins.workflow.cps.nodes.StepAtomNode;
import org.jenkinsci.plugins.workflow.cps.nodes.StepEndNode;
import org.jenkinsci.plugins.workflow.flow.FlowExecution;
import org.jenkinsci.plugins.workflow.flow.GraphListener;
import org.jenkinsci.plugins.workflow.graph.FlowNode;
import org.jenkinsci.plugins.workflow.graphanalysis.DepthFirstScanner;
import org.jenkinsci.plugins.workflow.steps.FlowInterruptedException;

import javax.annotation.CheckForNull;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * provides status (pending, error or success) and timing information for each stage in a build.
 * @author jasper
 */
@Extension(dynamicLoadable = YesNoMaybe.YES)
public class StageStatsListener implements GraphListener {

    private static final Logger LOGGER = Logger.getLogger(StageStatsListener.class.getName());

    /**
     * Evaluates if we can provide stats on a node.
     *
     * @param fn a node in workflow
     */
    @Override
    public void onNewHead(FlowNode fn) {
        if (!PropertyLoader.getBuildInfo()) {
            return;
        }

        if (isStage(fn)) {
            checkEnableBuildStatus(fn);
        } else if (fn instanceof StepAtomNode && !isDeclarativePipelineJob(fn)) {
            // We don't need to look at atom nodes for declarative pipeline jobs, because
            // they have a nice model containing all the stages

            ErrorAction errorAction = fn.getError();

            if (errorAction == null) {
                return;
            }

            List<? extends FlowNode> enclosingBlocks = fn.getEnclosingBlocks();
            boolean isInStage = false;

            for (FlowNode enclosingNode : enclosingBlocks) {
                if (isStage(enclosingNode)) {
                    isInStage = true;
                }
            }

            if (isInStage) {
                return;
            }

            // We have a non-declarative atom that isn't in a stage, which has failed.
            // Since normal processing is via stages, we'd normally miss this failure;
            // send an out of band error notification to make sure it's recorded by any
            // interested notifiers
            checkEnableBuildStatus(fn);
            BuildStatsAction buildStatsAction = buildStatusActionFor(fn.getExecution());
            buildStatsAction.addNonStageError(errorAction.getDisplayName(), StageStats.State.CompletedError);
        } else if (fn instanceof StepEndNode) {
            BuildStatsAction buildStatsAction = buildStatusActionFor(fn.getExecution());

            FlowNode startNode = ((StepEndNode) fn).getStartNode();

            if (!isStage(startNode)) {
                return;
            }

            String nodeName;
            long time = getTime(startNode, fn);
            LabelAction label = startNode.getAction(LabelAction.class);

            if (label != null) {
                nodeName = label.getDisplayName();
                if (nodeName != null) {
                    StageStats.State buildState = buildStateForStage(startNode, fn);
                    buildStatsAction.updateStageStats(nodeName, buildState, time);
                } else {
                    log(Level.WARNING, "Unexpected empty label for %s", startNode.getDisplayName());
                }
            }
        }
    }

    /**
     * Checks whether the current build meets our requirements for providing
     * status, and adds a BuildStatusAction to the build if so.
     *
     * @param flowNode node of a workflow
     */
    private void checkEnableBuildStatus(FlowNode flowNode) {
        FlowExecution exec = flowNode.getExecution();
        try {
            BuildStatsAction buildStatusAction = buildStatusActionFor(exec);

            Run<?, ?> run = runFor(exec);
            if (null == run) {
                log(Level.WARNING, "Could not find Run - status will not be provided build");
                return;
            }

            // Declarative pipeline jobs come with a nice execution model, which allows you
            // to get all of the stages at once at the beginning of the job.
            // Older scripted pipeline jobs do not, so we have to add them one at a
            // time as we discover them.
            List<StageStats> stages = getDeclarativeStages(run);
            boolean isDeclarativePipeline = stages != null;

            if (stages == null) {
                List<StageStats> stageStatsList = new ArrayList<>();
                stageStatsList.add(new StageStats(flowNode.getDisplayName()));
                stages = stageStatsList;
            }

            if (buildStatusAction.getBuildStats().getStages() == null) {
                Map<String, StageStats> map = stages.stream()
                    .collect(Collectors.toMap(StageStats::getName, v -> v));
                buildStatusAction.getBuildStats().setStages(map);
                buildStatusAction.setIsDeclarativePipeline(isDeclarativePipeline);
            } else {
                buildStatusAction.addStageStats(flowNode.getDisplayName(), StageStats.State.Pending);
            }
        } catch (Exception ex) {
            try {
                exec.getOwner().getListener().getLogger().println(ex.toString());
            } catch (IOException ex1) {
                log(Level.SEVERE, null, ex1);
            }
            log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Determines the appropriate state for a stage
     *
     * @param startNode The stage start node.
     * @param endNode THe stage end node.
     * @return Stage state
     */
    private StageStats.State buildStateForStage(FlowNode startNode, FlowNode endNode) {
        StageStats.State buildState = StageStats.State.CompletedSuccess;
        TagsAction tags = endNode.getAction(TagsAction.class);

        ErrorAction errorAction = endNode.getError();
        if (errorAction != null) {
            Result result;
            if (errorAction.getError() instanceof FlowInterruptedException) {
                result = ((FlowInterruptedException) errorAction.getError()).getResult();
            } else {
                result = Result.FAILURE;
            }
            buildState = StageStats.State.fromResult(result);
        }
        else if (tags != null) {
            String status = tags.getTagValue(StageStatus.TAG_NAME);
            if (status != null) {
                if (status.equals(StageStatus.getSkippedForFailure())) {
                    return StageStats.State.SkippedFailure;
                } else if (status.equals(StageStatus.getSkippedForUnstable())) {
                    return StageStats.State.SkippedUnstable;
                } else if (status.equals(StageStatus.getSkippedForConditional())) {
                    return StageStats.State.SkippedConditional;
                } else if (status.equals(StageStatus.getFailedAndContinued())) {
                    return StageStats.State.CompletedError;
                }
            }
        } else {
            Result result = resultForStage(startNode, endNode);
            buildState = StageStats.State.fromResult(result);
        }
        return buildState;

    }

    private @CheckForNull BuildStatsAction buildStatusActionFor(FlowExecution exec) {
        BuildStatsAction buildStatusAction = null;
        Run<?, ?> run = runFor(exec);
        if (run != null) {
            buildStatusAction = run.getAction(BuildStatsAction.class);
        }
        return buildStatusAction;
    }

    private Result resultForStage(FlowNode startNode, FlowNode endNode) {
        Result errorResult = Result.SUCCESS;
        DepthFirstScanner scanner = new DepthFirstScanner();
        if (scanner.setup(endNode, Collections.singletonList(startNode))) {
            WarningAction warningAction = StreamSupport.stream(scanner.spliterator(), false)
                .map(node -> node.getPersistentAction(WarningAction.class))
                .filter(Objects::nonNull)
                .max(Comparator.comparing(warning -> warning.getResult().ordinal))
                .orElse(null);
            if (warningAction != null) {
                errorResult = warningAction.getResult();
            }
        }
        return errorResult;
    }

    /**
     * Gets the execution time of a block defined by startNode and endNode.
     *
     * @param startNode startNode of a block
     * @param endNode endNode of a block
     * @return Execution time of the block
     */
    private long getTime(FlowNode startNode, FlowNode endNode) {
        TimingAction startTime = startNode.getAction(TimingAction.class);
        TimingAction endTime = endNode.getAction(TimingAction.class);

        if (startTime != null && endTime != null) {
            return endTime.getStartTime() - startTime.getStartTime();
        }
        return 0;
    }

    /**
     * Determines if a {@link FlowNode} describes a stage.
     *
     * Note: this check is copied from PipelineNodeUtil.java in blueocean-plugin
     *
     * @param node node of a workflow
     * @return true if it's a stage node; false otherwise
     */
    private boolean isStage(FlowNode node) {
        if (node instanceof StepAtomNode) {
            // This filters out labelled steps, such as `sh(script: "echo 'hello'", label: 'echo')`
            return false;
        }
        return node != null && ((node.getAction(StageAction.class) != null)
            || (node.getAction(LabelAction.class) != null && node.getAction(ThreadNameAction.class) == null));
    }

    /**
     * Determines if the node belongs to a declarative pipeline.
     *
     * @param fn node of a workflow
     * @return true/false
     */
    private boolean isDeclarativePipelineJob(FlowNode fn) {
        Run<?, ?> run = runFor(fn.getExecution());
        if (run == null) {
            return false;
        }
        return getDeclarativeStages(run) != null;
    }

    /**
     * Gets a list of stages in a declarative pipeline.
     *
     * @param run a particular run of a job
     * @return a list of stage names
     */
    private List<StageStats> getDeclarativeStages(Run<?, ?> run) {
        ExecutionModelAction executionModelAction = run.getAction(ExecutionModelAction.class);
        if (null == executionModelAction) {
            return null;
        }
        ModelASTStages stages = executionModelAction.getStages();
        if (null == stages) {
            return null;
        }
        List<ModelASTStage> stageList = stages.getStages();
        if (null == stageList) {
            return null;
        }

        List<StageStats> result = new ArrayList<>();
        for (ModelASTStage stage : stageList) {
            for (String stageName : getAllStageNames(stage)) {
                result.add(new StageStats(stageName, StageStats.State.Pending));
            }
        }
        return result;
    }

    /**
     * Gets the BuildStatusAction object for the specified executing workflow.
     *
     * Returns a list containing the stage name and names of all nested stages.
     *
     * @param stage The ModelASTStage object
     * @return List of stage names
     */
    private static List<String> getAllStageNames(ModelASTStage stage) {
        List<String> stageNames = new ArrayList<>();
        stageNames.add(stage.getName());
        List<ModelASTStage> stageList = null;
        if (stage.getStages() != null) {
            stageList = stage.getStages().getStages();
        } else {
            ModelASTParallel stageModel = stage.getParallel();
            if (stageModel != null) {
                stageList = stageModel.getStages();
            }
        }
        if (stageList != null) {
            for (ModelASTStage innerStage : stageList) {
                stageNames.addAll(getAllStageNames(innerStage));
            }
        }
        return stageNames;
    }


    /**
     * Gets the jenkins run object of the specified executing workflow.
     *
     * @param exec execution of a workflow
     * @return jenkins run object of a job
     */
    private @CheckForNull Run<?, ?> runFor(FlowExecution exec) {
        Queue.Executable executable;
        try {
            executable = exec.getOwner().getExecutable();
        } catch (IOException x) {
            log(Level.WARNING, null, x);
            return null;
        }
        if (executable instanceof Run) {
            return (Run<?, ?>) executable;
        } else {
            return null;
        }
    }

    /**
     * Prints to stdout or stderr.
     *
     * @param level INFO/WARNING/ERROR
     * @param format String that formats the log
     * @param args arguments for the formatted log string
     */
    private void log(Level level, String format, Object... args) {
        LOGGER.log(level, String.format(format, args));
    }

}
