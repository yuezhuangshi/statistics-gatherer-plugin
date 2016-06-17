package org.jenkins.plugins.statistics.model;

import java.util.Date;

/**
 * Created by hthakkallapally on 4/20/2015.
 */
public class QueueCause {

  private Date entryTime;

  private Date exitTime;

  private String reasonForWaiting;

  public Date getEntryTime() {
    return entryTime;
  }

  public void setEntryTime(Date entryTime) {
    this.entryTime = entryTime;
  }

  public Date getExitTime() {
    return exitTime;
  }

  public void setExitTime(Date exitTime) {
    this.exitTime = exitTime;
  }

  public String getReasonForWaiting() {
    return reasonForWaiting;
  }

  public void setReasonForWaiting(String reasonForWaiting) {
    this.reasonForWaiting = reasonForWaiting;
  }
}
