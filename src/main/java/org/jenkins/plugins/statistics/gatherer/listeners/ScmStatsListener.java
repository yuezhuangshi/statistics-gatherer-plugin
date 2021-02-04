package org.jenkins.plugins.statistics.gatherer.listeners;

import hudson.Extension;
import hudson.FilePath;
import hudson.model.AbstractBuild;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.model.listeners.SCMListener;
import hudson.scm.SCM;
import hudson.scm.SCMRevisionState;
import jenkins.YesNoMaybe;
import org.jenkins.plugins.statistics.gatherer.model.scm.ScmCheckoutInfo;
import org.jenkins.plugins.statistics.gatherer.util.Constants;
import org.jenkins.plugins.statistics.gatherer.util.PropertyLoader;
import org.jenkins.plugins.statistics.gatherer.util.RestClientUtil;

import java.io.File;
import java.time.LocalDateTime;

/**
 * Created by mcharron on 2016-07-15.
 */
@Extension(dynamicLoadable = YesNoMaybe.YES)
public class ScmStatsListener extends SCMListener {

    @Override
    public void onCheckout(Run<?, ?> run, SCM scm, FilePath workspace, TaskListener listener, File changelogFile, SCMRevisionState pollingBaseline) {
        if (PropertyLoader.getScmCheckoutInfo()) {
            if (!(run instanceof AbstractBuild)) {
                return;
            }
            ScmCheckoutInfo scmCheckoutInfo = new ScmCheckoutInfo();
            scmCheckoutInfo.setBuildUrl(run.getUrl());
            scmCheckoutInfo.setStartTime(Constants.TIME_EPOCH);
            scmCheckoutInfo.setEndTime(LocalDateTime.now());
            RestClientUtil.postToService(getUrl(), scmCheckoutInfo);
        }
    }

    private String getUrl() {
        return PropertyLoader.getScmCheckoutEndPoint();
    }
}
