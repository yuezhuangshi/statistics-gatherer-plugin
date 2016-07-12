package org.jenkins.plugins.statistics.gatherer.listeners;

import hudson.Extension;
import hudson.FilePath;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.model.listeners.SCMListener;
import hudson.scm.SCM;
import hudson.scm.SCMRevisionState;
import org.jenkins.plugins.statistics.gatherer.model.scm.ScmCheckout;
import org.jenkins.plugins.statistics.gatherer.util.PropertyLoader;
import org.jenkins.plugins.statistics.gatherer.util.RestClientUtil;

import java.io.File;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by mcharron on 2016-07-12.
 */
@Extension
public class ScmStatsListener extends SCMListener{
    private static final Logger LOGGER = Logger.getLogger(ItemStatsListener.class.getName());

    public ScmStatsListener() {
        //Necessary for jenkins
    }

    @Override
    public void onCheckout(Run<?,?> build, SCM scm, FilePath workspace, TaskListener listener, File changelogFile, SCMRevisionState pollingBaseline){
        ScmCheckout scmCheckout = new ScmCheckout();
        scmCheckout.setFullJobName(build.getParent().getFullName());
        scmCheckout.setBuildUrl(build.getUrl());
        scmCheckout.setStartTime(new Date());
        RestClientUtil.postToService(getRestUrl(), build);
    }

    private String getRestUrl() {
        return PropertyLoader.getScmCheckoutEndPoint();
    }
}
