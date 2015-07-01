package org.jenkins.plugins.statistics;

import hudson.Extension;
import hudson.util.FormValidation;
import jenkins.model.GlobalConfiguration;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

/**
 * Created by hthakkallapally on 6/25/2015.
 */
@Extension
public class StatisticsConfiguration extends GlobalConfiguration {

  private static final String SLASH = "/";

  private String notificationUrl;
  private int connectionTimeout;
  private int socketTimeout;

  public static StatisticsConfiguration get() {
    return GlobalConfiguration.all().get(StatisticsConfiguration.class);
  }

  public StatisticsConfiguration() {
    load();
  }

  public String getNotificationUrl() {
    if (notificationUrl != null && !notificationUrl.isEmpty()) {
      if (notificationUrl.endsWith(SLASH)) {
        return notificationUrl;
      }
      return notificationUrl + SLASH;
    }
    return notificationUrl;
  }

  public void setNotificationUrl(String notificationUrl) {
    this.notificationUrl = notificationUrl;
    save();
  }

  public int getConnectionTimeout() {
    return connectionTimeout;
  }

  public void setConnectionTimeout(int connectionTimeout) {
    if (this.connectionTimeout <= 0) {
      this.connectionTimeout = 1000;
    } else {
      this.connectionTimeout = connectionTimeout;
    }
    save();
  }

  public int getSocketTimeout() {
    return socketTimeout;
  }

  public void setSocketTimeout(int socketTimeout) {
    if (socketTimeout <= 0) {
      this.socketTimeout = 1000;
    } else {
      this.socketTimeout = socketTimeout;
    }
    save();
  }

  @Override
  public boolean configure(StaplerRequest req, JSONObject json) throws FormException {
    req.bindJSON(this,json);
    return true;
  }

  /**
   * Validate notificationUrl is filled correctly.
   *
   * @param notificationUrl
   * @return
   */
  public FormValidation doCheckNotificationUrl(
      @QueryParameter("notificationUrl") final String notificationUrl) {
    if (notificationUrl == null || notificationUrl.isEmpty()) {
      return FormValidation.error("Provide valid Notification URL. " +
          "For ex: \"http://ci.mycompany.com/api/\"");
    }
    if (!(notificationUrl.startsWith("http://") || notificationUrl.startsWith("https://"))) {
      return FormValidation.error("Only http and https protocols are supported");
    }
    return FormValidation.ok();
  }

  public FormValidation doCheckSocketTimeout(
      @QueryParameter("socketTimeout") final int socketTimeout) {
    if (socketTimeout < 0) {
      return FormValidation.error("Provide timeout in milli seconds. For ex. 2000");
    }
    return FormValidation.ok();
  }
}
