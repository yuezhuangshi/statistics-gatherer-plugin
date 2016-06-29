package org.jenkins.plugins.statistics.model;

import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by mcharron on 2016-06-27.
 */
public class StatsQueueTest {

    private static final String CI_URL = "http://url.com";
    private static final String JOB_NAME = "aName";
    private static final Date ENTRY_TIME = new Date(0);
    private static final Date EXIT_TIME = new Date(1000000);
    private static final String STARTED_BY = "aUser";
    private static final int JENKINS_QUEUE_ID = 1;
    private static final String STATUS = "COMPLETED";
    private static final int DURATION = 2000;
    private static final String DURATION_STR = "2000";
    private static final ArrayList<QueueCause> QUEUE_CAUSES = new ArrayList<QueueCause>();
    private StatsQueue statsQueue;

    @Before
    public void initBaseObject() {
        statsQueue = new StatsQueue(CI_URL,
                JOB_NAME,
                ENTRY_TIME,
                EXIT_TIME,
                STARTED_BY,
                JENKINS_QUEUE_ID,
                STATUS,
                DURATION,
                DURATION_STR,
                QUEUE_CAUSES);
    }

    @Test
    public void givenStatsQueue_whenGetCiUrl_thenReturnCiUrl() {
        //given

        //when
        String url = statsQueue.getCiUrl();

        //then
        assertEquals(CI_URL, url);
    }

    @Test
    public void givenNothing_whenConstructEmpty_thenAllValuesAreSet() {
        //when
        StatsQueue statsQueue = new StatsQueue();

        //then
        assertEquals("", statsQueue.getCiUrl());
        assertEquals("", statsQueue.getJobName());
        Date compareDate =new Date();
        assertTrue(statsQueue.getEntryTime().before(compareDate)|| statsQueue.getEntryTime().equals(compareDate));
        assertTrue(statsQueue.getExitTime().before(compareDate) || statsQueue.getExitTime().equals(compareDate));
        assertEquals("", statsQueue.getStartedBy());
        assertEquals(0, statsQueue.getJenkinsQueueId());
        assertEquals("", statsQueue.getStatus());
        assertEquals(0, statsQueue.getDuration());
        assertEquals("", statsQueue.getDurationStr());
        assertEquals(new ArrayList<QueueCause>(), statsQueue.getQueueCauses());
    }

    @Test
    public void givenStatsQueue_whenGetJobName_thenReturnJobName() {
        //given

        //when
        String jobName = statsQueue.getJobName();

        //then
        assertEquals(JOB_NAME, jobName);
    }

    @Test
    public void givenStatsQueue_whenGetEntryTime_thenReturnEntryTime() {
        //given

        //when
        Date entryTime = statsQueue.getEntryTime();

        //then
        assertEquals(ENTRY_TIME, entryTime);
    }

    @Test
    public void givenStatsQueue_whenGetExitTime_thenReturnExitTime() {
        //given

        //when
        Date exitTime = statsQueue.getExitTime();

        //then
        assertEquals(EXIT_TIME, exitTime);
    }

    @Test
    public void givenStatsQueue_whenGetStartedBy_thenReturnStartedBy() {
        //given

        //when
        String startedBy = statsQueue.getStartedBy();

        //then
        assertEquals(STARTED_BY, startedBy);
    }

    @Test
    public void givenStatsQueue_whenGetJenkinsQueueId_thenReturnJenkinsQueueId() {
        //given

        //when
        int jenkinsQueueId = statsQueue.getJenkinsQueueId();

        //then
        assertEquals(JENKINS_QUEUE_ID, jenkinsQueueId);
    }

    @Test
    public void givenStatsQueue_whenGetStatus_thenReturnStatus() {
        //given

        //when
        String status = statsQueue.getStatus();

        //then
        assertEquals(STATUS, status);
    }

    @Test
    public void givenStatsQueue_whenGetDuration_thenReturnDuration() {
        //given

        //when
        long duration = statsQueue.getDuration();

        //then
        assertEquals(DURATION, duration);
    }

    @Test
    public void givenStatsQueue_whenGetDurationString_thenReturnDurationString() {
        //given

        //when
        String durationStr = statsQueue.getDurationStr();

        //then
        assertEquals(DURATION_STR, durationStr);
    }

    @Test
    public void givenStatsQueue_whenGetQueueCauses_thenReturnQueueCauses() {
        //given

        //when
        List<QueueCause> queueCauses = statsQueue.getQueueCauses();

        //then
        assertEquals(QUEUE_CAUSES, queueCauses);
    }

    @Test
    public void givenStatsQueue_whenSetQueueCauses_thenQueueCausesIsSet() {
        //given
        QueueCause queueCause = new QueueCause(ENTRY_TIME, EXIT_TIME, "aReason", "type");
        List<QueueCause> queueCausesToBeSet = new ArrayList<QueueCause>();
        queueCausesToBeSet.add(queueCause);
        //when
        statsQueue.setQueueCauses(queueCausesToBeSet);

        //then
        List<QueueCause> queueCauses = statsQueue.getQueueCauses();
        assertEquals(queueCausesToBeSet, queueCauses);
    }

    @Test
    public void givenStatsQueue_whenSetCiUrl_thenCiUrlIsSet() {
        //given
        String ciUrl = "aUrl";
        //when
        statsQueue.setCiUrl(ciUrl);

        //then
        String url = statsQueue.getCiUrl();
        assertEquals(ciUrl, url);
    }

    @Test
    public void givenStatsQueue_whenSetEntryTime_thenEntryTimeIsSet() {
        //given
        Date expectedEntryTime = new Date(123456789);
        //when
        statsQueue.setEntryTime(expectedEntryTime);

        //then
        Date actualEntryTime = statsQueue.getEntryTime();
        assertEquals(expectedEntryTime, actualEntryTime);
    }
    @Test
    public void givenStatsQueue_whenSetExitTime_thenExitTimeIsSet() {
        //given
        Date expectedExitTime = new Date(123456789);
        //when
        statsQueue.setExitTime(expectedExitTime);

        //then
        Date actualExitTime = statsQueue.getExitTime();
        assertEquals(expectedExitTime, actualExitTime);
    }
    @Test
    public void givenStatsQueue_whenSetStartedBy_thenStartedByIsSet() {
        //given
        String expectedStartedBy = "IHazStartedIt!";
        //when
        statsQueue.setStartedBy(expectedStartedBy);

        //then
        String actualStartedBy = statsQueue.getStartedBy();
        assertEquals(expectedStartedBy, actualStartedBy);
    }
    @Test
    public void givenStatsQueue_whenSetJenkinsQueueId_thenJenkinsQueueIdIsSet() {
        //given
        int expectedQueueId = 123456;
        //when
        statsQueue.setJenkinsQueueId(expectedQueueId);

        //then
        int actualQueueId = statsQueue.getJenkinsQueueId();
        assertEquals(expectedQueueId, actualQueueId);
    }
    @Test
    public void givenStatsQueue_whenSetStatus_thenStatusIsSet() {
        //given
        String expectedStatus = "WOOHOO";
        //when
        statsQueue.setStatus(expectedStatus);

        //then
        String actualStatus = statsQueue.getStatus();
        assertEquals(expectedStatus, actualStatus);
    }
    @Test
    public void givenStatsQueue_whenSetDuration_thenDurationIsSet() {
        //given
        long expectedDuration = 123456;
        //when
        statsQueue.setDuration(expectedDuration);

        //then
        long actualDuration = statsQueue.getDuration();
        assertEquals(expectedDuration, actualDuration);
    }
    @Test
    public void givenStatsQueue_whenSetDurationString_thenDurationStringIsSet() {
        //given
        String expectedDurationString = "ThisIsAString";
        //when
        statsQueue.setDurationStr(expectedDurationString);

        //then
        String actualUrl = statsQueue.getDurationStr();
        assertEquals(expectedDurationString, actualUrl);
    }
    @Test
    public void givenStatsQueue_whenSetJobName_thenJobNameIsSet() {
        //given
        String expectedJobName = "ThisIsAName";
        //when
        statsQueue.setJobName(expectedJobName);

        //then
        String actualJobName = statsQueue.getJobName();
        assertEquals(expectedJobName, actualJobName);
    }

    @Test
    public void givenNullEntryTime_whenSetEntryTime_thenEntryTimeIsNull() {
        //given

        //when
        statsQueue.setEntryTime(null);

        //then
        Date actualEntryTime = statsQueue.getEntryTime();
        assertNull(actualEntryTime);
    }

    @Test
    public void givenNullExitTime_whenSetExitTime_thenExitTimeIsNull() {
        //given

        //when
        statsQueue.setExitTime(null);

        //then
        Date actualExitTime = statsQueue.getExitTime();
        assertNull(actualExitTime);
    }

    @Test
    public void givenStatsQueue_whenAddQueueCause_thenQueueCauseIsAdded() {
        //given
        QueueCause queueCause = new QueueCause(ENTRY_TIME, EXIT_TIME, "aReason", "type");
        List<QueueCause> expectedQueueCauses = new ArrayList<QueueCause>();
        expectedQueueCauses.add(queueCause);
        //when
        statsQueue.addQueueCause(queueCause);

        //then
        List<QueueCause> queueCauses = statsQueue.getQueueCauses();
        assertEquals(expectedQueueCauses, queueCauses);
    }
}
