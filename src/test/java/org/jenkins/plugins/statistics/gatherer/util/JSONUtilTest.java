package org.jenkins.plugins.statistics.gatherer.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.jenkins.plugins.statistics.gatherer.model.build.SCMInfo;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Matchers;
import org.mockito.Mockito;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by mcharron on 2016-06-27.
 */
public class JSONUtilTest {

    private SCMInfo testObject = new SCMInfo();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void givenObject_whenToJson_thenReturnJson(){
        testObject.setUrl("http://test.com");
        testObject.setBranch("blop");
        testObject.setCommit("blopie");
        String expectedJson = "{\"url\":\"http://test.com\",\"branch\":\"blop\",\"commit\":\"blopie\"}";

        String jsonString = JSONUtil.convertToJson(testObject);

        assertEquals(expectedJson, jsonString);
    }

//    @Test(expected=JsonParseException.class)
//    public void givenInvalidObject_whenToJson_thenThrows() throws IOException{
//        testObject.setUrl("http://test.com");
//        testObject.setBranch("blop");
//        testObject.setCommit("blopie");
//        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
//        Mockito.doThrow(new JsonParseException(null, "error"))
//                .when(mapper).writeValueAsString(Matchers.anyObject());
//        JSONUtil.convertToJson(testObject);
//
//    }
}
