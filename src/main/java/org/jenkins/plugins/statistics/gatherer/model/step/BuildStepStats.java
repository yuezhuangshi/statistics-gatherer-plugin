package org.jenkins.plugins.statistics.gatherer.model.step;

import java.util.Date;

/**
 * Created by mcharron on 2016-07-12.
 */
public class BuildStepStats {
    private Date startTime;
    private Date endTime;
    private String buildUrl;
    private String buildFullName;
    private String buildStepType;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getBuildUrl() {
        return buildUrl;
    }

    public void setBuildUrl(String buildUrl) {
        this.buildUrl = buildUrl;
    }

    public String getBuildFullName() {
        return buildFullName;
    }

    public void setBuildFullName(String buildFullName) {
        this.buildFullName = buildFullName;
    }

    public String getBuildStepType() {
        return buildStepType;
    }

    public void setBuildStepType(String buildStepType) {
        this.buildStepType = buildStepType;
    }
}
