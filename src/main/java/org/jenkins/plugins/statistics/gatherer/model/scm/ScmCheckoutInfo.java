package org.jenkins.plugins.statistics.gatherer.model.scm;

import org.jenkins.plugins.statistics.gatherer.util.Constants;

import java.time.LocalDateTime;

/**
 * Created by mcharron on 2016-07-18.
 */
public class ScmCheckoutInfo {

    private String buildUrl;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public ScmCheckoutInfo(LocalDateTime startTime, LocalDateTime endTime, String buildUrl) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.buildUrl = buildUrl;
    }

    public ScmCheckoutInfo() {
        this.startTime = Constants.TIME_EPOCH;
        this.endTime = Constants.TIME_EPOCH;
        this.buildUrl = "";
    }

    public String getBuildUrl() {
        return buildUrl;
    }

    public void setBuildUrl(String buildUrl) {
        this.buildUrl = buildUrl;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
