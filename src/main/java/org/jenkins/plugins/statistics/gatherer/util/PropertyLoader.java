package org.jenkins.plugins.statistics.gatherer.util;

import hudson.EnvVars;
import hudson.slaves.EnvironmentVariablesNodeProperty;
import hudson.slaves.NodeProperty;
import hudson.slaves.NodePropertyDescriptor;
import hudson.util.DescribableList;
import jenkins.model.Jenkins;
import org.jenkins.plugins.statistics.gatherer.StatisticsConfiguration;

import java.util.List;
import java.util.ResourceBundle;


public class PropertyLoader {
    public static final String DEFAULT_PROPERTY_FILE_NAME = "statistics";
    private static PropertyLoader instance = null;
    private final ResourceBundle resourceBundle;

    private PropertyLoader() {
        resourceBundle = ResourceBundle.getBundle(DEFAULT_PROPERTY_FILE_NAME);
    }

    public static final synchronized PropertyLoader getInstance() {
        if (instance == null) {
            instance = new PropertyLoader();
        }
        return instance;
    }

    public static final synchronized void setInstance(
            final PropertyLoader propertyLoader) {
        instance = propertyLoader;
    }

    protected String getResourceBundleProperty(String keyProperty) {
        return resourceBundle.getString(keyProperty);
    }

    public String getProperty(
            final String inKey) {
        if (inKey == null) {
            return null;
        }
        final String key = inKey.trim();
        if (key.length() <= 0) {
            return null;
        }
        final EnvVars environmentVariables = new EnvVars();
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> globalNodeProperties =
                Jenkins.getInstance().getGlobalNodeProperties();
        List<EnvironmentVariablesNodeProperty> properties =
                globalNodeProperties.getAll(EnvironmentVariablesNodeProperty.class);
        for (EnvironmentVariablesNodeProperty environmentVariablesNodeProperty : properties) {
            environmentVariables.putAll(environmentVariablesNodeProperty.getEnvVars());
        }
        final String value = environmentVariables.get(key);
        if (value == null || value.isEmpty()) {
            return getResourceBundleProperty(key);
        }
        return value;
    }

    public static String getEnvironmentProperty(
            final String key) {
        return getInstance().getProperty(key);
    }

    public static String getQueueEndPoint() {
        String endPoint = StatisticsConfiguration.get().getQueueUrl();
        if (endPoint != null && !endPoint.isEmpty()) {
            return endPoint;
        }
        endPoint = getEnvironmentProperty("statistics.endpoint.queueUrl");
        return endPoint == null ? "" : endPoint;
    }

    public static String getBuildEndPoint() {
        String endPoint = StatisticsConfiguration.get().getBuildUrl();
        if (endPoint != null && !endPoint.isEmpty()) {
            return endPoint;
        }
        endPoint = getEnvironmentProperty("statistics.endpoint.buildUrl");
        return endPoint == null ? "" : endPoint;
    }

    public static String getProjectEndPoint() {
        String endPoint = StatisticsConfiguration.get().getProjectUrl();
        if (endPoint != null && !endPoint.isEmpty()) {
            return endPoint;
        }
        endPoint = getEnvironmentProperty("statistics.endpoint.projectUrl");
        return endPoint == null ? "" : endPoint;
    }

    public static String getBuildStepEndPoint() {
        String endPoint = StatisticsConfiguration.get().getBuildStepUrl();
        if (endPoint != null && !endPoint.isEmpty()) {
            return endPoint;
        }
        endPoint = getEnvironmentProperty("statistics.endpoint.buildStepUrl");
        return endPoint == null ? "" : endPoint;
    }

    public static String getScmCheckoutEndPoint() {
        String endPoint = StatisticsConfiguration.get().getScmCheckoutUrl();
        if (endPoint != null && !endPoint.isEmpty()) {
            return endPoint;
        }
        endPoint = getEnvironmentProperty("statistics.endpoint.scmCheckoutUrl");
        return endPoint == null ? "" : endPoint;
    }

    public static Boolean getQueueInfo() {
        Boolean queueInfo = StatisticsConfiguration.get().getQueueInfo();
        if (queueInfo != null) {
            return queueInfo;
        }
        String queueInfoEnv = getEnvironmentProperty("statistics.endpoint.queueInfo");
        return "true".equals(queueInfoEnv);
    }

    public static Boolean getBuildInfo() {
        Boolean buildInfo = StatisticsConfiguration.get().getBuildInfo();
        if (buildInfo != null) {
            return buildInfo;
        }
        String buildInfoEnv = getEnvironmentProperty("statistics.endpoint.buildInfo");
        return "true".equals(buildInfoEnv);
    }

    public static Boolean getProjectInfo() {
        Boolean projectInfo = StatisticsConfiguration.get().getProjectInfo();
        if (projectInfo != null) {
            return projectInfo;
        }
        String projectInfoEnv = getEnvironmentProperty("statistics.endpoint.projectInfo");
        return "true".equals(projectInfoEnv);
    }

    public static Boolean getBuildStepInfo() {
        Boolean buildStepInfo = StatisticsConfiguration.get().getBuildStepInfo();
        if (buildStepInfo != null) {
            return buildStepInfo;
        }
        String buildStepInfoEnv = getEnvironmentProperty("statistics.endpoint.buildStepInfo");
        return "true".equals(buildStepInfoEnv);
    }

    public static Boolean getScmCheckoutInfo() {
        Boolean scmCheckoutInfo = StatisticsConfiguration.get().getScmCheckoutInfo();
        if (scmCheckoutInfo != null) {
            return scmCheckoutInfo;
        }
        String scmCheckoutInfoEnv = getEnvironmentProperty("statistics.endpoint.scmCheckoutInfo");
        return "true".equals(scmCheckoutInfoEnv);
    }
}