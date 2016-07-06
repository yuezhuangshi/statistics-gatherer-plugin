package org.jenkins.plugins.statistics.model;

import java.util.*;

/**
 * Created by hthakkallapally on 3/16/2015.
 */
public class StatsQueue {

    private String ciUrl;

    private String jobName;

    private Date entryTime;

    private Date exitTime;

    private String startedBy;

    private int jenkinsQueueId;

    private String status;

    private long duration;

    private String durationStr;

    private List<QueueCause> queueCauses = new ArrayList<>();

    public StatsQueue(String ciUrl,
                      String jobName,
                      Date entryTime,
                      Date exitTime,
                      String startedBy,
                      int jenkinsQueueId,
                      String status,
                      long duration,
                      String durationStr,
                      List<QueueCause> queueCauses) {
        this.ciUrl = ciUrl;
        this.jobName = jobName;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.startedBy = startedBy;
        this.jenkinsQueueId = jenkinsQueueId;
        this.status = status;
        this.duration = duration;
        this.durationStr = durationStr;
        this.queueCauses = queueCauses;
    }

    public StatsQueue() {
        this.ciUrl = "";
        this.jobName = "";
        this.entryTime = new Date();
        this.exitTime = new Date();
        this.startedBy = "";
        this.jenkinsQueueId = 0;
        this.status = "";
        this.duration = 0;
        this.durationStr = "";
        this.queueCauses = new ArrayList<>();
    }


    public String getCiUrl() {
        return ciUrl;
    }

    public void setCiUrl(String ciUrl) {
        this.ciUrl = ciUrl;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Date getEntryTime() {
        return entryTime == null ? null : new Date(entryTime.getTime());
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime == null ? null : new Date(entryTime.getTime());
    }

    public Date getExitTime() {
        return exitTime == null ? null : new Date(exitTime.getTime());
    }

    public void setExitTime(Date exitTime) {
        this.exitTime = exitTime == null ? null : new Date(exitTime.getTime());
    }

    public String getStartedBy() {
        return startedBy;
    }

    public void setStartedBy(String startedBy) {
        this.startedBy = startedBy;
    }

    public int getJenkinsQueueId() {
        return jenkinsQueueId;
    }

    public void setJenkinsQueueId(int jenkinsQueueId) {
        this.jenkinsQueueId = jenkinsQueueId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getDurationStr() {
        return durationStr;
    }

    public void setDurationStr(String durationStr) {
        this.durationStr = durationStr;
    }

    public List<QueueCause> getQueueCauses() {
        return queueCauses;
    }

    public void setQueueCauses(List<QueueCause> queueCauses) {
        this.queueCauses = queueCauses;
    }

    public void addQueueCause(QueueCause queueCause) {
        this.queueCauses.add(queueCause);
    }
}
