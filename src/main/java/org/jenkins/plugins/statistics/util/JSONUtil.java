package org.jenkins.plugins.statistics.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

/**
 * Created by hthakkallapally on 3/17/2015.
 */
public class JSONUtil {

  public static String convertToJsonStr(Object obj) {
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    return gson.toJson(obj);
  }
}
