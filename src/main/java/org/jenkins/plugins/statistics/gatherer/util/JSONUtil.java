package org.jenkins.plugins.statistics.gatherer.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by hthakkallapally on 3/17/2015.
 */
public class JSONUtil {

    private JSONUtil() {
        throw new IllegalAccessError("Utility class");
    }

    public static String convertToJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String convertedJson = "";
        try {
            convertedJson = mapper.writeValueAsString(object);
            return convertedJson;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertedJson;
    }
}
