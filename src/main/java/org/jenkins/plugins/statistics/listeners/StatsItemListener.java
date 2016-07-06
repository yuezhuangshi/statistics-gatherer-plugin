package org.jenkins.plugins.statistics.listeners;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.model.Item;
import hudson.model.User;
import hudson.model.listeners.ItemListener;
import jenkins.model.Jenkins;
import org.jenkins.plugins.statistics.model.StatsJob;
import org.jenkins.plugins.statistics.util.Constants;
import org.jenkins.plugins.statistics.util.PropertyLoader;
import org.jenkins.plugins.statistics.util.RestClientUtil;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by hthakkallapally on 3/12/2015.
 */
@Extension
public class StatsItemListener extends ItemListener {
    private static final Logger LOGGER = Logger.getLogger(StatsItemListener.class.getName());

    public StatsItemListener() {
        //Necessary for jenkins
    }

    @Override
    public void onCreated(Item item) {
        try {
            if (item == null) {
                return;
            }
            AbstractProject<?, ?> project = (AbstractProject<?, ?>) item;
            StatsJob ciJob = addCIJobData(project);
            ciJob.setCreatedDate(new Date());
            ciJob.setStatus(Constants.ACTIVE);
            setConfig(project, ciJob);
            RestClientUtil.postToService(getRestUrl(), ciJob);
        } catch (Exception e) {
            logException(item, e);
        }
    }

    private void logException(Item item, Exception e) {
        LOGGER.log(Level.WARNING, "Failed to call API " + getRestUrl() +
                " for job " + item.getDisplayName(), e);
    }

    /**
     * Construct REST API url for project resource.
     *
     * @return
     */
    private String getRestUrl() {
        return PropertyLoader.getProjectEndPoint();
    }

    /**
     * Construct CIJob model and populate common data in helper method.
     *
     * @param project
     * @return
     */
    private StatsJob addCIJobData(AbstractProject<?, ?> project) {
        StatsJob ciJob = new StatsJob();
        ciJob.setCiUrl(Jenkins.getInstance().getRootUrl());
        ciJob.setName(project.getName());
        String userName = Jenkins.getAuthentication().getName();
        User user = Jenkins.getInstance().getUser(userName);
        ciJob.setUserId(user.getId());
        ciJob.setUserName(user.getFullName());

        return ciJob;
    }

    /**
     * Get job configuration as a string and store it in DB.
     *
     * @param project
     * @param ciJob
     */
    private void setConfig(AbstractProject<?, ?> project, StatsJob ciJob) {
        try {
            ciJob.setConfigFile(project.getConfigFile().asString());
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed to get config.xml file " +
                    " for " + project.getDisplayName(), e);
        }
    }

    @Override
    public void onUpdated(Item item) {
        AbstractProject<?, ?> project = (AbstractProject<?, ?>) item;
        try {
            if (item == null) {
                return;
            }
            StatsJob ciJob = addCIJobData(project);
            ciJob.setUpdatedDate(new Date());
            ciJob.setStatus(project.isDisabled() ? Constants.DISABLED : Constants.ACTIVE);
            setConfig(project, ciJob);
            RestClientUtil.postToService(getRestUrl(), ciJob);
        } catch (Exception e) {
            logException(item, e);
        }
    }

    @Override
    public void onDeleted(Item item) {
        AbstractProject<?, ?> project = (AbstractProject<?, ?>) item;
        try {
            if (item == null) {
                return;
            }
            StatsJob ciJob = addCIJobData(project);
            ciJob.setUpdatedDate(new Date());
            ciJob.setStatus(Constants.DELETED);
            RestClientUtil.postToService(getRestUrl(), ciJob);
        } catch (Exception e) {
            logException(item, e);
        }
    }
}
