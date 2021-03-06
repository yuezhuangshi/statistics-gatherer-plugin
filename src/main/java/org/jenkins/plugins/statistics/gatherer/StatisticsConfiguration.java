package org.jenkins.plugins.statistics.gatherer;

import hudson.Extension;
import hudson.util.FormValidation;
import jenkins.YesNoMaybe;
import jenkins.model.GlobalConfiguration;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

/**
 * Created by hthakkallapally on 6/25/2015.
 */
@Extension(dynamicLoadable = YesNoMaybe.YES)
public class StatisticsConfiguration extends GlobalConfiguration {

    public static final String PROTOCOL_ERROR_MESSAGE = "Only http and https protocols are supported";

    private String queueUrl;
    private String buildUrl;
    private String projectUrl;
    private String scmCheckoutUrl;
    private String buildStepUrl;

    private Boolean queueInfo;
    private Boolean buildInfo;
    private Boolean projectInfo;
    private Boolean scmCheckoutInfo;
    private Boolean buildStepInfo;
    private Boolean shouldSendApiHttpRequests;

    public StatisticsConfiguration() {
        load();
    }

    public static StatisticsConfiguration get() {
        return GlobalConfiguration.all().get(StatisticsConfiguration.class);
    }

    public String getQueueUrl() {
        return queueUrl;
    }

    public Boolean getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(Boolean projectInfo) {
        this.projectInfo = projectInfo;
        save();
    }

    public Boolean getBuildInfo() {
        return buildInfo;
    }

    public void setBuildInfo(Boolean buildInfo) {
        this.buildInfo = buildInfo;
        save();
    }

    public Boolean getQueueInfo() {
        return queueInfo;
    }

    public void setQueueInfo(Boolean queueInfo) {
        this.queueInfo = queueInfo;
        save();
    }

    public Boolean getBuildStepInfo() {
        return buildStepInfo;
    }

    public void setBuildStepInfo(Boolean buildStepInfo) {
        this.buildStepInfo = buildStepInfo;
        save();
    }

    public Boolean getScmCheckoutInfo() {
        return scmCheckoutInfo;
    }

    public void setScmCheckoutInfo(Boolean scmCheckoutInfo) {
        this.scmCheckoutInfo = scmCheckoutInfo;
        save();
    }

    public void setQueueUrl(String queueUrl) {
        this.queueUrl = queueUrl;
        save();
    }

    public String getBuildUrl() {
        return buildUrl;
    }

    public void setBuildUrl(String buildUrl) {
        this.buildUrl = buildUrl;
        save();
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
        save();
    }

    public String getBuildStepUrl() {
        return buildStepUrl;
    }

    public void setBuildStepUrl(String buildStepUrl) {
        this.buildStepUrl = buildStepUrl;
        save();
    }

    public String getScmCheckoutUrl() {
        return scmCheckoutUrl;
    }

    public void setScmCheckoutUrl(String scmCheckoutUrl) {
        this.scmCheckoutUrl = scmCheckoutUrl;
        save();
    }

    public Boolean getShouldSendApiHttpRequests() { return shouldSendApiHttpRequests; }

    public void setShouldSendApiHttpRequests(Boolean shouldSendApiHttpRequests) {
        this.shouldSendApiHttpRequests = shouldSendApiHttpRequests;
        save();
    }

    @Override
    public boolean configure(StaplerRequest request, JSONObject json) {
        request.bindJSON(this, json);
        return true;
    }

    public FormValidation doCheckBuildUrl(
            @QueryParameter("buildUrl") final String buildUrl) {
        if (buildUrl == null || buildUrl.isEmpty()) {
            return FormValidation.error("Provide valid Build URL. " +
                    "For example: \"http://cistats.mycompany.com/api/build\"");
        }
        if (validateProtocolUsed(buildUrl)) {
            return FormValidation.error(PROTOCOL_ERROR_MESSAGE);
        }
        return FormValidation.ok();
    }

    public FormValidation doCheckQueueUrl(
            @QueryParameter("queueUrl") final String queueUrl) {
        if (queueUrl == null || queueUrl.isEmpty()) {
            return FormValidation.error("Provide valid Queue URL. " +
                    "For example: \"http://cistats.mycompany.com/api/queue\"");
        }
        if (validateProtocolUsed(queueUrl)) {
            return FormValidation.error(PROTOCOL_ERROR_MESSAGE);
        }
        return FormValidation.ok();
    }

    public FormValidation doCheckProjectUrl(
            @QueryParameter("projectUrl") final String projectUrl) {
        if (projectUrl == null || projectUrl.isEmpty()) {
            return FormValidation.error("Provide valid Project URL. " +
                    "For example: \"http://cistats.mycompany.com/api/project\"");
        }
        if (validateProtocolUsed(projectUrl)) {
            return FormValidation.error(PROTOCOL_ERROR_MESSAGE);
        }
        return FormValidation.ok();
    }

    public FormValidation doCheckBuildStepUrl(
            @QueryParameter("buildStepUrl") final String buildStepUrl) {
        if (buildStepUrl == null || buildStepUrl.isEmpty()) {
            return FormValidation.error("Provide valid Build Step URL. " +
                    "For example: \"http://cistats.mycompany.com/api/step\"");
        }
        if (validateProtocolUsed(buildStepUrl)) {
            return FormValidation.error(PROTOCOL_ERROR_MESSAGE);
        }
        return FormValidation.ok();
    }

    public FormValidation doCheckScmCheckoutUrl(
            @QueryParameter("scmCheckoutUrl") final String scmCheckoutUrl) {
        if (scmCheckoutUrl == null || scmCheckoutUrl.isEmpty()) {
            return FormValidation.error("Provide valid SCM Checkout URL. " +
                    "For example: \"http://cistats.mycompany.com/api/scm\"");
        }
        if (validateProtocolUsed(scmCheckoutUrl)) {
            return FormValidation.error(PROTOCOL_ERROR_MESSAGE);
        }
        return FormValidation.ok();
    }

    public FormValidation doCheckBuildInfo(
            @QueryParameter("buildInfo") final Boolean buildInfo) {
        if (buildInfo == null) {
            return FormValidation.error("Provide valid Build Info. ");
        }
        return FormValidation.ok();
    }

    public FormValidation doCheckQueueInfo(
            @QueryParameter("queueInfo") final Boolean queueInfo) {
        if (queueInfo == null) {
            return FormValidation.error("Provide valid Queue Info. ");
        }
        return FormValidation.ok();
    }

    public FormValidation doCheckProjectInfo(
            @QueryParameter("projectInfo") final Boolean projectInfo) {
        if (projectInfo == null) {
            return FormValidation.error("Provide valid Project Info. ");
        }
        return FormValidation.ok();
    }

    public FormValidation doCheckBuildStepInfo(
            @QueryParameter("buildStepInfo") final Boolean buildStepInfo) {
        if (buildStepInfo == null) {
            return FormValidation.error("Provide valid Build Step Info. ");
        }
        return FormValidation.ok();
    }

    public FormValidation doCheckScmCheckoutInfo(
            @QueryParameter("scmCheckoutInfo") final Boolean scmCheckoutInfo) {
        if (scmCheckoutInfo == null) {
            return FormValidation.error("Provide valid SCM Checkout Info. ");
        }
        return FormValidation.ok();
    }

    private boolean validateProtocolUsed(String url) {
        return !(url.startsWith("http://") || url.startsWith("https://"));
    }

}
