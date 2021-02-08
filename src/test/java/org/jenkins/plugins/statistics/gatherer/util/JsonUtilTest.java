package org.jenkins.plugins.statistics.gatherer.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jenkins.plugins.statistics.gatherer.model.build.BuildStats;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by mcharron on 2016-06-27.
 */
public class JsonUtilTest {

    private BuildStats.ScmInfo testObject = new BuildStats.ScmInfo();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void givenObject_whenToJson_thenReturnJson() {
        testObject.setUrl("http://test.com");
        testObject.setBranch("blop");
        testObject.setCommit("blopie");
        String expectedJson = "{\"branch\":\"blop\",\"commit\":\"blopie\",\"url\":\"http://test.com\"}";

        String jsonString = JsonUtil.convertToJson(testObject);

        assertEquals(expectedJson, jsonString);
    }

    @Test
    public void givenInvalidObject_whenToJson_thenReturnEmptyJson() {
        JSONObject object = new JSONObject();
        String json = JsonUtil.convertToJson(object);
        assertEquals("{}", json);
    }

    @Test
    public void givenJsonArray_whenConvertToList_thenReturnList() {
        JSONArray array = new JSONArray();
        array.add("test");
        List<String> result = JsonUtil.convertJsonArrayToList(array);
        assertEquals(1, result.size());
        assertEquals("test", result.get(0));
    }

    @Test
    public void givenNull_whenConvertToList_thenReturnEmptyList() {
        List<String> result = JsonUtil.convertJsonArrayToList(null);
        assertNull(result);
    }

    @Test
    public void givenJsonObjectWithCategories_whenConvertBuildFailureToMap_thenReturnValidMap() {
        JSONArray array = new JSONArray();
        array.add("test");
        JSONObject object = new JSONObject();
        object.put("categories", array);
        Map<String, Object> result = JsonUtil.convertBuildFailureToMap(object);
        assertEquals(1, result.size());
        assertEquals(1, ((List) result.get("categories")).size());
        assertEquals("test", ((List) result.get("categories")).get(0));
    }

    @Test
    public void givenJsonObjectWithoutCategories_whenConvertBuildFailureToMap_thenReturnValidMap() {
        JSONObject object = new JSONObject();
        object.put("stuff", "testString");
        Map<String, Object> result = JsonUtil.convertBuildFailureToMap(object);
        assertEquals(1, result.size());
        assertEquals("testString", result.get("stuff"));
    }

    @Test(expected = IllegalAccessError.class)
    public void givenProtectedConstructor_whenNew_throwIllegalAccess() {
        new JsonUtil();
    }
}
