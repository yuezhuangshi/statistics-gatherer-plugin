package org.jenkins.plugins.statistics.model;

import hudson.util.CopyOnWriteMap;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by mcharron on 2016-06-29.
 */
public class StatsBuildTest {

    private static final String CI_URL = "http://url.com";
    private static final String JOB_NAME = "JOB";
    private static final String FULL_JOB_NAME = "FULLJOB";
    private static final String STARTED_USER_ID = "USER";
    private static final String STARTED_USER_NAME = "USERNAME";
    private static final String RESULT = "SUCCESS";
    private static final int NUMBER = 1;
    private static final Date START_TIME = new Date(0);
    private static final Date END_TIME = new Date(1000000);
    private static final long DURATION = 123456;
    private static final long QUEUE_TIME = 12345678;
    private static final SlaveInfo SLAVE_INFO = new SlaveInfo();
    private static final SCMInfo SCM_INFO = new SCMInfo();
    private static final Map<String, String> PARAMETERS = new HashMap<String, String>();
    private StatsBuild statsBuild;

    @Before
    public void initBaseObject() {
        statsBuild = new StatsBuild(CI_URL,
                JOB_NAME,
                FULL_JOB_NAME,
                NUMBER,
                SLAVE_INFO,
                START_TIME,
                END_TIME,
                STARTED_USER_ID,
                STARTED_USER_NAME,
                RESULT,
                DURATION,
                PARAMETERS,
                SCM_INFO,
                QUEUE_TIME);
    }

    @Test
    public void givenStatsBuild_whenGetCiUrl_thenReturnCiUrl() {
        //given

        //when
        String ciUrl = statsBuild.getCiUrl();

        //then
        assertEquals(CI_URL, ciUrl);
    }

    @Test
    public void givenStatsBuild_whenSetCiUrl_thenCiUrlIsSet() {
        //given
        String expectedCiUrl = "IHazCiUrl!";
        //when
        statsBuild.setCiUrl(expectedCiUrl);

        //then
        String actualCiUrl = statsBuild.getCiUrl();
        assertEquals(expectedCiUrl, actualCiUrl);
    }

    @Test
    public void givenStatsBuild_whenGetJobName_thenReturnJobName() {
        //given

        //when
        String jobName = statsBuild.getJobName();

        //then
        assertEquals(JOB_NAME, jobName);
    }

    @Test
    public void givenStatsBuild_whenSetJobName_thenJobNameIsSet() {
        //given
        String expectedJobName = "IHazJobName!";
        //when
        statsBuild.setJobName(expectedJobName);

        //then
        String actualJobName = statsBuild.getJobName();
        assertEquals(expectedJobName, actualJobName);
    }

    @Test
    public void givenStatsBuild_whenGetFullJobName_thenReturnFullJobName() {
        //given

        //when
        String fullJobName = statsBuild.getFullJobName();

        //then
        assertEquals(FULL_JOB_NAME, fullJobName);
    }

    @Test
    public void givenStatsBuild_whenSetFullJobName_thenFullJobNameIsSet() {
        //given
        String expectedFullJobName = "IHazFullJobName!";
        //when
        statsBuild.setFullJobName(expectedFullJobName);

        //then
        String actualFullJobName = statsBuild.getFullJobName();
        assertEquals(expectedFullJobName, actualFullJobName);
    }

    @Test
    public void givenStatsBuild_whenGetStartedUserId_thenReturnStartedUserId() {
        //given

        //when
        String startedUserId = statsBuild.getStartedUserId();

        //then
        assertEquals(STARTED_USER_ID, startedUserId);
    }

    @Test
    public void givenStatsBuild_whenSetStartedUserId_thenStartedUserIdIsSet() {
        //given
        String expectedStartedUserId = "IHazStartedUserId!";
        //when
        statsBuild.setStartedUserId(expectedStartedUserId);

        //then
        String actualStartedUserId = statsBuild.getStartedUserId();
        assertEquals(expectedStartedUserId, actualStartedUserId);
    }

    @Test
    public void givenStatsBuild_whenGetStartedUserName_thenReturnStartedUserName() {
        //given

        //when
        String startedUserName = statsBuild.getStartedUserName();

        //then
        assertEquals(STARTED_USER_NAME, startedUserName);
    }

    @Test
    public void givenStatsBuild_whenSetStartedUserName_thenStartedUserNameIsSet() {
        //given
        String expectedStartedUserName = "IHazStartedUserName!";
        //when
        statsBuild.setStartedUserName(expectedStartedUserName);

        //then
        String actualStartedUserName = statsBuild.getStartedUserName();
        assertEquals(expectedStartedUserName, actualStartedUserName);
    }

    @Test
    public void givenStatsBuild_whenGetResult_thenReturnResult() {
        //given

        //when
        String result = statsBuild.getResult();

        //then
        assertEquals(RESULT, result);
    }

    @Test
    public void givenStatsBuild_whenSetResult_thenResultIsSet() {
        //given
        String expectedResult = "IHazResult!";
        //when
        statsBuild.setResult(expectedResult);

        //then
        String actualResult = statsBuild.getResult();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void givenStatsBuild_whenGetNumber_thenReturnNumber() {
        //given

        //when
        int number = statsBuild.getNumber();

        //then
        assertEquals(NUMBER, number);
    }

    @Test
    public void givenStatsBuild_whenSetNumber_thenNumberIsSet() {
        //given
        int expectedNumber = 123456789;
        //when
        statsBuild.setNumber(expectedNumber);

        //then
        int actualNumber = statsBuild.getNumber();
        assertEquals(expectedNumber, actualNumber);
    }

    @Test
    public void givenStatsBuild_whenGetDuration_thenReturnDuration() {
        //given

        //when
        long Duration = statsBuild.getDuration();

        //then
        assertEquals(DURATION, Duration);
    }

    @Test
    public void givenStatsBuild_whenSetDuration_thenDurationIsSet() {
        //given
        long expectedDuration = 123456789;
        //when
        statsBuild.setDuration(expectedDuration);

        //then
        long actualDuration = statsBuild.getDuration();
        assertEquals(expectedDuration, actualDuration);
    }

    @Test
    public void givenStatsBuild_whenGetQueueTime_thenReturnQueueTime() {
        //given

        //when
        long queueTime = statsBuild.getQueueTime();

        //then
        assertEquals(QUEUE_TIME, queueTime);
    }

    @Test
    public void givenStatsBuild_whenSetQueueTime_thenQueueTimeIsSet() {
        //given
        long expectedQueueTime = 123456789;
        //when
        statsBuild.setQueueTime(expectedQueueTime);

        //then
        long actualQueueTime = statsBuild.getQueueTime();
        assertEquals(expectedQueueTime, actualQueueTime);
    }

    @Test
    public void givenStatsBuild_whenGetSlaveInfo_thenReturnSlaveInfo() {
        //given

        //when
        SlaveInfo slaveInfo = statsBuild.getSlaveInfo();

        //then
        assertEquals(SLAVE_INFO, slaveInfo);
    }

    @Test
    public void givenStatsBuild_whenSetSlaveInfo_thenSlaveInfoIsSet() {
        //given
        SlaveInfo expectedSlaveInfo = new SlaveInfo();
        //when
        statsBuild.setSlaveInfo(expectedSlaveInfo);

        //then
        SlaveInfo actualSlaveInfo = statsBuild.getSlaveInfo();
        assertEquals(expectedSlaveInfo, actualSlaveInfo);
    }

    @Test
    public void givenStatsBuild_whenGetSCMInfo_thenReturnSCMInfo() {
        //given

        //when
        SCMInfo scmInfo = statsBuild.getScmInfo();

        //then
        assertEquals(SCM_INFO, scmInfo);
    }

    @Test
    public void givenStatsBuild_whenSetSCMInfo_thenSCMInfoIsSet() {
        //given
        SCMInfo expectedSCMInfo = new SCMInfo();
        //when
        statsBuild.setScmInfo(expectedSCMInfo);

        //then
        SCMInfo actualSCMInfo = statsBuild.getScmInfo();
        assertEquals(expectedSCMInfo, actualSCMInfo);
    }

    @Test
    public void givenStatsBuild_whenGetStartTime_thenReturnStartTime() {
        //given

        //when
        Date startTime = statsBuild.getStartTime();

        //then
        assertEquals(START_TIME, startTime);
    }

    @Test
    public void givenStatsBuild_whenSetStartTime_thenStartTimeIsSet() {
        //given
        Date expectedStartTime = new Date();
        //when
        statsBuild.setStartTime(expectedStartTime);

        //then
        Date actualStartTime = statsBuild.getStartTime();
        assertEquals(expectedStartTime, actualStartTime);
    }

    @Test
    public void givenStatsBuild_whenGetEndTime_thenReturnEndTime() {
        //given

        //when
        Date endTime = statsBuild.getEndTime();

        //then
        assertEquals(END_TIME, endTime);
    }

    @Test
    public void givenStatsBuild_whenSetEndTime_thenEndTimeIsSet() {
        //given
        Date expectedEndTime = new Date();
        //when
        statsBuild.setEndTime(expectedEndTime);

        //then
        Date actualEndTime = statsBuild.getEndTime();
        assertEquals(expectedEndTime, actualEndTime);
    }

    @Test
    public void givenStatsBuild_whenGetParameters_thenReturnParameters() {
        //given

        //when
        Map<String, String>  Parameters = statsBuild.getParameters();

        //then
        assertEquals(PARAMETERS, Parameters);
    }

    @Test
    public void givenStatsBuild_whenSetParameters_thenParametersIsSet() {
        //given
        Map<String, String>  expectedParameters = new HashMap<String, String>();
        expectedParameters.put("aKey", "something");
        //when
        statsBuild.setParameters(expectedParameters);

        //then
        Map<String, String> actualParameters = statsBuild.getParameters();
        assertEquals(expectedParameters, actualParameters);
    }
}
