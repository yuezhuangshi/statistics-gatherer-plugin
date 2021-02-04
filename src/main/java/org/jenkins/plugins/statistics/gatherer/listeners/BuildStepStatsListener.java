package org.jenkins.plugins.statistics.gatherer.listeners;

import hudson.Extension;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.BuildStepListener;
import hudson.tasks.BuildStep;
import jenkins.YesNoMaybe;
import org.jenkins.plugins.statistics.gatherer.model.step.BuildStepStats;
import org.jenkins.plugins.statistics.gatherer.util.Constants;
import org.jenkins.plugins.statistics.gatherer.util.PropertyLoader;
import org.jenkins.plugins.statistics.gatherer.util.RestClientUtil;

import java.time.LocalDateTime;

/**
 * Created by mcharron on 2016-07-12.
 */
@Extension(dynamicLoadable = YesNoMaybe.YES)
public class BuildStepStatsListener extends BuildStepListener {

    @Override
    public void finished(AbstractBuild build, BuildStep bs, BuildListener listener, boolean canContinue) {
        if (PropertyLoader.getBuildStepInfo()) {
            BuildStepStats buildStepStats = new BuildStepStats();
            buildStepStats.setBuildUrl(build.getUrl());
            buildStepStats.setBuildStepType(bs.getClass().toString().replace("class ", ""));
            buildStepStats.setBuildStepId(bs.toString());
            buildStepStats.setEndTime(LocalDateTime.now());
            buildStepStats.setStartTime(Constants.TIME_EPOCH);
            RestClientUtil.postToService(getRestUrl(), buildStepStats);
        }
    }

    @Override
    public void started(AbstractBuild build, BuildStep bs, BuildListener listener) {
        if (PropertyLoader.getBuildStepInfo()) {
            BuildStepStats buildStepStats = new BuildStepStats();
            buildStepStats.setBuildUrl(build.getUrl());
            buildStepStats.setBuildStepType(bs.getClass().toString().replace("class ", ""));
            buildStepStats.setBuildStepId(bs.toString());
            buildStepStats.setStartTime(LocalDateTime.now());
            buildStepStats.setEndTime(Constants.TIME_EPOCH);
            RestClientUtil.postToService(getRestUrl(), buildStepStats);
        }
    }

    private String getRestUrl() {
        return PropertyLoader.getBuildStepEndPoint();
    }
}
