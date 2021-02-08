package org.jenkins.plugins.statistics.gatherer.listeners;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.model.Item;
import hudson.model.Job;
import hudson.model.User;
import hudson.model.listeners.ItemListener;
import jenkins.YesNoMaybe;
import jenkins.model.Jenkins;
import org.jenkins.plugins.statistics.gatherer.model.job.JobStats;
import org.jenkins.plugins.statistics.gatherer.util.Constants;
import org.jenkins.plugins.statistics.gatherer.util.PropertyLoader;
import org.jenkins.plugins.statistics.gatherer.util.RestClientUtil;
import org.jenkinsci.plugins.workflow.job.WorkflowJob;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * provides job info information when job create, update, delete
 * @author jasper
 */
@Extension(dynamicLoadable = YesNoMaybe.YES)
public class ItemStatsListener extends ItemListener {

    private static final Logger LOGGER = Logger.getLogger(ItemStatsListener.class.getName());

    @Override
    public void onCreated(Item item) {
        if (!PropertyLoader.getProjectInfo()) {
            return;
        }
        LocalDateTime now = LocalDateTime.now();
        Job<?, ?> job = (Job<?, ?>) item;
        JobStats jobStats = addJobData(job);
        jobStats.setCreatedDate(now);
        jobStats.setUpdatedDate(now);
        jobStats.setStatus(Constants.ACTIVE);
        setConfig(job, jobStats);
        RestClientUtil.postToService(getRestUrl(), jobStats);
    }


    @Override
    public void onUpdated(Item item) {
        if (!PropertyLoader.getProjectInfo()) {
            return;
        }
        Job<?, ?> job = (Job<?, ?>) item;
        JobStats jobStats = addJobData(job);
        jobStats.setUpdatedDate(LocalDateTime.now());
        addJobStatus(job, jobStats);
        setConfig(job, jobStats);
        RestClientUtil.postToService(getRestUrl(), jobStats);
    }

    @Override
    public void onDeleted(Item item) {
        if (!PropertyLoader.getProjectInfo()) {
            return;
        }
        Job<?, ?> job = (Job<?, ?>) item;
        JobStats jobStats = addJobData(job);
        jobStats.setUpdatedDate(LocalDateTime.now());
        jobStats.setStatus(Constants.DELETED);
        RestClientUtil.postToService(getRestUrl(), jobStats);
    }

    /**
     * Construct REST API url for project resource.
     */
    private String getRestUrl() {
        return PropertyLoader.getProjectEndPoint();
    }

    /**
     * Construct JobStats model and populate common data in helper method.
     */
    private JobStats addJobData(Job<?, ?> job) {
        JobStats jobStats = JobStats.builder()
            .name(job.getName())
            .fullName(job.getFullName())
            .rootUrl(Jenkins.get().getRootUrl())
            .jobUrl(job.getUrl())
            .build();

        User user = Jenkins.get().getUser(Jenkins.getAuthentication().getName());
        if (user != null) {
            jobStats.setOperatorId(user.getId());
            jobStats.setOperatorName(user.getFullName());
        }

        return jobStats;
    }

    /**
     * add Job status to JobStats model
     */
    private void addJobStatus(Job<?, ?> job, JobStats jobStats) {
        if (job instanceof WorkflowJob) {
            boolean disabled = ((WorkflowJob) job).isDisabled();
            jobStats.setStatus(disabled ? Constants.DISABLED: Constants.ACTIVE);
        } else if (job instanceof AbstractProject) {
            boolean disabled = ((AbstractProject<?, ?>) job).isDisabled();
            jobStats.setStatus(disabled ? Constants.DISABLED: Constants.ACTIVE);
        }
    }

    /**
     * Get job configuration as a string
     */
    private void setConfig(Job<?, ?> job, JobStats jobStats) {
        try {
            jobStats.setConfigFile(job.getConfigFile().asString());
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed to get config.xml file for " + job.getDisplayName(), e);
        }
    }

}
