package org.jenkins.plugins.statistics.gatherer.listeners;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hudson.EnvVars;
import hudson.Extension;
import hudson.PluginWrapper;
import hudson.model.*;
import hudson.model.listeners.RunListener;
import hudson.triggers.SCMTrigger;
import hudson.triggers.TimerTrigger;
import jenkins.YesNoMaybe;
import jenkins.model.Jenkins;
import org.jenkins.plugins.statistics.gatherer.model.build.BuildStats;
import org.jenkins.plugins.statistics.gatherer.model.stage.StageStats;
import org.jenkins.plugins.statistics.gatherer.util.Constants;
import org.jenkins.plugins.statistics.gatherer.util.JsonUtil;
import org.jenkins.plugins.statistics.gatherer.util.PropertyLoader;
import org.jenkins.plugins.statistics.gatherer.util.RestClientUtil;
import org.jenkinsci.plugins.workflow.cps.replay.ReplayCause;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * provides status (pending, error or success) and timing information for each build.
 * @author jasper
 */
@Extension(dynamicLoadable = YesNoMaybe.YES)
public class BuildStatsListener extends RunListener<Run<?, ?>> {

    private static final Logger LOGGER = Logger.getLogger(BuildStatsListener.class.getName());

    @Override
    public void onStarted(Run<?, ?> run, TaskListener listener) {
        if (!PropertyLoader.getBuildInfo()) {
            return;
        }
        BuildStatsAction buildStatsAction = new BuildStatsAction(run.getParent(), run);
        BuildStats buildStats = buildStatsAction.getBuildStats();
        buildStats.setRootUrl(Jenkins.get().getRootUrl());
        buildStats.setBuildUrl(run.getUrl());

        buildStats.setJobName(run.getParent().getName());
        buildStats.setFullJobName(run.getParent().getFullName());
        buildStats.setBuildNumber(run.getNumber());

        buildStats.setBuildCause(run.getCauses().get(0).getShortDescription());
        buildStats.setQueueTime(run.getExecutor() != null ? run.getExecutor().getTimeSpentInQueue() : 0);
        buildStats.setStartTime(run.getTime());

        addUserDetails(run, buildStats);
        addScmInfo(run, listener, buildStats);
        addParameters(run, buildStats);
        addSlaveInfo(run, buildStats, listener);

        run.addAction(buildStatsAction);
    }

    /**
     * Update the build status and duration.
     */
    @Override
    public void onFinalized(final Run<?, ?> run) {
        if (!PropertyLoader.getBuildInfo()) {
            return;
        }
        try {
            BuildStatsAction buildStatusAction = run.getAction(BuildStatsAction.class);
            BuildStats buildStats = buildStatusAction.getBuildStats();

            buildStats.setResult(run.getResult().toString());
            buildStats.setDuration(run.getDuration());
            Date endTime = new Date(run.getTimeInMillis() + run.getDuration());
            buildStats.setEndTime(endTime);
            addBuildFailureCauses(buildStats);
            buildStatusAction.updateBuildStatusForJob(StageStats.State.fromResult(run.getResult()));

            RestClientUtil.postToService(getRestUrl(), buildStats);
            LOGGER.log(Level.FINE, run.getExternalizableId() + " ended at " + endTime);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Failed to call API " + getRestUrl() + " for build " + run.getDisplayName(), e);
        }
    }

    /**
     * Construct REST URL for build resource.
     */
    private String getRestUrl() {
        return PropertyLoader.getBuildEndPoint();
    }

    /**
     * Get environment variable in the build.
     */
    private EnvVars getEnvVars(Run<?, ?> run, TaskListener listener) {
        EnvVars environment = null;
        try {
            environment = run.getEnvironment(listener);
        } catch (IOException | InterruptedException e) {
            LOGGER.log(Level.WARNING, "Failed to retrieve environment for " + run.getUrl(), e);
        }
        return environment;
    }

    /**
     * Add the slave info to build model.
     */
    private void addSlaveInfo(Run<?, ?> run, BuildStats build, TaskListener listener) {
        BuildStats.SlaveInfo slaveInfo = new BuildStats.SlaveInfo();
        EnvVars environment = getEnvVars(run, listener);
        if (environment != null) {
            if (environment.get(Constants.NODE_NAME) != null) {
                slaveInfo.setSlaveName(environment.get(Constants.NODE_NAME));
            }
            if (environment.get(Constants.NODE_LABELS) != null) {
                slaveInfo.setLabel(environment.get(Constants.NODE_LABELS));
            }
            if (environment.get(Constants.EXECUTOR_NUMBER) != null) {
                slaveInfo.setExecutor(environment.get(Constants.EXECUTOR_NUMBER));
            }
        }
        build.setSlaveInfo(slaveInfo);
    }

    /**
     * Get all the parameters used in the build.
     */
    private void addParameters(Run<?, ?> run, BuildStats build) {
        ParametersAction paramsAction = run.getAction(ParametersAction.class);
        if (paramsAction != null) {
            EnvVars env = new EnvVars();
            for (ParameterValue value : paramsAction.getParameters()) {
                if (!value.isSensitive()) {
                    value.buildEnvironment(run, env);
                }
            }
            build.setParameters(env);
        }
    }

    /**
     * Get the SCM info from the build and add it to model.
     */
    private void addScmInfo(Run<?, ?> run, TaskListener listener, BuildStats build) {
        EnvVars environment = getEnvVars(run, listener);
        if (environment != null) {
            BuildStats.ScmInfo scmInfo = new BuildStats.ScmInfo();
            if (environment.get(Constants.GITLAB_ACTION) != null) {
                scmInfo.setAction(environment.get(Constants.GITLAB_ACTION).toLowerCase());
            }

            if (environment.get(Constants.GITLAB_URL) != null) {
                scmInfo.setUrl(environment.get(Constants.GITLAB_URL));
            }

            if (environment.get(Constants.GITLAB_BRANCH) != null) {
                scmInfo.setBranch(environment.get(Constants.GITLAB_BRANCH));
            }

            if (environment.get(Constants.GITLAB_COMMIT) != null) {
                scmInfo.setCommit(environment.get(Constants.GITLAB_COMMIT));
            }

            if (environment.get(Constants.GITLAB_USERNAME) != null) {
                scmInfo.setAuthor(environment.get(Constants.GITLAB_USERNAME));
            }
            build.setScmInfo(scmInfo);
        }
    }

    /**
     * Add user name and user id details to buildStats model.
     */
    private void addUserDetails(Run<?, ?> run, BuildStats buildStats) {
        List<Cause> causes = run.getCauses();
        for (Cause cause : causes) {
            if (cause instanceof Cause.UserIdCause) {
                String userId = ((Cause.UserIdCause) cause).getUserId();
                userId = (userId == null || userId.isEmpty()) ? Constants.ANONYMOUS : userId;
                buildStats.setStartedUserId(userId);
                String userName = ((Cause.UserIdCause) cause).getUserName();
                userName = (userName == null || userName.isEmpty()) ? Constants.ANONYMOUS : userName;
                buildStats.setStartedUserName(userName);
            } else if (cause instanceof Cause.UpstreamCause) {
                buildStats.setStartedUserId(Constants.UPSTREAM);
                buildStats.setStartedUserName(Constants.SYSTEM);
            } else if (cause instanceof SCMTrigger.SCMTriggerCause) {
                buildStats.setStartedUserId(Constants.SCM);
                buildStats.setStartedUserName(Constants.SYSTEM);
            } else if (cause instanceof TimerTrigger.TimerTriggerCause) {
                buildStats.setStartedUserId(Constants.TIMER);
                buildStats.setStartedUserName(Constants.SYSTEM);
            } else if (cause instanceof ReplayCause) {
                buildStats.setStartedUserId(Constants.REPLAY);
                buildStats.setStartedUserName(Constants.SYSTEM);
            } else {
                buildStats.setStartedUserId(Constants.UNKNOWN);
                buildStats.setStartedUserName(Constants.SYSTEM);
            }
        }
    }

    /**
     * Add build failure causes to buildStats model
     */
    private void addBuildFailureCauses(BuildStats buildStats) {
        List<PluginWrapper> plugins = Jenkins.get().getPluginManager().getPlugins();
        for (PluginWrapper plugin : plugins) {
            if (plugin.getDisplayName().contains(Constants.BUILD_FAILURE_ANALYZER_PLUGIN)) {
                String url = buildStats.getRootUrl() + buildStats.getBuildUrl() + Constants.BUILD_FAILURE_URL_TO_APPEND;
                JSONObject response = RestClientUtil.getJson(url);
                if (response != null && response.containsKey("actions")) {
                    JSONArray actions = response.getJSONArray("actions");
                    for (int i = 0; i < actions.size(); i++) {
                        JSONObject failureResponse = actions.getJSONObject(i);
                        if (!failureResponse.keySet().isEmpty()) {
                            if (failureResponse.containsKey(Constants.FOUND_FAILURE_CAUSES)) {
                                List<Map<String, Object>> failureCauses = new ArrayList<>();
                                for (int j = 0; j < failureResponse.getJSONArray(Constants.FOUND_FAILURE_CAUSES).size(); j++) {
                                    JSONArray foundFailureCauses = failureResponse.getJSONArray(Constants.FOUND_FAILURE_CAUSES);
                                    Map<String, Object> jsonObject = JsonUtil.convertBuildFailureToMap(foundFailureCauses.getJSONObject(j));
                                    failureCauses.add(jsonObject);
                                }
                                buildStats.setBuildFailureCauses(failureCauses);
                            }
                        }
                    }
                    break;
                }
            }
        }
    }

}
