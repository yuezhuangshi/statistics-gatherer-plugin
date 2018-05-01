package org.jenkins.plugins.statistics.gatherer.util;


import hudson.Plugin;
import jenkins.model.Jenkins;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PropertyLoader.class, Jenkins.class})
public class LogbackImplTest {

    @Mock
    Jenkins jenkinsMock;

    Path logbackXml;

    @Before
    public void setup() throws IOException {
        mockStatic(PropertyLoader.class);
        mockStatic(Jenkins.class);

        logbackXml = Files.createTempFile(LogbackImplTest.class.getName(), ".xml");
        Files.write(logbackXml, ("<configuration>\n" +
                "\n" +
                "  <appender name=\"STDOUT\" class=\"ch.qos.logback.core.ConsoleAppender\">\n" +
                "    <!-- encoders are assigned the type\n" +
                "         ch.qos.logback.classic.encoder.PatternLayoutEncoder by default -->\n" +
                "    <encoder>\n" +
                "      <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>\n" +
                "    </encoder>\n" +
                "  </appender>\n" +
                "\n" +
                "  <root level=\"debug\">\n" +
                "    <appender-ref ref=\"STDOUT\" />\n" +
                "  </root>\n" +
                "</configuration>").getBytes());
    }

    @Test
    public void givenLogbackAppender_whenLogbackIsConfigured_thenLoggerIsCreated() throws MalformedURLException {
        when(PropertyLoader.getLogbackConfigXmlUrl()).thenReturn(logbackXml.toFile().toURI().toURL().toString());
        when(PropertyLoader.getShouldSendToLogback()).thenReturn(true);
        when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        Plugin pluginMock = mock(Plugin.class);
        when(jenkinsMock.getPlugin(LogbackUtil.LOGBACK_PLUGIN_NAME)).thenReturn(pluginMock);


        LogbackUtil logbackUtil = new LogbackUtil();
        logbackUtil.logInfo("something");

        Logback logback = logbackUtil.getLogback();
        assertThat(logback, is(notNullValue()));
        assertThat(logback.getClass().getName(), is(LogbackImpl.class.getName()));

        LogbackImpl logbackImpl = ((LogbackImpl) logback);
        assertThat(logbackImpl.getLogger(), is(notNullValue()));
    }

    @Test
    public void givenLogbackAppender_whenLogbackIsEnabledButNotConfigured_thenLoggerIsNotCreated() throws MalformedURLException {
        when(PropertyLoader.getLogbackConfigXmlUrl()).thenReturn("");
        when(PropertyLoader.getShouldSendToLogback()).thenReturn(true);
        when(Jenkins.getInstance()).thenReturn(jenkinsMock);
        Plugin pluginMock = mock(Plugin.class);
        when(jenkinsMock.getPlugin(LogbackUtil.LOGBACK_PLUGIN_NAME)).thenReturn(pluginMock);


        LogbackUtil logbackUtil = new LogbackUtil();
        logbackUtil.logInfo("something");

        Logback logback = logbackUtil.getLogback();
        assertThat(logback, is(nullValue()));
    }


}
