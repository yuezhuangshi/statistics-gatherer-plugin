package org.jenkins.plugins.statistics.gatherer.model.step;

import org.jenkins.plugins.statistics.gatherer.util.Constants;

import java.time.LocalDateTime;

/**
 * Created by mcharron on 2016-07-12.
 */
public class BuildStepStats {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String buildUrl;
    private String buildStepType;
    private String buildStepId;

    public BuildStepStats(LocalDateTime startTime, LocalDateTime endTime, String buildUrl, String buildStepType, String buildStepId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.buildUrl = buildUrl;
        this.buildStepType = buildStepType;
        this.buildStepId = buildStepId;
    }

    public BuildStepStats() {
        this.startTime = Constants.TIME_EPOCH;
        this.endTime = Constants.TIME_EPOCH;
        this.buildUrl = "";
        this.buildStepType = "";
        this.buildStepId = "";
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

    public String getBuildUrl() {
        return buildUrl;
    }

    public void setBuildUrl(String buildUrl) {
        this.buildUrl = buildUrl;
    }

    public String getBuildStepType() {
        return buildStepType;
    }

    public void setBuildStepType(String buildStepType) {
        this.buildStepType = buildStepType;
    }

    public String getBuildStepId() {
        return buildStepId;
    }

    public void setBuildStepId(String buildStepId) {
        this.buildStepId = buildStepId;
    }
}
