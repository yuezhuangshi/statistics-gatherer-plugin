package org.jenkins.plugins.statistics.gatherer.util;

import com.google.common.base.Strings;
import hudson.EnvVars;
import hudson.slaves.EnvironmentVariablesNodeProperty;
import hudson.slaves.NodeProperty;
import hudson.slaves.NodePropertyDescriptor;
import hudson.util.DescribableList;
import jenkins.model.Jenkins;
import org.jenkins.plugins.statistics.gatherer.StatisticsConfiguration;

import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class PropertyLoader {

    public static final String DEFAULT_PROPERTY_FILE_NAME = "statistics";
    private static PropertyLoader instance = null;
    private final ResourceBundle resourceBundle;

    private PropertyLoader() {
        resourceBundle = ResourceBundle.getBundle(DEFAULT_PROPERTY_FILE_NAME);
    }

    public static synchronized PropertyLoader getInstance() {
        if (instance == null) {
            setInstance(new PropertyLoader());
        }
        return instance;
    }

    public static synchronized void setInstance(final PropertyLoader propertyLoader) {
        instance = propertyLoader;
    }

    protected String getResourceBundleProperty(String keyProperty) {
        try {
            return resourceBundle.getString(keyProperty);
        } catch (MissingResourceException e) {
            return null;
        }
    }

    public String getProperty(final String inKey) {
        if (inKey == null) {
            return null;
        }
        final String key = inKey.trim();
        if (key.length() <= 0) {
            return null;
        }
        final EnvVars environmentVariables = new EnvVars();
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> globalNodeProperties =
                Jenkins.get().getGlobalNodeProperties();
        List<EnvironmentVariablesNodeProperty> properties =
                globalNodeProperties.getAll(EnvironmentVariablesNodeProperty.class);
        for (EnvironmentVariablesNodeProperty environmentVariablesNodeProperty : properties) {
            environmentVariables.putAll(environmentVariablesNodeProperty.getEnvVars());
        }
        String value = environmentVariables.get(key);
        if (!Strings.isNullOrEmpty(value)) {
            return value;
        }
        value = environmentVariables.get(key.toLowerCase());
        if (!Strings.isNullOrEmpty(value)) {
            return value;
        }

        value = getResourceBundleProperty(key);
        if (!Strings.isNullOrEmpty(value)) {
            return value;
        }
        return getResourceBundleProperty(key.toLowerCase());
    }

    public static String getEnvironmentProperty(final String key) {
        return getInstance().getProperty(key);
    }

    public static boolean isTrue(String value) {
        return value != null && "true".equals(value.toLowerCase());
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

    public static Boolean getBuildInfo() {
        Boolean buildInfo = StatisticsConfiguration.get().getBuildInfo();
        if (buildInfo != null) {
            return buildInfo;
        }
        return isTrue(getEnvironmentProperty("statistics.endpoint.buildInfo"));
    }

    public static Boolean getProjectInfo() {
        Boolean projectInfo = StatisticsConfiguration.get().getProjectInfo();
        if (projectInfo != null) {
            return projectInfo;
        }
        return isTrue(getEnvironmentProperty("statistics.endpoint.projectInfo"));
    }

    public static Boolean getShouldSendApiHttpRequests() {
        Boolean shouldSendApiHttpRequests = StatisticsConfiguration.get().getShouldSendApiHttpRequests();
        if (shouldSendApiHttpRequests != null) {
            return shouldSendApiHttpRequests;
        }
        return isTrue(getEnvironmentProperty("statistics.endpoint.shouldSendApiHttpRequests"));
    }

}
