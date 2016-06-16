package org.jenkins.plugins.statistics.model;

import java.util.Date;
import java.util.Map;

/**
 * Created by hthakkallapally on 3/16/2015.
 */
public class StatsBuild {

  private String ciUrl;

  private String jobName;

  private String jobFullName;

  private int number;

  private SlaveInfo slaveInfo;

  private Date startTime;

  private Date endTime;

  private String startedUserId;

  private String startedUserName;

  private String result;

  private long duration;

  private Map<String, String> parameters;

  private SCMInfo scmInfo;

  private long queueTime;

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

  public String getJobFullName() {
    return jobFullName;
  }

  public void setJobFullName(String jobFullName) {
    this.jobFullName = jobFullName;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public SlaveInfo getSlaveInfo() {
    return slaveInfo;
  }

  public void setSlaveInfo(SlaveInfo slaveInfo) {
    this.slaveInfo = slaveInfo;
  }

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

  public String getStartedUserId() {
    return startedUserId;
  }

  public void setStartedUserId(String startedUserId) {
    this.startedUserId = startedUserId;
  }

  public String getStartedUserName() {
    return startedUserName;
  }

  public void setStartedUserName(String startedUserName) {
    this.startedUserName = startedUserName;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public long getDuration() {
    return duration;
  }

  public void setDuration(long duration) {
    this.duration = duration;
  }

  public Map<String, String> getParameters() {
    return parameters;
  }

  public void setParameters(Map<String, String> parameters) {
    this.parameters = parameters;
  }

  public SCMInfo getScmInfo() {
    return scmInfo;
  }

  public void setScmInfo(SCMInfo scmInfo) {
    this.scmInfo = scmInfo;
  }

  public long getQueueTime() {
    return queueTime;
  }

  public void setQueueTime(long queueTime) {
    this.queueTime = queueTime;
  }
}
