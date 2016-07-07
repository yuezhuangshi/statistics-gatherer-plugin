package org.jenkins.plugins.statistics.gatherer;

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
    public static final String PROTOCOL_ERROR_MESSAGE = "Only http and https protocols are supported";

    private String queueUrl;
    private String buildUrl;
    private String projectUrl;

    public StatisticsConfiguration() {
        load();
    }

    public static StatisticsConfiguration get() {
        return GlobalConfiguration.all().get(StatisticsConfiguration.class);
    }

    public String getQueueUrl() {
        if (queueUrl != null && !queueUrl.isEmpty()) {
            if (queueUrl.endsWith(SLASH)) {
                return queueUrl;
            }
            return queueUrl + SLASH;
        }
        return queueUrl;
    }

    public void setQueueUrl(String queueUrl) {
        this.queueUrl = queueUrl;
        save();
    }

    public String getBuildUrl() {
        if (buildUrl != null && !buildUrl.isEmpty()) {
            if (buildUrl.endsWith(SLASH)) {
                return buildUrl;
            }
            return buildUrl + SLASH;
        }
        return buildUrl;
    }

    public void setBuildUrl(String buildUrl) {
        this.buildUrl = buildUrl;
        save();
    }

    public String getProjectUrl() {
        if (projectUrl != null && !projectUrl.isEmpty()) {
            if (projectUrl.endsWith(SLASH)) {
                return projectUrl;
            }
            return projectUrl + SLASH;
        }
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
        save();
    }

    @Override
    public boolean configure(StaplerRequest request, JSONObject json) throws FormException {
        request.bindJSON(this, json);
        return true;
    }

    /**
     * Validate notificationUrl is filled correctly.
     *
     * @param buildUrl
     * @return
     */
    public FormValidation doCheckBuildUrl(
            @QueryParameter("buildUrl") final String buildUrl) {
        if (buildUrl == null || buildUrl.isEmpty()) {
            return FormValidation.error("Provide valid Build URL. " +
                    "For ex: \"http://ci.mycompany.com/api/builds\"");
        }
        if (validateProtocolUsed(buildUrl))
            return FormValidation.error(PROTOCOL_ERROR_MESSAGE);
        return FormValidation.ok();
    }

    public FormValidation doCheckQueueUrl(
            @QueryParameter("queueUrl") final String queueUrl) {
        if (queueUrl == null || queueUrl.isEmpty()) {
            return FormValidation.error("Provide valid Queue URL. " +
                    "For ex: \"http://ci.mycompany.com/api/queues\"");
        }
        if (validateProtocolUsed(queueUrl))
            return FormValidation.error(PROTOCOL_ERROR_MESSAGE);
        return FormValidation.ok();
    }

    public FormValidation doCheckProjectUrl(
            @QueryParameter("projectUrl") final String projectUrl) {
        if (projectUrl == null || projectUrl.isEmpty()) {
            return FormValidation.error("Provide valid Project URL. " +
                    "For ex: \"http://ci.mycompany.com/api/\"");
        }
        if (validateProtocolUsed(projectUrl))
            return FormValidation.error(PROTOCOL_ERROR_MESSAGE);
        return FormValidation.ok();
    }

    private boolean validateProtocolUsed(@QueryParameter("projectUrl") String projectUrl) {
        if (!(projectUrl.startsWith("http://") || projectUrl.startsWith("https://"))) {
            return true;
        }
        return false;
    }
}
