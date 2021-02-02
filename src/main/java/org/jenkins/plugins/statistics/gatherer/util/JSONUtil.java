package org.jenkins.plugins.statistics.gatherer.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by hthakkallapally on 3/17/2015.
 */
public class JSONUtil {

    private static final Logger LOGGER = Logger.getLogger(JSONUtil.class.getName());

    protected JSONUtil() {
        throw new IllegalAccessError("Utility class");
    }

    public static String convertToJson(Object object) {
        try {
            return JSON.toJSONString(object);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Json conversion failed for object " + object, e);
        }
        return "";
    }

    public static Map<String, Object> convertBuildFailureToMap(JSONObject jObject) {
        Map<String, Object> map = new HashMap<>(16);
        Set<Map.Entry<String, Object>> entries = jObject.entrySet();

        for (Map.Entry<String, Object> entry : entries) {
            String key = entry.getKey();
            if ("categories".equals(key)) {
                List<String> value = convertJsonArrayToList(jObject.getJSONArray(key));
                map.put(key, value);
            } else {
                String value = jObject.getString(key);
                map.put(key, value);
            }
        }

        return map;
    }

    public static List<String> convertJsonArrayToList(JSONArray jsonArray) {
        String jsonStr = JSON.toJSONString(jsonArray);
        return JSON.parseArray(jsonStr, String.class);
    }
}
