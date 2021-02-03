package org.jenkins.plugins.statistics.gatherer.model.step;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by mcharron on 2016-07-19.
 */
public class BuildStepStatsTest {

    private static final String BUILD_URL = "anUrl";
    private static final String BUILD_STEP_TYPE = "aType";
    private static final String BUILD_STEP_ID = "anId";
    private static final LocalDateTime START_TIME = Constants.TIME_EPOCH.plusSeconds(1);
    private static final LocalDateTime END_TIME = Constants.TIME_EPOCH.plusSeconds(1000);
    private BuildStepStats buildStepStats;

    @Before
    public void initBaseObject() {
        buildStepStats = new BuildStepStats(START_TIME, END_TIME, BUILD_URL,
                BUILD_STEP_TYPE,
                BUILD_STEP_ID);
    }

    @Test
    public void givenNothing_whenCreateBuildStepStats_thenItIsInitialized() {
        BuildStepStats buildStep = new BuildStepStats();
        assertEquals("", buildStep.getBuildUrl());
        assertEquals("", buildStep.getBuildStepType());
        assertEquals("", buildStep.getBuildStepId());
        assertEquals(Constants.TIME_EPOCH, buildStep.getStartTime());
        assertEquals(Constants.TIME_EPOCH, buildStep.getEndTime());
    }

    @Test
    public void givenBuildStepStats_whenGetBuildUrl_thenReturnBuildUrl() {
        //given

        //when
        String buildUrl = buildStepStats.getBuildUrl();

        //then
        assertEquals(BUILD_URL, buildUrl);
    }

    @Test
    public void givenBuildStepStats_whenSetBuildUrl_thenBuildUrlIsSet() {
        //given
        String expectedBuildUrl = "IHazBuildUrl!";
        //when
        buildStepStats.setBuildUrl(expectedBuildUrl);

        //then
        String actualBuildUrl = buildStepStats.getBuildUrl();
        assertEquals(expectedBuildUrl, actualBuildUrl);
    }

    @Test
    public void givenBuildStepStats_whenGetBuildStepType_thenReturnBuildStepType() {
        //given

        //when
        String buildStepType = buildStepStats.getBuildStepType();

        //then
        assertEquals(BUILD_STEP_TYPE, buildStepType);
    }

    @Test
    public void givenBuildStepStats_whenSetBuildStepType_thenBuildStepTypeIsSet() {
        //given
        String expectedBuildStepType = "IHazBuildStepType!";
        //when
        buildStepStats.setBuildStepType(expectedBuildStepType);

        //then
        String actualBuildStepType = buildStepStats.getBuildStepType();
        assertEquals(expectedBuildStepType, actualBuildStepType);
    }

    @Test
    public void givenBuildStepStats_whenGetBuildStepId_thenReturnBuildStepId() {
        //given

        //when
        String buildStepId = buildStepStats.getBuildStepId();

        //then
        assertEquals(BUILD_STEP_ID, buildStepId);
    }

    @Test
    public void givenBuildStepStats_whenSetBuildStepId_thenBuildStepIdIsSet() {
        //given
        String expectedBuildStepId = "IHazBuildStepId!";
        //when
        buildStepStats.setBuildStepId(expectedBuildStepId);

        //then
        String actualBuildStepId = buildStepStats.getBuildStepId();
        assertEquals(expectedBuildStepId, actualBuildStepId);
    }

    @Test
    public void givenBuildStepStats_whenGetStartTime_thenReturnStartTime() {
        //given

        //when
        LocalDateTime startTime = buildStepStats.getStartTime();

        //then
        assertEquals(START_TIME, startTime);
    }

    @Test
    public void givenBuildStepStats_whenSetStartTime_thenStartTimeIsSet() {
        //given
        LocalDateTime expectedStartTime = Constants.TIME_EPOCH.plusSeconds(123456);
        //when
        buildStepStats.setStartTime(expectedStartTime);

        //then
        LocalDateTime actualStartTime = buildStepStats.getStartTime();
        assertEquals(expectedStartTime, actualStartTime);
    }

    @Test
    public void givenBuildStepStats_whenGetEndTime_thenReturnEndTime() {
        //given

        //when
        LocalDateTime endTime = buildStepStats.getEndTime();

        //then
        assertEquals(END_TIME, endTime);
    }

    @Test
    public void givenBuildStepStats_whenSetEndTime_thenEndTimeIsSet() {
        //given
        LocalDateTime expectedEndTime = Constants.TIME_EPOCH.plusSeconds(123456);

        //when
        buildStepStats.setEndTime(expectedEndTime);

        //then
        LocalDateTime actualEndTime = buildStepStats.getEndTime();
        assertEquals(expectedEndTime, actualEndTime);
    }
}
