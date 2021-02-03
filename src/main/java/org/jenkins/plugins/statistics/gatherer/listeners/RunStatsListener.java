package org.jenkins.plugins.statistics.gatherer.listeners;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hudson.EnvVars;
import hudson.Extension;
import hudson.Launcher;
import hudson.PluginWrapper;
import hudson.model.*;
import hudson.model.listeners.RunListener;
import hudson.triggers.SCMTrigger;
import hudson.triggers.TimerTrigger;
import jenkins.YesNoMaybe;
import jenkins.model.Jenkins;
import org.jenkins.plugins.statistics.gatherer.model.build.BuildStats;
import org.jenkins.plugins.statistics.gatherer.model.build.SCMInfo;
import org.jenkins.plugins.statistics.gatherer.model.build.SlaveInfo;
import org.jenkins.plugins.statistics.gatherer.model.scm.ScmCheckoutInfo;
import org.jenkins.plugins.statistics.gatherer.util.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Listener used for storing the Build information to the DB.
 *
 * @author hthakkallapally
 */
@Extension(dynamicLoadable = YesNoMaybe.YES)
public class RunStatsListener extends RunListener<Run<?, ?>> {

    private static final Logger LOGGER = Logger.getLogger(RunStatsListener.class.getName());
    private static final String BUILD_FAILURE_URL_TO_APPEND = "/api/json?depth=2&tree=actions[foundFailureCauses[categories,description,id,name]]";

    public RunStatsListener() {
        //Necessary for jenkins
    }

    public RunStatsListener(Class<Run<?, ?>> targetType) {
        super(targetType);
    }

    @Override
    public void onStarted(Run<?, ?> run, TaskListener listener) {
        if (PropertyLoader.getBuildInfo()) {
            try {
                final String buildResult = run.getResult() == null ? "INPROGRESS" : run.getResult().toString();
                final LocalDateTime startTime = LocalDateTime.ofInstant(
                    run.getTimestamp().getTime().toInstant(), ZoneId.systemDefault()
                );
                BuildStats build = new BuildStats();
                build.setContextId(run.getExecutor() != null && run.getExecutor().getCurrentWorkUnit() != null ? run.getExecutor().getCurrentWorkUnit().context.hashCode() : 0);
                build.setStartTime(startTime);
                build.setCiUrl(Jenkins.getInstance().getRootUrl());
                build.setJobName(run.getParent().getName());
                build.setFullJobName(run.getParent().getFullName());
                build.setNumber(run.getNumber());
                build.setResult(buildResult);
                build.setBuildUrl(run.getUrl());
                build.setQueueTime(run.getExecutor() != null ? run.getExecutor().getTimeSpentInQueue() : 0);
                build.setBuildCause(run.getCauses().get(0).getShortDescription());
                addUserDetails(run, build);
                addSCMInfo(run, listener, build);
                addParameters(run, build);
                addSlaveInfo(run, build, listener);
                RestClientUtil.postToService(getRestUrl(), build);
                LogbackUtil.info(build);
                LOGGER.log(Level.FINE, "Started build and its status is : " + buildResult +
                        " and start time is : " + startTime);
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Failed to call API " + getRestUrl() +
                        " for build " + run.getDisplayName(), e);
            }
        }
    }

    /**
     * Construct REST URL for build resource.
     *
     * @return API end point url.
     */
    private String getRestUrl() {
        return PropertyLoader.getBuildEndPoint();
    }

    /**
     * Add the slave info to build model.
     *
     * @param run
     * @param build
     */
    private void addSlaveInfo(Run<?, ?> run, BuildStats build, TaskListener listener) throws InterruptedException {
        SlaveInfo slaveInfo = new SlaveInfo();
        EnvVars environment = getEnvVars(run, listener);
        if (environment != null) {
            if (environment.get("NODE_NAME") != null) {
                slaveInfo.setSlaveName(environment.get("NODE_NAME"));
            }
            if (environment.get("NODE_LABELS") != null) {
                slaveInfo.setLabel(environment.get("NODE_LABELS"));
            }
            if (environment.get("EXECUTOR_NUMBER") != null) {
                slaveInfo.setExecutor(environment.get("EXECUTOR_NUMBER"));
            }
        }
        build.setSlaveInfo(slaveInfo);
    }

    private EnvVars getEnvVars(Run<?, ?> run, TaskListener listener) throws InterruptedException {
        EnvVars environment = null;
        try {
            environment = run.getEnvironment(listener);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed to retrieve environment for " + run.getUrl(), e);
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, "Failed to retrieve environment for " + run.getUrl(), e);
            throw e;
        }
        return environment;
    }

    /**
     * Get all the parameters used in the build.
     *
     * @param run
     * @param build
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
     *
     * @param run
     * @param listener
     * @param build
     */
    private void addSCMInfo(Run<?, ?> run, TaskListener listener, BuildStats build) throws InterruptedException {
        EnvVars environment = getEnvVars(run, listener);
        SCMInfo scmInfo = new SCMInfo();
        if (environment != null) {
            if (environment.get("GIT_URL") != null) {
                scmInfo.setUrl(environment.get("GIT_URL"));
            } else if (environment.get("SVN_URL") != null) {
                scmInfo.setUrl(environment.get("SVN_URL"));
            }
            if (environment.get("GIT_BRANCH") != null) {
                scmInfo.setBranch(environment.get("GIT_BRANCH"));
            } else if (environment.get("Branch") != null) {
                scmInfo.setBranch(environment.get("Branch"));
            }
            if (environment.get("GIT_COMMIT") != null) {
                scmInfo.setCommit(environment.get("GIT_COMMIT"));
            } else if (environment.get("SVN_REVISION") != null) {
                scmInfo.setCommit(environment.get("SVN_REVISION"));
            }
        }
        build.setScmInfo(scmInfo);
    }

    /**
     * Add user name and user id details to build model.
     *
     * @param run
     * @param build
     */
    private void addUserDetails(Run<?, ?> run, BuildStats build) {
        List<Cause> causes = run.getCauses();
        for (Cause cause : causes) {
            if (cause instanceof Cause.UserIdCause) {
                String userId = ((Cause.UserIdCause) cause).getUserId();
                userId = (userId == null || userId.isEmpty()) ? Constants.ANONYMOUS : userId;
                build.setStartedUserId(userId);
                String userName = ((Cause.UserIdCause) cause).getUserName();
                userName = (userName == null || userName.isEmpty()) ? Constants.ANONYMOUS : userName;
                build.setStartedUserName(userName);
            } else if (cause instanceof Cause.UpstreamCause) {
                build.setStartedUserId(JenkinsCauses.UPSTREAM);
                build.setStartedUserName(Constants.SYSTEM);
            } else if (cause instanceof SCMTrigger.SCMTriggerCause) {
                build.setStartedUserId(JenkinsCauses.SCM);
                build.setStartedUserName(Constants.SYSTEM);
            } else if (cause instanceof TimerTrigger.TimerTriggerCause) {
                build.setStartedUserId(JenkinsCauses.TIMER);
                build.setStartedUserName(Constants.SYSTEM);
            } else {
                build.setStartedUserId(Constants.UNKNOWN);
                build.setStartedUserName(Constants.SYSTEM);
            }
        }
    }

    /**
     * Update the build status and duration.
     */
    @Override
    public void onFinalized(final Run<?, ?> run) {
        if (PropertyLoader.getBuildInfo()) {
            try {
                final String buildResult = run.getResult() == null ? Constants.UNKNOWN : run.getResult().toString();
                final LocalDateTime endTime = LocalDateTime.now();
                BuildStats build = new BuildStats();
                build.setCiUrl(Jenkins.getInstance().getRootUrl());
                build.setJobName(run.getParent().getName());
                build.setFullJobName(run.getParent().getFullName());
                build.setNumber(run.getNumber());
                build.setResult(buildResult);
                build.setBuildUrl(run.getUrl());
                build.setDuration(run.getDuration());
                build.setEndTime(endTime);
                addSCMInfo(run, SoutTaskListener.INSTANCE, build);
                addBuildFailureCauses(build);
                RestClientUtil.postToService(getRestUrl(), build);
                LogbackUtil.info(build);
                LOGGER.log(Level.FINE, run.getParent().getName() + " build is completed " +
                        "its status is : " + buildResult +
                        " at time : " + endTime);
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Failed to call API " + getRestUrl() +
                        " for build " + run.getDisplayName(), e);
            }
        }
    }

    private void addBuildFailureCauses(BuildStats build) {
        List<PluginWrapper> plugins = Jenkins.getInstance().getPluginManager().getPlugins();
        for (PluginWrapper plugin : plugins) {
            if (plugin.getDisplayName().contains("Build Failure Analyzer")) {
                JSONObject response = RestClientUtil.getJson(build.getCiUrl() + build.getBuildUrl() + BUILD_FAILURE_URL_TO_APPEND);
                if (response != null && response.containsKey("actions")) {
                    JSONArray actions = response.getJSONArray("actions");
                    for (int i = 0; i < actions.size(); i++) {
                        JSONObject failureResponse = actions.getJSONObject(i);
                        if (!failureResponse.keySet().isEmpty()) {
                            List<Map<String, Object>> failureCauses = new ArrayList<>();
                            if (failureResponse.containsKey("foundFailureCauses")) {
                                for (int j = 0; j < failureResponse.getJSONArray("foundFailureCauses").size(); j++) {
                                    JSONArray foundFailureCauses = failureResponse.getJSONArray("foundFailureCauses");
                                    Map<String, Object> jsonObject = JSONUtil.convertBuildFailureToMap(foundFailureCauses.getJSONObject(j));
                                    failureCauses.add(jsonObject);
                                }
                            }
                            build.setBuildFailureCauses(failureCauses);
                        }
                    }
                    break;
                }
            }
        }
    }

    @Override
    public Environment setUpEnvironment(AbstractBuild build,
                                        Launcher launcher,
                                        BuildListener listener) throws IOException, InterruptedException {
        if (PropertyLoader.getScmCheckoutInfo()) {
            ScmCheckoutInfo scmCheckoutInfo = new ScmCheckoutInfo();
            scmCheckoutInfo.setStartTime(LocalDateTime.now());
            scmCheckoutInfo.setBuildUrl(build.getUrl());
            scmCheckoutInfo.setEndTime(Constants.TIME_EPOCH);
            RestClientUtil.postToService(getScmCheckoutUrl(), scmCheckoutInfo);
            LogbackUtil.info(scmCheckoutInfo);
        }
        return super.setUpEnvironment(build, launcher, listener);

    }

    private String getScmCheckoutUrl() {
        return PropertyLoader.getScmCheckoutEndPoint();
    }
}
