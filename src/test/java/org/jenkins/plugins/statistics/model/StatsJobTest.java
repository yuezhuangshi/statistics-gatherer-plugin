package org.jenkins.plugins.statistics.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by mcharron on 2016-06-29.
 */
public class StatsJobTest {


    private static final String NAME = "Job";
    private static final String USER_ID = "user";
    private static final String USERNAME = "username";
    private static final String CI_URL = "http://url.com";
    private static final String STATUS = "STATUS";
    private static final String CONFIG_FILE = "aConfigFile";
    private static final Date CREATED_DATE = new Date(0);
    private static final Date UPDATED_DATE = new Date(1000000);
    private StatsJob statsJob;

    @Before
    public void initBaseObject() {
        statsJob = new StatsJob(NAME,
                CREATED_DATE,
                USER_ID,
                USERNAME,
                CI_URL,
                UPDATED_DATE,
                STATUS,
                CONFIG_FILE);
    }

    @Test
    public void givenStatsJob_whenGetCiUrl_thenReturnCiUrl() {
        //given

        //when
        String ciUrl = statsJob.getCiUrl();

        //then
        assertEquals(CI_URL, ciUrl);
    }

    @Test
    public void givenStatsJob_whenSetCiUrl_thenCiUrlIsSet() {
        //given
        String expectedCiUrl = "IHazCiUrl!";
        //when
        statsJob.setCiUrl(expectedCiUrl);

        //then
        String actualCiUrl = statsJob.getCiUrl();
        assertEquals(expectedCiUrl, actualCiUrl);
    }

    @Test
    public void givenStatsJob_whenGetName_thenReturnName() {
        //given

        //when
        String name = statsJob.getName();

        //then
        assertEquals(NAME, name);
    }

    @Test
    public void givenStatsJob_whenSetName_thenNameIsSet() {
        //given
        String expectedName = "IHazName!";
        //when
        statsJob.setName(expectedName);

        //then
        String actualName = statsJob.getName();
        assertEquals(expectedName, actualName);
    }

    @Test
    public void givenStatsJob_whenGetUsername_thenReturnUsername() {
        //given

        //when
        String userName = statsJob.getUserName();

        //then
        assertEquals(USERNAME, userName);
    }

    @Test
    public void givenStatsJob_whenSetUserName_thenUserNameIsSet() {
        //given
        String expectedFullJobName = "IHazUserName!";
        //when
        statsJob.setUserName(expectedFullJobName);

        //then
        String actualUserName = statsJob.getUserName();
        assertEquals(expectedFullJobName, actualUserName);
    }

    @Test
    public void givenStatsJob_whenGetUserId_thenReturnUserId() {
        //given

        //when
        String userId = statsJob.getUserId();

        //then
        assertEquals(USER_ID, userId);
    }

    @Test
    public void givenStatsJob_whenSetUserId_thenUserIdIsSet() {
        //given
        String expectedUserId = "IHazUserId!";
        //when
        statsJob.setUserId(expectedUserId);

        //then
        String actualUserId = statsJob.getUserId();
        assertEquals(expectedUserId, actualUserId);
    }

    @Test
    public void givenStatsJob_whenGetStatus_thenReturnStatus() {
        //given

        //when
        String status = statsJob.getStatus();

        //then
        assertEquals(STATUS, status);
    }

    @Test
    public void givenStatsJob_whenSetStatus_thenStatusIsSet() {
        //given
        String expectedStatus = "IHazStatus!";
        //when
        statsJob.setStatus(expectedStatus);

        //then
        String actualStatus = statsJob.getStatus();
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void givenStatsJob_whenGetConfigFile_thenReturnConfigFile() {
        //given

        //when
        String configFile = statsJob.getConfigFile();

        //then
        assertEquals(CONFIG_FILE, configFile);
    }

    @Test
    public void givenStatsJob_whenSetConfigFile_thenConfigFileIsSet() {
        //given
        String expectedConfigFile = "IHazConfigFile!";
        //when
        statsJob.setConfigFile(expectedConfigFile);

        //then
        String actualConfigFile = statsJob.getConfigFile();
        assertEquals(expectedConfigFile, actualConfigFile);
    }

    @Test
    public void givenStatsJob_whenGetCreatedDate_thenReturnCreateDate() {
        //given

        //when
        Date createdDate = statsJob.getCreatedDate();

        //then
        assertEquals(CREATED_DATE, createdDate);
    }

    @Test
    public void givenStatsJob_whenSetCreatedDate_thenCreatedDateIsSet() {
        //given
        Date expectedCreatedDate = new Date();
        //when
        statsJob.setCreatedDate(expectedCreatedDate);

        //then
        Date actualCreatedDate = statsJob.getCreatedDate();
        assertEquals(expectedCreatedDate, actualCreatedDate);
    }

    @Test
    public void givenStatsJob_whenGetUpdatedDate_thenReturnUpdatedDate() {
        //given

        //when
        Date updatedDate = statsJob.getUpdatedDate();

        //then
        assertEquals(UPDATED_DATE, updatedDate);
    }

    @Test
    public void givenStatsJob_whenSetUpdatedDate_thenUpdatedDateIsSet() {
        //given
        Date expectedUpdatedDate = new Date();
        //when
        statsJob.setUpdatedDate(expectedUpdatedDate);

        //then
        Date actualUpdatedDate = statsJob.getUpdatedDate();
        assertEquals(expectedUpdatedDate, actualUpdatedDate);
    }
}
