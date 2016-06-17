package org.jenkins.plugins.statistics.listeners;

import org.jenkins.plugins.statistics.model.StatsQueue;
import org.jenkins.plugins.statistics.model.QueueCause;
import org.jenkins.plugins.statistics.util.*;
import hudson.Extension;
import hudson.model.Cause;
import hudson.model.Queue;
import hudson.model.Queue.Item;
import hudson.model.queue.QueueListener;
import hudson.triggers.SCMTrigger;
import hudson.triggers.TimerTrigger;
import jenkins.model.Jenkins;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by hthakkallapally on 3/13/2015.
 */
@Extension
public class StatsQueueListener extends QueueListener {
  private static final Logger LOGGER = Logger.getLogger(
      StatsQueueListener.class.getName());

  public StatsQueueListener() {

  }

  @Override
  public void onEnterWaiting(Queue.WaitingItem wi) {
    try {
      wi.getInQueueSince();
      StatsQueue queue = getCiQueue(wi);
      addStartedBy(wi, queue);
      queue.setEntryTime(new Date());
      queue.setExitTime(null);
      queue.setStatus(Constants.ENTERED);
      if (wi.getCauseOfBlockage() != null) {
        addEntryQueueCause("waiting", wi, queue);
      }
      RestClientUtil.postToService(getRestUrl(), queue);
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, "Failed to add Queue info for " +
          "job "+wi.task.getFullDisplayName()+
          " with queue id "+wi.id + " using "+ getRestUrl(), e);
    }
  }

  private void addEntryQueueCause(String name, Item wi,
                                  StatsQueue queue) {
    QueueCause cause = new QueueCause();
    cause.setEntryTime(new Date());
    cause.setExitTime(null);
    cause.setReasonForWaiting(wi.getCauseOfBlockage().getShortDescription());
    Map<String, QueueCause> map = new HashMap<String, QueueCause>();
    map.put(name, cause);
    queue.setQueueCauses(map);
  }

  @Override
  public void onLeaveWaiting(Queue.WaitingItem wi) {
    try {
      wi.getInQueueSince();
      StatsQueue queue = getCiQueue(wi);
      if (wi.getCauseOfBlockage() != null) {
        addExitQueueCause("waiting", wi, queue);
      }
      //PUT URL is pointing to /api/queues instead of/api/queues/id
      // as id will be reset to 1 each time you restart the Jenkins CI.
      // Hence the logic to update the correct queue record should be handled
      // at client side.
      RestClientUtil.putToService(getRestUrl(), queue);
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, "Failed to add Queue info for " +
          "job "+wi.task.getFullDisplayName()+
          " with queue id "+wi.id + " using "+ getRestUrl(), e);
    }
  }

  private void addExitQueueCause(String name, Item wi, StatsQueue queue) {
    QueueCause cause = new QueueCause();
    cause.setEntryTime(null);
    cause.setExitTime(new Date());
    cause.setReasonForWaiting(wi.getCauseOfBlockage().getShortDescription());
    Map<String, QueueCause> map = new HashMap<String, QueueCause>();
    map.put(name, cause);
    queue.setQueueCauses(map);
  }

  /**
   * onEnterBlocked is used to update Reason for waiting in Queue.
   * for ex. "Build #35 is already in progress (ETA:9 min 3 sec)"
   *
   * @param bi
   */
  @Override
  public void onEnterBlocked(Queue.BlockedItem bi) {
    try {
      StatsQueue queue = getCiQueue(bi);
      if (bi.getCauseOfBlockage() != null) {
        addEntryQueueCause("blocked", bi, queue);
      }
      //PUT URL is pointing to /api/queues instead of/api/queues/id
      // as id will be reset to 1 each time you restart the Jenkins CI.
      // Hence the logic to update the correct queue record should be handled
      // at client side.
      RestClientUtil.putToService(getRestUrl(), queue);
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, "JOb "+bi.task.getFullDisplayName()+
          " with queue id "+bi.id+
          "failed with exception : " + e);
    }
  }

  @Override
  public void onLeaveBlocked(Queue.BlockedItem bi) {
    try {
      StatsQueue queue = getCiQueue(bi);
      if (bi.getCauseOfBlockage() != null) {
        addExitQueueCause("blocked", bi, queue);
      }
      //PUT URL is pointing to /api/queues instead of/api/queues/id
      // as id will be reset to 1 each time you restart the Jenkins CI.
      // Hence the logic to update the correct queue record should be handled
      // at client side.
      RestClientUtil.putToService(getRestUrl(), queue);
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, "JOb "+bi.task.getFullDisplayName()+
          " with queue id "+bi.id+
          "failed with exception : " + e);
    }
  }

  /**
   * onEnterBlocked is used to update Reason for waiting in Queue.
   * for ex. "Waiting for next available executor"
   *
   * @param bi
   */
  @Override
  public void onEnterBuildable(Queue.BuildableItem bi) {
    try {
      StatsQueue queue = getCiQueue(bi);
      if (bi.getCauseOfBlockage() != null) {
        addEntryQueueCause("buildable", bi, queue);
      }
      //PUT URL is pointing to /api/queues instead of/api/queues/id
      // as id will be reset to 1 each time you restart the Jenkins CI.
      // Hence the logic to update the correct queue record should be handled
      // at client side.
      RestClientUtil.putToService(getRestUrl(), queue);
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, "JOb "+bi.task.getFullDisplayName()+
          " with queue id "+bi.id+
          "failed with exception : " + e);
    }
  }

  @Override
  public void onLeaveBuildable(Queue.BuildableItem bi) {
    try {
      StatsQueue queue = getCiQueue(bi);
      if (bi.getCauseOfBlockage() != null) {
        addExitQueueCause("buildable", bi, queue);
      }
      //PUT URL is pointing to /api/queues instead of/api/queues/id
      // as id will be reset to 1 each time you restart the Jenkins CI.
      // Hence the logic to update the correct queue record should be handled
      // at client side.
      RestClientUtil.putToService(getRestUrl(), queue);
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, "JOb "+bi.task.getFullDisplayName()+
          " with queue id "+bi.id+
          "failed with exception : " + e);
    }
  }

  /**
   * Construct REST API url for queue resource.
   *
   * @return
   */
  private String getRestUrl() {
    String endPoint = PropertyLoader.getStatsEndPoint();
    String queueRes = PropertyLoader.getEnvironmentProperty(
        "statistics.resource.queue");
    return endPoint + queueRes;
  }

  /**
   * Returns a queue model object from Jenkins queue.
   *
   * @param wi
   * @return
   */
  private StatsQueue getCiQueue(Item wi) {
    StatsQueue queue = new StatsQueue();
    String ciUrl = Jenkins.getInstance() != null
        ? Jenkins.getInstance().getRootUrl()
        : "";
    queue.setCiUrl(ciUrl);
    queue.setJobName(wi.task.getFullDisplayName());
    queue.setJenkinsQueueId(wi.id);
    return queue;
  }

  /**
   * Adds the Started By information to the Queue.
   *
   * @param wi
   * @param queue
   */
  private void addStartedBy(Item wi, StatsQueue queue) {
    List<Cause> causes = wi.getCauses();
    for (Cause cause : causes) {
     if (cause instanceof Cause.UserIdCause
         || cause instanceof Cause.UserCause) {
       queue.setStartedBy(((Cause.UserIdCause) cause).getUserName());
       break;
     } else if (cause instanceof Cause.UpstreamCause) {
       queue.setStartedBy(JenkinsCauses.UPSTREAM);
       break;
     } else if (cause instanceof SCMTrigger.SCMTriggerCause) {
       queue.setStartedBy(JenkinsCauses.SCM);
       break;
     } else if (cause instanceof TimerTrigger.TimerTriggerCause) {
       queue.setStartedBy(JenkinsCauses.TIMER);
       break;
     }
    }
  }


  @Override
  public void onLeft(Queue.LeftItem li) {
    try {
      StatsQueue queue = getCiQueue(li);
      // We have already set entry time in onEnterWaiting(). No need
      // to set it again.
      queue.setEntryTime(null);
      queue.setExitTime(new Date());
      queue.setStatus(Constants.LEFT);
      queue.setDurationStr(li.getInQueueForString());
      queue.setDuration(System.currentTimeMillis() - li.getInQueueSince());
      //PUT URL is pointing to /api/queues instead of/api/queues/id
      // as id will be reset to 1 each time you restart the Jenkins CI.
      // Hence the logic to update the correct queue record should be handled
      // at client side.
      RestClientUtil.putToService(getRestUrl(), queue);
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, "Failed to add Queue info for " +
          "job "+li.task.getFullDisplayName()+
          " with queue id "+li.id + " using "+ getRestUrl(), e);
    }
  }
}
