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

    /**
     * This should only be called when the process is being setup.  It
     * is not intended for general use.
     */
    public static final synchronized void setInstance(
            final PropertyLoader propertyLoader) {
        instance = propertyLoader;
    }

    protected String getResourceBundleProperty(String keyProperty) {
        return resourceBundle.getString(keyProperty);
    }

    /**
     * Utility method to get the properties value
     * First it will try to get the property value from the vars supplied
     * then from the property file
     *
     * @param inKey for which value will be returned
     *              the properties are looked thru the property file
     * @return the value of the key
     * @throws Exception
     */
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

    /**
     * Utility method to get the properties value
     * First it will try to get the property value from the vars supplied
     * then from the property file
     *
     * @param key for which value will be returned
     * @return the value of the key
     * @throws Exception
     */
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
}