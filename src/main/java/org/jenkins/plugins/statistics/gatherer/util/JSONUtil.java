package org.jenkins.plugins.statistics.gatherer.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by hthakkallapally on 3/17/2015.
 */
public class JSONUtil {

    private static final Logger LOGGER = Logger.getLogger(
            RestClientUtil.class.getName());
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
            LOGGER.log(Level.WARNING, "Json conversion failed for object " + object, e);
        }
        return convertedJson;
    }
}
