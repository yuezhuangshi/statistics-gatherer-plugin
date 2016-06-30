package org.jenkins.plugins.statistics.util;

import hudson.EnvVars;
import hudson.slaves.EnvironmentVariablesNodeProperty;
import hudson.slaves.NodeProperty;
import hudson.slaves.NodePropertyDescriptor;
import hudson.util.DescribableList;
import jenkins.model.Jenkins;
import org.jenkins.plugins.statistics.StatisticsConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.util.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;


/**
 * Created by mcharron on 2016-06-29.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({StatisticsConfiguration.class, ResourceBundle.class, Jenkins.class})
public class PropertyLoaderTest {

    @Test
    public void givenNoInstance_whenGetInstance_thenReturnInstance(){
        //given
        //when
        PropertyLoader propertyLoader = PropertyLoader.getInstance();

        //then
        assertNotNull(propertyLoader);
    }

    @Test
    public void givenInstance_whenGetInstance_thenReturnInstance(){
        //given
        //To create instance
        PropertyLoader propertyLoader = PropertyLoader.getInstance();

        //when
        PropertyLoader samePropertyLoader = PropertyLoader.getInstance();

        //then
        assertNotNull(propertyLoader);
        assertNotNull(samePropertyLoader);
        assertEquals(propertyLoader, samePropertyLoader);
    }

    @Test
    public void givenResourceBundleProperty_whenGetKey_thenReturnKeyString(){
        //given
        //when
        String property = PropertyLoader.getInstance().getResourceBundleProperty("statistics.endpoint.queueUrl");
        //then
        assertNotNull(property);
        assertEquals("http://cistats.mycompany.com/api/queues", property);
    }

    @Test
    public void givenQueueUrl_whenGetQueueUrlEndpoint_thenReturnQueueUrl(){
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getQueueUrl()).thenReturn("statistics.endpoint.queueUrl");
        //when
        String url = PropertyLoader.getQueueEndPoint();

        //then
        assertEquals("statistics.endpoint.queueUrl", url);
    }


    @Test
    public void givenNoQueueUrl_whenGetQueueUrlEndpoint_thenReturnQueueUrl(){
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getQueueUrl()).thenReturn(null);
        Jenkins jenkincMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(new ArrayList<EnvironmentVariablesNodeProperty>());
        Mockito.when(jenkincMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkincMock);
        //when
        String url = PropertyLoader.getQueueEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/queues", url);
    }

    @Test
    public void givenEnvVars_whenGetQueueUrlEndpoint_thenReturnQueueUrl(){
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getQueueUrl()).thenReturn(null);
        Jenkins jenkincMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        List<EnvironmentVariablesNodeProperty> environmentVariablesNodePropertyList = new ArrayList<EnvironmentVariablesNodeProperty>();
        EnvironmentVariablesNodeProperty envVar = mock(EnvironmentVariablesNodeProperty.class);
        Map<String, String> envVarMap = new HashMap<String, String>();
        envVarMap.put("statistics.endpoint.queueUrl", "http://cistats.mycompany.com/api/");
        Mockito.when(envVar.getEnvVars()).thenReturn(new EnvVars(envVarMap));
        environmentVariablesNodePropertyList.add(envVar);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(environmentVariablesNodePropertyList);
        Mockito.when(jenkincMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkincMock);
        //when
        String url = PropertyLoader.getQueueEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/", url);
    }

    @Test
    public void givenEmptyEnvVars_whenGetQueueUrlEndpoint_thenReturnQueueUrl(){
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getQueueUrl()).thenReturn(null);
        Jenkins jenkincMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        List<EnvironmentVariablesNodeProperty> environmentVariablesNodePropertyList = new ArrayList<EnvironmentVariablesNodeProperty>();
        EnvironmentVariablesNodeProperty envVar = mock(EnvironmentVariablesNodeProperty.class);
        Map<String, String> envVarMap = new HashMap<String, String>();
        envVarMap.put("statistics.endpoint.queueUrl", "");
        Mockito.when(envVar.getEnvVars()).thenReturn(new EnvVars(envVarMap));
        environmentVariablesNodePropertyList.add(envVar);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(environmentVariablesNodePropertyList);
        Mockito.when(jenkincMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkincMock);
        //when
        String url = PropertyLoader.getQueueEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/queues", url);
    }

    @Test
    public void givenEmptyQueueUrl_whenGetQueueUrlEndpoint_thenReturnQueueUrl(){
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getQueueUrl()).thenReturn("");
        Jenkins jenkinSMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(new ArrayList<EnvironmentVariablesNodeProperty>());
        Mockito.when(jenkinSMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinSMock);
        //when
        String url = PropertyLoader.getQueueEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/queues", url);
    }

    @Test
    public void givenBuildUrl_whenGetBuildUrlEndpoint_thenReturnBuildUrl(){
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getBuildUrl()).thenReturn("statistics.endpoint.buildUrl");
        //when
        String url = PropertyLoader.getBuildEndPoint();

        //then
        assertEquals("statistics.endpoint.buildUrl", url);
    }


    @Test
    public void givenNoBuildUrl_whenGetBuildUrlEndpoint_thenReturnBuildUrl(){
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getBuildUrl()).thenReturn(null);
        Jenkins jenkincMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(new ArrayList<EnvironmentVariablesNodeProperty>());
        Mockito.when(jenkincMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkincMock);
        //when
        String url = PropertyLoader.getBuildEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/builds", url);
    }

    @Test
    public void givenEnvVars_whenGetBuildUrlEndpoint_thenReturnBuildUrl(){
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getBuildUrl()).thenReturn(null);
        Jenkins jenkincMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        List<EnvironmentVariablesNodeProperty> environmentVariablesNodePropertyList = new ArrayList<EnvironmentVariablesNodeProperty>();
        EnvironmentVariablesNodeProperty envVar = mock(EnvironmentVariablesNodeProperty.class);
        Map<String, String> envVarMap = new HashMap<String, String>();
        envVarMap.put("statistics.endpoint.buildUrl", "http://cistats.mycompany.com/api/");
        Mockito.when(envVar.getEnvVars()).thenReturn(new EnvVars(envVarMap));
        environmentVariablesNodePropertyList.add(envVar);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(environmentVariablesNodePropertyList);
        Mockito.when(jenkincMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkincMock);
        //when
        String url = PropertyLoader.getBuildEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/", url);
    }

    @Test
    public void givenEmptyEnvVars_whenGetBuildUrlEndpoint_thenReturnBuildUrl(){
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getBuildUrl()).thenReturn(null);
        Jenkins jenkincMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        List<EnvironmentVariablesNodeProperty> environmentVariablesNodePropertyList = new ArrayList<EnvironmentVariablesNodeProperty>();
        EnvironmentVariablesNodeProperty envVar = mock(EnvironmentVariablesNodeProperty.class);
        Map<String, String> envVarMap = new HashMap<String, String>();
        envVarMap.put("statistics.endpoint.buildUrl", "");
        Mockito.when(envVar.getEnvVars()).thenReturn(new EnvVars(envVarMap));
        environmentVariablesNodePropertyList.add(envVar);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(environmentVariablesNodePropertyList);
        Mockito.when(jenkincMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkincMock);
        //when
        String url = PropertyLoader.getBuildEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/builds", url);
    }

    @Test
    public void givenEmptyBuildUrl_whenGetBuildUrlEndpoint_thenReturnBuildUrl(){
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getBuildUrl()).thenReturn("");
        Jenkins jenkinSMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(new ArrayList<EnvironmentVariablesNodeProperty>());
        Mockito.when(jenkinSMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinSMock);
        //when
        String url = PropertyLoader.getBuildEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/builds", url);
    }

    @Test
    public void givenProjectUrl_whenGetProjectUrlEndpoint_thenReturnProjectUrl(){
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getProjectUrl()).thenReturn("statistics.endpoint.projectUrl");
        //when
        String url = PropertyLoader.getProjectEndPoint();

        //then
        assertEquals("statistics.endpoint.projectUrl", url);
    }


    @Test
    public void givenNoProjectUrl_whenGetProjectUrlEndpoint_thenReturnProjectUrl(){
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getProjectUrl()).thenReturn(null);
        Jenkins jenkincMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(new ArrayList<EnvironmentVariablesNodeProperty>());
        Mockito.when(jenkincMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkincMock);
        //when
        String url = PropertyLoader.getProjectEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/projects", url);
    }

    @Test
    public void givenEnvVars_whenGetProjectUrlEndpoint_thenReturnProjectUrl(){
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getProjectUrl()).thenReturn(null);
        Jenkins jenkincMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        List<EnvironmentVariablesNodeProperty> environmentVariablesNodePropertyList = new ArrayList<EnvironmentVariablesNodeProperty>();
        EnvironmentVariablesNodeProperty envVar = mock(EnvironmentVariablesNodeProperty.class);
        Map<String, String> envVarMap = new HashMap<String, String>();
        envVarMap.put("statistics.endpoint.projectUrl", "http://cistats.mycompany.com/api/");
        Mockito.when(envVar.getEnvVars()).thenReturn(new EnvVars(envVarMap));
        environmentVariablesNodePropertyList.add(envVar);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(environmentVariablesNodePropertyList);
        Mockito.when(jenkincMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkincMock);
        //when
        String url = PropertyLoader.getProjectEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/", url);
    }

    @Test
    public void givenEmptyEnvVars_whenGetProjectUrlEndpoint_thenReturnProjectUrl(){
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getProjectUrl()).thenReturn(null);
        Jenkins jenkincMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        List<EnvironmentVariablesNodeProperty> environmentVariablesNodePropertyList = new ArrayList<EnvironmentVariablesNodeProperty>();
        EnvironmentVariablesNodeProperty envVar = mock(EnvironmentVariablesNodeProperty.class);
        Map<String, String> envVarMap = new HashMap<String, String>();
        envVarMap.put("statistics.endpoint.projectUrl", "");
        Mockito.when(envVar.getEnvVars()).thenReturn(new EnvVars(envVarMap));
        environmentVariablesNodePropertyList.add(envVar);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(environmentVariablesNodePropertyList);
        Mockito.when(jenkincMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkincMock);
        //when
        String url = PropertyLoader.getProjectEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/projects", url);
    }

    @Test
    public void givenEmptyProjectUrl_whenGetProjectUrlEndpoint_thenReturnProjectUrl(){
        //given
        PowerMockito.mockStatic(StatisticsConfiguration.class);
        PowerMockito.mockStatic(Jenkins.class);
        Mockito.when(StatisticsConfiguration.get()).thenReturn(mock(StatisticsConfiguration.class));
        Mockito.when(StatisticsConfiguration.get().getProjectUrl()).thenReturn("");
        Jenkins jenkinSMock = mock(Jenkins.class);
        DescribableList<NodeProperty<?>, NodePropertyDescriptor> describableList = mock(DescribableList.class);
        Mockito.when(describableList.getAll(EnvironmentVariablesNodeProperty.class)).thenReturn(new ArrayList<EnvironmentVariablesNodeProperty>());
        Mockito.when(jenkinSMock.getGlobalNodeProperties()).thenReturn(describableList);
        Mockito.when(Jenkins.getInstance()).thenReturn(jenkinSMock);
        //when
        String url = PropertyLoader.getProjectEndPoint();

        //then
        assertEquals("http://cistats.mycompany.com/api/projects", url);
    }

    @Test
    public void givenNoKey_whenGetProperty_thenReturnNull(){
        //given
        //when
        String property = PropertyLoader.getInstance().getProperty(null);
        //then
        assertNull(property);
    }

    @Test
    public void givenEmptyKey_whenGetProperty_thenReturnNull(){
        //given
        //when
        String property = PropertyLoader.getInstance().getProperty("");
        //then
        assertNull(property);
    }
}
