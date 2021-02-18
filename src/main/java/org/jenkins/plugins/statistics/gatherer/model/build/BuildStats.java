package org.jenkins.plugins.statistics.gatherer.model.build;

import com.alibaba.fastjson.annotation.JSONField;
import hudson.model.Result;
import hudson.model.Run;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jenkins.plugins.statistics.gatherer.model.stage.StageStats;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Build Stats
 * @author jasper
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildStats {

    /**
     * Global jenkins root url
     */
    private String rootUrl;
    /**
     * build url
     */
    private String buildUrl;
    /**
     * job name
     */
    private String jobName;
    /**
     * full job name
     */
    private String fullJobName;
    /**
     * declare or script pipeline
     */
    private boolean isDeclarativePipeline;
    /**
     * build number
     */
    private Integer buildNumber;
    /**
     * build result, eg: success, failure and etc.
     * @see {@link Result}
     */
    private String result;
    /**
     * not use LocalDateTime because of security reason
     * build schedule time
     * see difference between {@link Run#getTime()} and {@link Run#getStartTimeInMillis()}
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /**
     * not use LocalDateTime because of security reason
     * build end time
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    /**
     * build duration
     */
    private Long duration;
    /**
     * the number of milli-seconds the currently executing job spent in the queue
     * waiting for an available executor. This excludes the quiet period time of the job.
     */
    private Long queueTime;
    /**
     * slave info
     */
    private SlaveInfo slaveInfo;
    /**
     * build scm info
     */
    private ScmInfo scmInfo;
    /**
     * build parameters
     */
    private Map<String, String> parameters;
    /**
     * build stage stats
     */
    private Map<String, StageStats> stages;
    /**
     * trigger user id
     */
    private String startedUserId;
    /**
     * trigger user name
     */
    private String startedUserName;
    /**
     * build cause
     */
    private String buildCause;
    /**
     * build failure cause
     */
    private List<Map<String, Object>> buildFailureCauses;

    /**
     * build scm info
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScmInfo {

        /**
         * scm action type, eg: push / merge
         */
        private String action;
        /**
         * scm url, eg: git url
         */
        private String url;
        /**
         * scm branch, eg: git branch
         */
        private String branch;
        /**
         * scm commit, eg: git commit
         */
        private String commit;

        /**
         * scm commit author
         */
        private String author;

    }

    /**
     * build slave info
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SlaveInfo {

        private String slaveName;
        private String executor;
        private String label;

    }

}
