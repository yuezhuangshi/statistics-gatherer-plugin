package org.jenkins.plugins.statistics.model;

import java.util.Date;
import java.util.Map;

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

  private Map<String, QueueCause> queueCauses;

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

  public Map<String, QueueCause> getQueueCauses() {
    return queueCauses;
  }

  public void setQueueCauses(Map<String, QueueCause> queueCauses) {
    this.queueCauses = queueCauses;
  }
}
