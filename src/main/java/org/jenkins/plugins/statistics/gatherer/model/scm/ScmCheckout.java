package org.jenkins.plugins.statistics.gatherer.model.scm;

import java.util.Date;

/**
 * Created by mcharron on 2016-07-12.
 */
public class ScmCheckout {

    private String fullJobName;

    private Date startTime;

    private String buildUrl;

    public String getFullJobName() {
        return fullJobName;
    }

    public void setFullJobName(String fullJobName) {
        this.fullJobName = fullJobName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getBuildUrl() {
        return buildUrl;
    }

    public void setBuildUrl(String buildUrl) {
        this.buildUrl = buildUrl;
    }
}
