package org.jenkins.plugins.statistics.gatherer.model.queue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hthakkallapally on 3/16/2015.
 */
public class QueueStats {

    private String ciUrl;
    private String jobName;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private String startedBy;
    private int jenkinsQueueId;
    private String status;
    private int contextId;
    private long duration;
    private List<QueueCause> queueCauses;

    public QueueStats(String ciUrl,
                      String jobName,
                      LocalDateTime entryTime,
                      LocalDateTime exitTime,
                      String startedBy,
                      int jenkinsQueueId,
                      String status,
                      long duration,
                      List<QueueCause> queueCauses,
                      int contextId) {
        this.ciUrl = ciUrl;
        this.jobName = jobName;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.startedBy = startedBy;
        this.jenkinsQueueId = jenkinsQueueId;
        this.status = status;
        this.duration = duration;
        this.queueCauses = queueCauses;
        this.contextId = contextId;
    }

    public QueueStats() {
        this.ciUrl = "";
        this.jobName = "";
        this.entryTime = LocalDateTime.now();
        this.exitTime = LocalDateTime.now();
        this.startedBy = "";
        this.jenkinsQueueId = 0;
        this.status = "";
        this.duration = 0;
        this.queueCauses = new ArrayList<>();
        this.contextId = 0;
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

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
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

    public List<QueueCause> getQueueCauses() {
        return queueCauses;
    }

    public void setQueueCauses(List<QueueCause> queueCauses) {
        this.queueCauses = queueCauses;
    }

    public void addQueueCause(QueueCause queueCause) {
        this.queueCauses.add(queueCause);
    }

    public int getContextId() {
        return contextId;
    }

    public void setContextId(int contextId) {
        this.contextId = contextId;
    }
}
