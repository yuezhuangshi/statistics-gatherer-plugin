package org.jenkins.plugins.statistics.gatherer.model.job;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author jasper
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobStats {

    /**
     * job name
     */
    private String name;
    /**
     * job full name
     */
    private String fullName;
    /**
     * operator user id
     */
    private String operatorId;
    /**
     * operator user name
     */
    private String operatorName;
    /**
     * root url
     */
    private String rootUrl;
    /**
     * job url
     */
    private String jobUrl;
    /**
     * job status
     */
    private String status;
    /**
     * job config file
     */
    private String configFile;
    /**
     * job create date
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime createdDate;
    /**
     * job update date
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime updatedDate;

}
