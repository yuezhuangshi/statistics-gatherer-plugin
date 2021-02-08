package org.jenkins.plugins.statistics.gatherer.model.build;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by mcharron on 2016-06-29.
 */
public class BuildStatsTest {

    private static final String ROOT_URL = "http://url.com";
    private static final String JOB_NAME = "JOB";
    private static final String FULL_JOB_NAME = "FULLJOB";
    private static final String STARTED_USER_ID = "USER";
    private static final String STARTED_USER_NAME = "USERNAME";
    private static final String RESULT = "SUCCESS";
    private static final Integer BUILD_NUMBER = 1;
    private static final Date START_TIME = new Date(0);
    private static final Date END_TIME = new Date(1000000);
    private static final Long DURATION = 123456L;
    private static final Long QUEUE_TIME = 12345678L;
    private static final BuildStats.SlaveInfo SLAVE_INFO = new BuildStats.SlaveInfo();
    private static final BuildStats.ScmInfo SCM_INFO = new BuildStats.ScmInfo();
    private static final Map<String, String> PARAMETERS = new HashMap<>();
    private static final String BUILD_URL = "http://url.com/build";
    private static final String BUILD_CAUSE = "This is a cause";
    private static final List<Map<String, Object>> BUILD_FAILURE_CAUSES = new ArrayList<>();
    private BuildStats buildStats;

    @Before
    public void initBaseObject() {
        buildStats = BuildStats.builder()
            .rootUrl(ROOT_URL)
            .jobName(JOB_NAME)
            .fullJobName(FULL_JOB_NAME)
            .buildNumber(BUILD_NUMBER)
            .slaveInfo(SLAVE_INFO)
            .startTime(START_TIME)
            .endTime(END_TIME)
            .startedUserId(STARTED_USER_ID)
            .startedUserName(STARTED_USER_NAME)
            .result(RESULT)
            .duration(DURATION)
            .parameters(PARAMETERS)
            .scmInfo(SCM_INFO)
            .queueTime(QUEUE_TIME)
            .buildUrl(BUILD_URL)
            .buildCause(BUILD_CAUSE)
            .buildFailureCauses(BUILD_FAILURE_CAUSES)
            .build();
    }

    @Test
    public void givenStatsBuild_whenGetRootUrl_thenReturnCiUrl() {
        //given

        //when
        String ciUrl = buildStats.getRootUrl();

        //then
        assertEquals(ROOT_URL, ciUrl);
    }

    @Test
    public void givenStatsBuild_whenSetRootUrl_thenCiUrlIsSet() {
        //given
        String expectedCiUrl = "IHazCiUrl!";
        //when
        buildStats.setRootUrl(expectedCiUrl);

        //then
        String actualCiUrl = buildStats.getRootUrl();
        assertEquals(expectedCiUrl, actualCiUrl);
    }

    @Test
    public void givenStatsBuild_whenGetJobName_thenReturnJobName() {
        //given

        //when
        String jobName = buildStats.getJobName();

        //then
        assertEquals(JOB_NAME, jobName);
    }

    @Test
    public void givenStatsBuild_whenSetJobName_thenJobNameIsSet() {
        //given
        String expectedJobName = "IHazJobName!";
        //when
        buildStats.setJobName(expectedJobName);

        //then
        String actualJobName = buildStats.getJobName();
        assertEquals(expectedJobName, actualJobName);
    }

    @Test
    public void givenStatsBuild_whenGetFullJobName_thenReturnFullJobName() {
        //given

        //when
        String fullJobName = buildStats.getFullJobName();

        //then
        assertEquals(FULL_JOB_NAME, fullJobName);
    }

    @Test
    public void givenStatsBuild_whenSetFullJobName_thenFullJobNameIsSet() {
        //given
        String expectedFullJobName = "IHazFullJobName!";
        //when
        buildStats.setFullJobName(expectedFullJobName);

        //then
        String actualFullJobName = buildStats.getFullJobName();
        assertEquals(expectedFullJobName, actualFullJobName);
    }

    @Test
    public void givenStatsBuild_whenGetStartedUserId_thenReturnStartedUserId() {
        //given

        //when
        String startedUserId = buildStats.getStartedUserId();

        //then
        assertEquals(STARTED_USER_ID, startedUserId);
    }

    @Test
    public void givenStatsBuild_whenSetStartedUserId_thenStartedUserIdIsSet() {
        //given
        String expectedStartedUserId = "IHazStartedUserId!";
        //when
        buildStats.setStartedUserId(expectedStartedUserId);

        //then
        String actualStartedUserId = buildStats.getStartedUserId();
        assertEquals(expectedStartedUserId, actualStartedUserId);
    }

    @Test
    public void givenStatsBuild_whenGetStartedUserName_thenReturnStartedUserName() {
        //given

        //when
        String startedUserName = buildStats.getStartedUserName();

        //then
        assertEquals(STARTED_USER_NAME, startedUserName);
    }

    @Test
    public void givenStatsBuild_whenSetStartedUserName_thenStartedUserNameIsSet() {
        //given
        String expectedStartedUserName = "IHazStartedUserName!";
        //when
        buildStats.setStartedUserName(expectedStartedUserName);

        //then
        String actualStartedUserName = buildStats.getStartedUserName();
        assertEquals(expectedStartedUserName, actualStartedUserName);
    }

    @Test
    public void givenStatsBuild_whenGetResult_thenReturnResult() {
        //given

        //when
        String result = buildStats.getResult();

        //then
        assertEquals(RESULT, result);
    }

    @Test
    public void givenStatsBuild_whenSetResult_thenResultIsSet() {
        //given
        String expectedResult = "IHazResult!";
        //when
        buildStats.setResult(expectedResult);

        //then
        String actualResult = buildStats.getResult();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void givenStatsBuild_whenGetBuildNumber_thenReturnNumber() {
        //given

        //when
        Integer number = buildStats.getBuildNumber();

        //then
        assertEquals(BUILD_NUMBER, number);
    }

    @Test
    public void givenStatsBuild_whenSetBuildNumber_thenNumberIsSet() {
        //given
        Integer expectedNumber = 123456789;
        //when
        buildStats.setBuildNumber(expectedNumber);

        //then
        Integer actualNumber = buildStats.getBuildNumber();
        assertEquals(expectedNumber, actualNumber);
    }

    @Test
    public void givenStatsBuild_whenGetDuration_thenReturnDuration() {
        //given

        //when
        Long Duration = buildStats.getDuration();

        //then
        assertEquals(DURATION, Duration);
    }

    @Test
    public void givenStatsBuild_whenSetDuration_thenDurationIsSet() {
        //given
        long expectedDuration = 123456789;
        //when
        buildStats.setDuration(expectedDuration);

        //then
        long actualDuration = buildStats.getDuration();
        assertEquals(expectedDuration, actualDuration);
    }

    @Test
    public void givenStatsBuild_whenGetQueueTime_thenReturnQueueTime() {
        //given

        //when
        Long queueTime = buildStats.getQueueTime();

        //then
        assertEquals(QUEUE_TIME, queueTime);
    }

    @Test
    public void givenStatsBuild_whenSetQueueTime_thenQueueTimeIsSet() {
        //given
        long expectedQueueTime = 123456789;
        //when
        buildStats.setQueueTime(expectedQueueTime);

        //then
        long actualQueueTime = buildStats.getQueueTime();
        assertEquals(expectedQueueTime, actualQueueTime);
    }

    @Test
    public void givenStatsBuild_whenGetSlaveInfo_thenReturnSlaveInfo() {
        //given

        //when
        BuildStats.SlaveInfo slaveInfo = buildStats.getSlaveInfo();

        //then
        assertEquals(SLAVE_INFO, slaveInfo);
    }

    @Test
    public void givenStatsBuild_whenSetSlaveInfo_thenSlaveInfoIsSet() {
        //given
        BuildStats.SlaveInfo expectedSlaveInfo = new BuildStats.SlaveInfo();
        //when
        buildStats.setSlaveInfo(expectedSlaveInfo);

        //then
        BuildStats.SlaveInfo actualSlaveInfo = buildStats.getSlaveInfo();
        assertEquals(expectedSlaveInfo, actualSlaveInfo);
    }

    @Test
    public void givenStatsBuild_whenGetSCMInfo_thenReturnSCMInfo() {
        //given

        //when
        BuildStats.ScmInfo scmInfo = buildStats.getScmInfo();

        //then
        assertEquals(SCM_INFO, scmInfo);
    }

    @Test
    public void givenStatsBuild_whenSetSCMInfo_thenSCMInfoIsSet() {
        //given
        BuildStats.ScmInfo expectedSCMInfo = new BuildStats.ScmInfo();
        //when
        buildStats.setScmInfo(expectedSCMInfo);

        //then
        BuildStats.ScmInfo actualSCMInfo = buildStats.getScmInfo();
        assertEquals(expectedSCMInfo, actualSCMInfo);
    }

    @Test
    public void givenStatsBuild_whenGetStartTime_thenReturnStartTime() {
        //given

        //when
        Date startTime = buildStats.getStartTime();

        //then
        assertEquals(START_TIME, startTime);
    }

    @Test
    public void givenStatsBuild_whenSetStartTime_thenStartTimeIsSet() {
        //given
        Date expectedStartTime = new Date();
        //when
        buildStats.setStartTime(expectedStartTime);

        //then
        Date actualStartTime = buildStats.getStartTime();
        assertEquals(expectedStartTime, actualStartTime);
    }

    @Test
    public void givenStatsBuild_whenGetEndTime_thenReturnEndTime() {
        //given

        //when
        Date endTime = buildStats.getEndTime();

        //then
        assertEquals(END_TIME, endTime);
    }

    @Test
    public void givenStatsBuild_whenSetEndTime_thenEndTimeIsSet() {
        //given
        Date expectedEndTime = new Date();
        //when
        buildStats.setEndTime(expectedEndTime);

        //then
        Date actualEndTime = buildStats.getEndTime();
        assertEquals(expectedEndTime, actualEndTime);
    }

    @Test
    public void givenStatsBuild_whenGetParameters_thenReturnParameters() {
        //given

        //when
        Map<String, String> Parameters = buildStats.getParameters();

        //then
        assertEquals(PARAMETERS, Parameters);
    }

    @Test
    public void givenStatsBuild_whenSetParameters_thenParametersIsSet() {
        //given
        Map<String, String> expectedParameters = new HashMap<>();
        expectedParameters.put("aKey", "something");
        //when
        buildStats.setParameters(expectedParameters);

        //then
        Map<String, String> actualParameters = buildStats.getParameters();
        assertEquals(expectedParameters, actualParameters);
    }

    @Test
    public void givenStatsBuild_whenGetBuildUrl_thenReturnBuildUrl() {
        //given

        //when
        String buildUrl = buildStats.getBuildUrl();

        //then
        assertEquals(BUILD_URL, buildUrl);
    }

    @Test
    public void givenStatsBuild_whenSetBuildUrl_thenBuildUrlIsSet() {
        //given
        String expectedBuildUrl = "IHazBuildUrl!";
        //when
        buildStats.setBuildUrl(expectedBuildUrl);

        //then
        String actualBuildUrl = buildStats.getBuildUrl();
        assertEquals(expectedBuildUrl, actualBuildUrl);
    }

    @Test
    public void givenStatsBuild_whenGetBuildCause_thenReturnBuildCause() {
        //given

        //when
        String buildCause = buildStats.getBuildCause();

        //then
        assertEquals(BUILD_CAUSE, buildCause);
    }

    @Test
    public void givenStatsBuild_whenSetBuildCause_thenBuildCauseIsSet() {
        //given
        String expectedBuildCause = "IHazBuildCause!";
        //when
        buildStats.setBuildCause(expectedBuildCause);

        //then
        String actualBuildCause = buildStats.getBuildCause();
        assertEquals(expectedBuildCause, actualBuildCause);
    }

    @Test
    public void givenStatsBuild_whenGetBuildFailureCauses_thenReturnBuildFailureCauses() {
        //given

        //when
        List<Map<String, Object>> buildFailureCauses = buildStats.getBuildFailureCauses();

        //then
        assertEquals(BUILD_FAILURE_CAUSES, buildFailureCauses);
    }

    @Test
    public void givenStatsBuild_whenSetBuildFailureCauses_thenBuildFailureCausesIsSet() {
        //given
        List<Map<String, Object>> expectedBuildFailureCauses = new ArrayList<>();
        expectedBuildFailureCauses.add(new HashMap<>());
        //when
        buildStats.setBuildFailureCauses(expectedBuildFailureCauses);

        //then
        List<Map<String, Object>> actualBuildFailureCauses = buildStats.getBuildFailureCauses();
        assertEquals(expectedBuildFailureCauses, actualBuildFailureCauses);
    }
}
