package org.jenkins.plugins.statistics.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by hthakkallapally on 3/17/2015.
 */
public class JSONUtil {

    public JSONUtil() {
        //Must
    }

    public static String convertToJsonStr(Object obj) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(obj);
    }
}
