package org.jenkins.plugins.statistics.gatherer.listeners;

import hudson.model.Build;
import hudson.model.FreeStyleBuild;
import hudson.model.FreeStyleProject;
import hudson.model.queue.QueueTaskFuture;
import hudson.tasks.Shell;
import org.jenkins.plugins.statistics.gatherer.model.build.BuildStats;
import org.jenkins.plugins.statistics.gatherer.util.PropertyLoader;
import org.jenkins.plugins.statistics.gatherer.util.RestClientUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jvnet.hudson.test.JenkinsRule;
import org.mockito.ArgumentCaptor;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PropertyLoader.class, RestClientUtil.class})
@PowerMockIgnore({"javax.crypto.*"})
public class BuildStatsListenerTest {

    @Rule
    public JenkinsRule j = new JenkinsRule();

    private BuildStatsListener listener;

    @Before
    public void setup() {
        listener = new BuildStatsListener();
        mockStatic(PropertyLoader.class);
    }

    @Test
    public void givenRunWithBuildInfoTrue_whenStarted_thenPostOnce() throws Exception {
        mockStatic(RestClientUtil.class);
        when(PropertyLoader.getBuildInfo()).thenReturn(true);
        when(PropertyLoader.getBuildEndPoint()).thenReturn("http://localhost");

        Build<?, ?> build = triggerNewBuild().get();

        verifyStatic(RestClientUtil.class, times(1));
        ArgumentCaptor<BuildStats> captor = ArgumentCaptor.forClass(BuildStats.class);
        RestClientUtil.postToService(anyString(), captor.capture());
        BuildStats buildStats = captor.getAllValues().get(0);

        assertThat(buildStats.getResult(), is(equalTo("SUCCESS")));
        assertThat(buildStats.getStartTime(), is(notNullValue()));
        assertThat(buildStats.getBuildCause(), is(notNullValue()));
        assertBuildInfoEqualsTo(buildStats, build);
    }

    @Test
    public void givenRunWithBuildInfoTrue_whenCompleted_thenPost() throws Exception {
        mockStatic(RestClientUtil.class);
        when(PropertyLoader.getBuildInfo()).thenReturn(true);
        when(PropertyLoader.getBuildEndPoint()).thenReturn("http://localhost");

        Build<?, ?> build = triggerNewBuild().get();

        verifyStatic(RestClientUtil.class, times(1));
        ArgumentCaptor<BuildStats> captor = ArgumentCaptor.forClass(BuildStats.class);
        RestClientUtil.postToService(anyString(), captor.capture());
        BuildStats buildStats = captor.getAllValues().get(0);

        assertThat(buildStats.getResult(), is(equalTo("SUCCESS")));
        assertThat(buildStats.getStartTime(), is(notNullValue()));
        assertThat(buildStats.getBuildCause(), is(notNullValue()));
        assertBuildInfoEqualsTo(buildStats, build);
    }

    private void assertBuildInfoEqualsTo(BuildStats buildStats, Build<?, ?> build) {
        assertThat(buildStats.getBuildUrl(), is(build.getUrl()));
        assertThat(buildStats.getFullJobName(), is(build.getParent().getFullName()));
        assertThat(buildStats.getJobName(), is(build.getParent().getName()));
        assertThat(buildStats.getBuildNumber(), is(build.getNumber()));
    }

    private QueueTaskFuture<FreeStyleBuild> triggerNewBuild() throws IOException {
        FreeStyleProject project = j.createFreeStyleProject();
        project.getBuildersList().add(new Shell("echo hello"));
        return project.scheduleBuild2(0);
    }
}