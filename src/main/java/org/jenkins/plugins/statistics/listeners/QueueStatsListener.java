package org.jenkins.plugins.statistics.listeners;

import hudson.Extension;
import hudson.model.Cause;
import hudson.model.Queue;
import hudson.model.Queue.Item;
import hudson.model.queue.QueueListener;
import hudson.triggers.SCMTrigger;
import hudson.triggers.TimerTrigger;
import jenkins.model.Jenkins;
import org.jenkins.plugins.statistics.model.QueueCause;
import org.jenkins.plugins.statistics.model.QueueStats;
import org.jenkins.plugins.statistics.util.Constants;
import org.jenkins.plugins.statistics.util.JenkinsCauses;
import org.jenkins.plugins.statistics.util.PropertyLoader;
import org.jenkins.plugins.statistics.util.RestClientUtil;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by hthakkallapally on 3/13/2015.
 */
@Extension
public class QueueStatsListener extends QueueListener {
    private static final Logger LOGGER = Logger.getLogger(
            QueueStatsListener.class.getName());

    public QueueStatsListener() {
        //Necessary for jenkins
    }

    @Override
    public void onEnterWaiting(Queue.WaitingItem waitingItemi) {
        try {
            waitingItemi.getInQueueSince();
            QueueStats queue = getCiQueue(waitingItemi);
            addStartedBy(waitingItemi, queue);
            queue.setEntryTime(new Date());
            queue.setExitTime(null);
            queue.setStatus(Constants.ENTERED);
            if (waitingItemi.getCauseOfBlockage() != null) {
                addEntryQueueCause("waiting", waitingItemi, queue);
            }
            RestClientUtil.postToService(getRestUrl(), queue);
        } catch (Exception e) {
            logExceptionWaiting(waitingItemi, e);
        }
    }

    private void logExceptionWaiting(Queue.WaitingItem waitingItemi, Exception e) {
        LOGGER.log(Level.WARNING, "Failed to add Queue info for " +
                "job " + waitingItemi.task.getFullDisplayName() +
                " with queue id " + waitingItemi.getId() + " using " + getRestUrl(), e);
    }

    private void addEntryQueueCause(String type, Item item,
                                    QueueStats queue) {
        QueueCause cause = new QueueCause();
        cause.setType(type);
        cause.setEntryTime(new Date());
        cause.setExitTime(null);
        cause.setReasonForWaiting(item.getCauseOfBlockage().getShortDescription());
        queue.addQueueCause(cause);
    }

    @Override
    public void onLeaveWaiting(Queue.WaitingItem waitingItem) {
        try {
            waitingItem.getInQueueSince();
            QueueStats queue = getCiQueue(waitingItem);
            if (waitingItem.getCauseOfBlockage() != null) {
                addExitQueueCause("waiting", waitingItem, queue);
            }
            RestClientUtil.postToService(getRestUrl(), queue);
        } catch (Exception e) {
            logExceptionWaiting(waitingItem, e);
        }
    }

    private void addExitQueueCause(String type, Item item, QueueStats queue) {
        QueueCause cause = new QueueCause();
        cause.setType(type);
        cause.setEntryTime(null);
        cause.setExitTime(new Date());
        cause.setReasonForWaiting(item.getCauseOfBlockage().getShortDescription());
        queue.addQueueCause(cause);
    }

    /**
     * onEnterBlocked is used to update Reason for waiting in Queue.
     * for ex. "Build #35 is already in progress (ETA:9 min 3 sec)"
     *
     * @param blockedItem
     */
    @Override
    public void onEnterBlocked(Queue.BlockedItem blockedItem) {
        try {
            QueueStats queue = getCiQueue(blockedItem);
            if (blockedItem.getCauseOfBlockage() != null) {
                addEntryQueueCause("blocked", blockedItem, queue);
            }
            RestClientUtil.postToService(getRestUrl(), queue);
        } catch (Exception e) {
            logExceptionBlocked(blockedItem, e);
        }
    }

    private void logExceptionBlocked(Queue.BlockedItem blockedItem, Exception e) {
        LOGGER.log(Level.WARNING, "Job " + blockedItem.task.getFullDisplayName() +
                " with queue id " + blockedItem.getId() +
                "failed with exception : " + e);
    }

    @Override
    public void onLeaveBlocked(Queue.BlockedItem blockedItem) {
        try {
            QueueStats queue = getCiQueue(blockedItem);
            if (blockedItem.getCauseOfBlockage() != null) {
                addExitQueueCause("blocked", blockedItem, queue);
            }

            RestClientUtil.postToService(getRestUrl(), queue);
        } catch (Exception e) {
            logExceptionBlocked(blockedItem, e);
        }
    }

    /**
     * onEnterBlocked is used to update Reason for waiting in Queue.
     * for ex. "Waiting for next available executor"
     *
     * @param buildableItem
     */
    @Override
    public void onEnterBuildable(Queue.BuildableItem buildableItem) {
        try {
            QueueStats queue = getCiQueue(buildableItem);
            if (buildableItem.getCauseOfBlockage() != null) {
                addEntryQueueCause("buildable", buildableItem, queue);
            }
            RestClientUtil.postToService(getRestUrl(), queue);
        } catch (Exception e) {
            logExceptionLeave(buildableItem, e);
        }
    }

    @Override
    public void onLeaveBuildable(Queue.BuildableItem buildableItem) {
        try {
            QueueStats queue = getCiQueue(buildableItem);
            if (buildableItem.getCauseOfBlockage() != null) {
                addExitQueueCause("buildable", buildableItem, queue);
            }
            RestClientUtil.postToService(getRestUrl(), queue);
        } catch (Exception e) {
            logExceptionLeave(buildableItem, e);
        }
    }

    private void logExceptionLeave(Queue.BuildableItem buildableItem, Exception e) {
        LOGGER.log(Level.WARNING, "JOb " + buildableItem.task.getFullDisplayName() +
                " with queue id " + buildableItem.getId() +
                "failed with exception : " + e);
    }

    /**
     * Construct REST API url for queue resource.
     *
     * @return
     */
    private String getRestUrl() {
        return PropertyLoader.getQueueEndPoint();
    }

    /**
     * Returns a queue model object from Jenkins queue.
     *
     * @param item
     * @return
     */
    private QueueStats getCiQueue(Item item) {
        QueueStats queue = new QueueStats();
        String ciUrl = Jenkins.getInstance() != null
                ? Jenkins.getInstance().getRootUrl()
                : "";
        queue.setCiUrl(ciUrl);
        queue.setJobName(item.task.getFullDisplayName());
        queue.setJenkinsQueueId((int) item.getId());
        return queue;
    }

    /**
     * Adds the Started By information to the Queue.
     *
     * @param item
     * @param queue
     */
    private void addStartedBy(Item item, QueueStats queue) {
        List<Cause> causes = item.getCauses();
        for (Cause cause : causes) {
            if (cause instanceof Cause.UserIdCause) {
                queue.setStartedBy(((Cause.UserIdCause) cause).getUserName());
            } else if (cause instanceof Cause.UpstreamCause) {
                queue.setStartedBy(JenkinsCauses.UPSTREAM);
            } else if (cause instanceof SCMTrigger.SCMTriggerCause) {
                queue.setStartedBy(JenkinsCauses.SCM);
            } else if (cause instanceof TimerTrigger.TimerTriggerCause) {
                queue.setStartedBy(JenkinsCauses.TIMER);
            }
        }
    }


    @Override
    public void onLeft(Queue.LeftItem leftItem) {
        try {
            QueueStats queue = getCiQueue(leftItem);
            // We have already set entry time in onEnterWaiting(). No need
            // to set it again.
            queue.setEntryTime(null);
            queue.setExitTime(new Date());
            queue.setStatus(Constants.LEFT);
            queue.setDurationStr(leftItem.getInQueueForString());
            queue.setDuration(System.currentTimeMillis() - leftItem.getInQueueSince());
            RestClientUtil.postToService(getRestUrl(), queue);
        } catch (Exception e) {
            logExceptionLeft(leftItem, e);
        }
    }

    private void logExceptionLeft(Queue.LeftItem leftItem, Exception e) {
        LOGGER.log(Level.WARNING, "Failed to add Queue info for " +
                "job " + leftItem.task.getFullDisplayName() +
                " with queue id " + leftItem.getId() + " using " + getRestUrl(), e);
    }
}
