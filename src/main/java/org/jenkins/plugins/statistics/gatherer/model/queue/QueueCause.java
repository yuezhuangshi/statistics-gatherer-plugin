package org.jenkins.plugins.statistics.gatherer.model.queue;

import java.time.LocalDateTime;

/**
 * Created by hthakkallapally on 4/20/2015.
 */
public class QueueCause {

    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private String reasonForWaiting;
    private String type;

    public QueueCause(LocalDateTime entryTime,
                      LocalDateTime exitTime,
                      String reasonForWaiting,
                      String type) {
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.reasonForWaiting = reasonForWaiting;
        this.type = type;
    }

    public QueueCause() {
        this.entryTime = LocalDateTime.now();
        this.exitTime = LocalDateTime.now();
        this.reasonForWaiting = "";
        this.type = "";
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public String getReasonForWaiting() {
        return reasonForWaiting;
    }

    public void setReasonForWaiting(String reasonForWaiting) {
        this.reasonForWaiting = reasonForWaiting;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
