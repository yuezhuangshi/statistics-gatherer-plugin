package org.jenkins.plugins.statistics.listeners;

import org.jenkins.plugins.statistics.model.StatsJob;
import org.jenkins.plugins.statistics.util.*;
import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.model.Item;
import hudson.model.User;
import hudson.model.listeners.ItemListener;
import jenkins.model.Jenkins;

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
      LOGGER.log(Level.WARNING, "Failed to call API " + getRestUrl() +
          " for job " + item.getDisplayName(), e);
    }
  }

  /**
   * Construct REST API url for project resource.
   *
   * @return
   */
  private String getRestUrl() {
    String endPoint = PropertyLoader.getStatsEndPoint();
    String projRes = PropertyLoader.getEnvironmentProperty(
        "statistics.resource.project");
    return endPoint + projRes;
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
      RestClientUtil.putToService(getRestUrl(), ciJob);
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, "Failed to call API " + getRestUrl()+
          " for job " + item.getDisplayName(), e);
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
      RestClientUtil.putToService(getRestUrl(), ciJob);
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, "Failed to call API "+ getRestUrl()
              +" for job "+ item.getDisplayName(), e);
    }
  }
}
