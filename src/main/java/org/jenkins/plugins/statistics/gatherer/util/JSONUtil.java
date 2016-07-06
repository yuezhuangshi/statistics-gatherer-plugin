package org.jenkins.plugins.statistics.gatherer.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by hthakkallapally on 3/17/2015.
 */
public class JSONUtil {

    private JSONUtil() {
        throw new IllegalAccessError("Utility class");
    }

    public static String convertToJsonStr(Object obj) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(obj);
    }
}
