package org.jenkins.plugins.statistics.gatherer.listeners;

import hudson.Extension;
import hudson.FilePath;
import hudson.model.AbstractBuild;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.model.listeners.SCMListener;
import hudson.scm.SCM;
import hudson.scm.SCMRevisionState;
import org.jenkins.plugins.statistics.gatherer.model.scm.ScmCheckout;
import org.jenkins.plugins.statistics.gatherer.util.PropertyLoader;
import org.jenkins.plugins.statistics.gatherer.util.RestClientUtil;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mcharron on 2016-07-15.
 */
@Extension
public class ScmStatsListener extends SCMListener{

    @Override
    public void onCheckout(Run<?,?> run, SCM scm, FilePath workspace, TaskListener listener, File changelogFile, SCMRevisionState pollingBaseline){
        if (PropertyLoader.getScmCheckoutInfo()){
            if (!(run instanceof AbstractBuild)) {
                return;
            }
            ScmCheckout scmCheckout = new ScmCheckout();
            scmCheckout.setBuildUrl(run.getUrl());
            scmCheckout.setStartTime(new Date(0));
            scmCheckout.setEndTime(Calendar.getInstance().getTime());
            RestClientUtil.postToService(getUrl(), scmCheckout);
        }
    }

    private String getUrl(){
        return PropertyLoader.getScmCheckoutEndPoint();
    }
}
