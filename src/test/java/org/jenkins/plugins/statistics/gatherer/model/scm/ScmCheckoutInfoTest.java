package org.jenkins.plugins.statistics.gatherer.model.scm;

import org.jenkins.plugins.statistics.gatherer.util.Constants;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

/**
 * Created by mcharron on 2016-07-19.
 */
public class ScmCheckoutInfoTest {

    private static final String BUILD_URL = "anUrl";
    private static final LocalDateTime START_TIME = Constants.TIME_EPOCH.plusSeconds(1);
    private static final LocalDateTime END_TIME = Constants.TIME_EPOCH.plusSeconds(1000);
    private ScmCheckoutInfo scmCheckoutInfo;

    @Before
    public void initBaseObject() {
        scmCheckoutInfo = new ScmCheckoutInfo(START_TIME, END_TIME, BUILD_URL);
    }

    @Test
    public void givenNothing_whenCreateScmCheckoutInfo_thenItIsInitialized() {
        ScmCheckoutInfo scmCheckout = new ScmCheckoutInfo();
        assertEquals("", scmCheckout.getBuildUrl());
        assertEquals(Constants.TIME_EPOCH, scmCheckout.getStartTime());
        assertEquals(Constants.TIME_EPOCH, scmCheckout.getEndTime());
    }

    @Test
    public void givenScmCheckoutInfo_whenGetBuildUrl_thenReturnBuildUrl() {
        //given

        //when
        String buildUrl = scmCheckoutInfo.getBuildUrl();

        //then
        assertEquals(BUILD_URL, buildUrl);
    }

    @Test
    public void givenScmCheckoutInfo_whenSetBuildUrl_thenBuildUrlIsSet() {
        //given
        String expectedBuildUrl = "IHazBuildUrl!";
        //when
        scmCheckoutInfo.setBuildUrl(expectedBuildUrl);

        //then
        String actualBuildUrl = scmCheckoutInfo.getBuildUrl();
        assertEquals(expectedBuildUrl, actualBuildUrl);
    }

    @Test
    public void givenScmCheckoutInfo_whenGetStartTime_thenReturnStartTime() {
        //given

        //when
        LocalDateTime startTime = scmCheckoutInfo.getStartTime();

        //then
        assertEquals(START_TIME, startTime);
    }

    @Test
    public void givenScmCheckoutInfo_whenSetStartTime_thenStartTimeIsSet() {
        //given
        LocalDateTime expectedStartTime = Constants.TIME_EPOCH.plusSeconds(123456);
        //when
        scmCheckoutInfo.setStartTime(expectedStartTime);

        //then
        LocalDateTime actualStartTime = scmCheckoutInfo.getStartTime();
        assertEquals(expectedStartTime, actualStartTime);
    }

    @Test
    public void givenScmCheckoutInfo_whenGetEndTime_thenReturnEndTime() {
        //given

        //when
        LocalDateTime endTime = scmCheckoutInfo.getEndTime();

        //then
        assertEquals(END_TIME, endTime);
    }

    @Test
    public void givenScmCheckoutInfo_whenSetEndTime_thenEndTimeIsSet() {
        //given
        LocalDateTime expectedEndTime = Constants.TIME_EPOCH.plusSeconds(123456);

        //when
        scmCheckoutInfo.setEndTime(expectedEndTime);

        //then
        LocalDateTime actualEndTime = scmCheckoutInfo.getEndTime();
        assertEquals(expectedEndTime, actualEndTime);
    }
}
