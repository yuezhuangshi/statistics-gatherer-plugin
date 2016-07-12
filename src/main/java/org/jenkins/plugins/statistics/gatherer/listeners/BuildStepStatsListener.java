package org.jenkins.plugins.statistics.gatherer.listeners;

import hudson.Extension;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.BuildStepListener;
import hudson.tasks.BuildStep;

import java.util.Date;

/**
 * Created by mcharron on 2016-07-12.
 */
@Extension
public class BuildStepStatsListener extends BuildStepListener{

    @Override
    public void finished(AbstractBuild build, BuildStep bs, BuildListener listener, boolean canContinue){
        System.out.println(new Date());
        System.out.println(build.getProject().getUrl());
        System.out.println(build.getProject().getFullName());
        System.out.println(bs.getProjectActions(build.getProject()));
        System.out.println(bs.toString());
    }

    @Override
    public void started(AbstractBuild build, BuildStep bs, BuildListener listener){
        System.out.println(build.getProject().getUrl());
        System.out.println(build.getProject().getFullName());
        System.out.println(bs.getProjectActions(build.getProject()));
        System.out.println(bs.getRequiredMonitorService());
        System.out.println( bs.getClass().toString());
        System.out.println(new Date());
    }
}
