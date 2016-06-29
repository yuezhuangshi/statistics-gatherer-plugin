package org.jenkins.plugins.statistics.util;

import org.jenkins.plugins.statistics.model.SCMInfo;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by mcharron on 2016-06-27.
 */
public class JSONUtilTest {

    private SCMInfo testObject = new SCMInfo();

    @Test
    public void givenObject_whenToJson_thenReturnJson(){
        testObject.setUrl("http://test.com");
        testObject.setBranch("blop");
        testObject.setCommit("blopie");
        String expectedJson = "{\"url\":\"http://test.com\",\"branch\":\"blop\",\"commit\":\"blopie\"}";

        String jsonString = JSONUtil.convertToJsonStr(testObject);

        assertEquals(expectedJson, jsonString);
    }
}
