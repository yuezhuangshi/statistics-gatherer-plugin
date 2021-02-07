package org.jenkins.plugins.statistics.gatherer.model.build;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by hthakkallapally on 3/16/2015.
 */
public class BuildStats {

    private String ciUrl;
    private String jobName;
    private String fullJobName;
    private int number;
    private SlaveInfo slaveInfo;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String startedUserId;
    private String startedUserName;
    private String result;
    private long duration;
    private Map<String, String> parameters;
    private SCMInfo scmInfo;
    private long queueTime;
    private String buildUrl;
    private int contextId;
    private String buildCause;
    private List<Map<String, Object>> buildFailureCauses;

    public BuildStats(String ciUrl,
                      String jobName,
                      String fullJobName,
                      int number,
                      SlaveInfo slaveInfo,
                      LocalDateTime startTime,
                      LocalDateTime endTime,
                      String startedUserId,
                      String startedUserName,
                      String result,
                      long duration,
                      Map<String, String> parameters,
                      SCMInfo scmInfo,
                      long queueTime,
                      String buildUrl,
                      int contextId,
                      String buildCause,
                      List<Map<String, Object>> buildFailureCauses) {
        this.ciUrl = ciUrl;
        this.jobName = jobName;
        this.fullJobName = fullJobName;
        this.number = number;
        this.slaveInfo = slaveInfo;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startedUserId = startedUserId;
        this.startedUserName = startedUserName;
        this.result = result;
        this.duration = duration;
        this.parameters = parameters;
        this.scmInfo = scmInfo;
        this.queueTime = queueTime;
        this.buildUrl = buildUrl;
        this.contextId = contextId;
        this.buildCause = buildCause;
        this.buildFailureCauses = buildFailureCauses;
    }

    public BuildStats() {
        this.ciUrl = "";
        this.jobName = "";
        this.fullJobName = "";
        this.number = 0;
        this.slaveInfo = new SlaveInfo();
        this.startTime = LocalDateTime.now();
        this.endTime = LocalDateTime.now();
        this.startedUserId = "";
        this.startedUserName = "";
        this.result = "";
        this.duration = 0;
        this.parameters = new HashMap<>();
        this.scmInfo = new SCMInfo();
        this.queueTime = 0;
        this.buildUrl = "";
        this.contextId = 0;
        this.buildCause = "";
        this.buildFailureCauses = new ArrayList<>();
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

    public String getFullJobName() {
        return fullJobName;
    }

    public void setFullJobName(String fullJobName) {
        this.fullJobName = fullJobName;
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

    public String getBuildUrl() {
        return buildUrl;
    }

    public void setBuildUrl(String buildUrl) {
        this.buildUrl = buildUrl;
    }

    public int getContextId() {
        return contextId;
    }

    public void setContextId(int contextId) {
        this.contextId = contextId;
    }

    public String getBuildCause() {
        return buildCause;
    }

    public void setBuildCause(String buildCause) {
        this.buildCause = buildCause;
    }

    public List<Map<String, Object>> getBuildFailureCauses() {
        return buildFailureCauses;
    }

    public void setBuildFailureCauses(List<Map<String, Object>> buildFailureCauses) {
        this.buildFailureCauses = buildFailureCauses;
    }
}
