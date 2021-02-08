package org.jenkins.plugins.statistics.gatherer.model.job;

import org.jenkins.plugins.statistics.gatherer.util.Constants;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by mcharron on 2016-06-29.
 */
public class JobStatsTest {

    private static final String NAME = "Job";
    private static final String FULL_NAME = "Job";
    private static final String OPERATOR_ID = "user";
    private static final String OPERATOR_NAME = "username";
    private static final String ROOT_URL = "http://url.com";
    private static final String STATUS = "STATUS";
    private static final String CONFIG_FILE = "aConfigFile";
    private static final LocalDateTime CREATED_DATE = Constants.TIME_EPOCH;
    private static final LocalDateTime UPDATED_DATE = Constants.TIME_EPOCH.plusSeconds(1000);
    private static final String JOB_URL = "http://url.com/job";
    private JobStats jobStats;

    @Before
    public void initBaseObject() {
        jobStats = JobStats.builder()
            .name(NAME)
            .fullName(FULL_NAME)
            .operatorId(OPERATOR_ID)
            .operatorName(OPERATOR_NAME)
            .rootUrl(ROOT_URL)
            .jobUrl(JOB_URL)
            .status(STATUS)
            .configFile(CONFIG_FILE)
            .createdDate(CREATED_DATE)
            .updatedDate(UPDATED_DATE)
            .build();
    }

    @Test
    public void givenNothing_whenConstruct_thenValuesAreSet() {
        //when
        JobStats jobStats = new JobStats();
        //then
        LocalDateTime compareDate = LocalDateTime.now();
        assertNull(jobStats.getName());
        assertNull(jobStats.getCreatedDate());
        assertNull(jobStats.getOperatorId());
        assertNull(jobStats.getOperatorName());
        assertNull(jobStats.getRootUrl());
        assertNull(jobStats.getUpdatedDate());
        assertNull(jobStats.getStatus());
        assertNull(jobStats.getConfigFile());
        assertNull(jobStats.getJobUrl());
    }

    @Test
    public void givenStatsJob_whenGetCiUrl_thenReturnCiUrl() {
        //given

        //when
        String ciUrl = jobStats.getRootUrl();

        //then
        assertEquals(ROOT_URL, ciUrl);
    }

    @Test
    public void givenStatsJob_whenSetCiUrl_thenCiUrlIsSet() {
        //given
        String expectedCiUrl = "IHazCiUrl!";
        //when
        jobStats.setRootUrl(expectedCiUrl);

        //then
        String actualCiUrl = jobStats.getRootUrl();
        assertEquals(expectedCiUrl, actualCiUrl);
    }

    @Test
    public void givenStatsJob_whenGetName_thenReturnName() {
        //given

        //when
        String name = jobStats.getName();

        //then
        assertEquals(NAME, name);
    }

    @Test
    public void givenStatsJob_whenSetName_thenNameIsSet() {
        //given
        String expectedName = "IHazName!";
        //when
        jobStats.setName(expectedName);

        //then
        String actualName = jobStats.getName();
        assertEquals(expectedName, actualName);
    }

    @Test
    public void givenStatsJob_whenGetUsername_thenReturnUsername() {
        //given

        //when
        String userName = jobStats.getOperatorName();

        //then
        assertEquals(OPERATOR_NAME, userName);
    }

    @Test
    public void givenStatsJob_whenSetUserName_thenUserNameIsSet() {
        //given
        String expectedFullJobName = "IHazUserName!";
        //when
        jobStats.setOperatorName(expectedFullJobName);

        //then
        String actualUserName = jobStats.getOperatorName();
        assertEquals(expectedFullJobName, actualUserName);
    }

    @Test
    public void givenStatsJob_whenGetUserId_thenReturnUserId() {
        //given

        //when
        String userId = jobStats.getOperatorId();

        //then
        assertEquals(OPERATOR_ID, userId);
    }

    @Test
    public void givenStatsJob_whenSetUserId_thenUserIdIsSet() {
        //given
        String expectedUserId = "IHazUserId!";
        //when
        jobStats.setOperatorId(expectedUserId);

        //then
        String actualUserId = jobStats.getOperatorId();
        assertEquals(expectedUserId, actualUserId);
    }

    @Test
    public void givenStatsJob_whenGetStatus_thenReturnStatus() {
        //given

        //when
        String status = jobStats.getStatus();

        //then
        assertEquals(STATUS, status);
    }

    @Test
    public void givenStatsJob_whenSetStatus_thenStatusIsSet() {
        //given
        String expectedStatus = "IHazStatus!";
        //when
        jobStats.setStatus(expectedStatus);

        //then
        String actualStatus = jobStats.getStatus();
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void givenStatsJob_whenGetConfigFile_thenReturnConfigFile() {
        //given

        //when
        String configFile = jobStats.getConfigFile();

        //then
        assertEquals(CONFIG_FILE, configFile);
    }

    @Test
    public void givenStatsJob_whenSetConfigFile_thenConfigFileIsSet() {
        //given
        String expectedConfigFile = "IHazConfigFile!";
        //when
        jobStats.setConfigFile(expectedConfigFile);

        //then
        String actualConfigFile = jobStats.getConfigFile();
        assertEquals(expectedConfigFile, actualConfigFile);
    }

    @Test
    public void givenStatsJob_whenGetCreatedDate_thenReturnCreateDate() {
        //given

        //when
        LocalDateTime createdDate = jobStats.getCreatedDate();

        //then
        assertEquals(CREATED_DATE, createdDate);
    }

    @Test
    public void givenStatsJob_whenSetCreatedDate_thenCreatedDateIsSet() {
        //given
        LocalDateTime expectedCreatedDate = LocalDateTime.now();
        //when
        jobStats.setCreatedDate(expectedCreatedDate);

        //then
        LocalDateTime actualCreatedDate = jobStats.getCreatedDate();
        assertEquals(expectedCreatedDate, actualCreatedDate);
    }

    @Test
    public void givenStatsJob_whenGetUpdatedDate_thenReturnUpdatedDate() {
        //given

        //when
        LocalDateTime updatedDate = jobStats.getUpdatedDate();

        //then
        assertEquals(UPDATED_DATE, updatedDate);
    }

    @Test
    public void givenStatsJob_whenSetUpdatedDate_thenUpdatedDateIsSet() {
        //given
        LocalDateTime expectedUpdatedDate = LocalDateTime.now();
        //when
        jobStats.setUpdatedDate(expectedUpdatedDate);

        //then
        LocalDateTime actualUpdatedDate = jobStats.getUpdatedDate();
        assertEquals(expectedUpdatedDate, actualUpdatedDate);
    }

    @Test
    public void givenNullEntryTime_whenSetEntryTime_thenEntryTimeIsNull() {
        //given

        //when
        jobStats.setCreatedDate(null);

        //then
        LocalDateTime createdDate = jobStats.getCreatedDate();
        assertNull(createdDate);
    }

    @Test
    public void givenNullExitTime_whenSetExitTime_thenExitTimeIsNull() {
        //given

        //when
        jobStats.setUpdatedDate(null);

        //then
        LocalDateTime updatedDate = jobStats.getUpdatedDate();
        assertNull(updatedDate);
    }

    @Test
    public void givenStatsJob_whenGetJobUrl_thenReturnJobUrl() {
        //given

        //when
        String jobUrl = jobStats.getJobUrl();

        //then
        assertEquals(JOB_URL, jobUrl);
    }

    @Test
    public void givenStatsJob_whenSetJobUrl_thenJobUrlIsSet() {
        //given
        String expectedJobUrl = "IHazJobUrl!";
        //when
        jobStats.setJobUrl(expectedJobUrl);

        //then
        String actualJobUrl = jobStats.getJobUrl();
        assertEquals(expectedJobUrl, actualJobUrl);
    }
}
