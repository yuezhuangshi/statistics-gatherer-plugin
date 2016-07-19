package org.jenkins.plugins.statistics.gatherer.util;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RestClientUtil {

    private static final Logger LOGGER = Logger.getLogger(
            RestClientUtil.class.getName());

    private RestClientUtil() {
        throw new IllegalAccessError("Utility class");
    }

    public static void postToService(final String url, Object object) {
        String jsonToPost = JSONUtil.convertToJson(object);
        Unirest.post(url)
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(jsonToPost)
                .asJsonAsync(new Callback<JsonNode>() {

                    public void failed(UnirestException e) {
                        LOGGER.log(Level.WARNING, "The request for url " + url + " has failed.", e);
                    }

                    public void completed(HttpResponse<JsonNode> response) {
                        int responseCode = response.getStatus();
                        LOGGER.log(Level.INFO, "The request for url " + url + " completed with status " + responseCode);
                    }

                    public void cancelled() {
                        LOGGER.log(Level.INFO, "The request for url " + url + " has been cancelled");
                    }

                });
    }

    public static JSONObject getJson(final String url) {
        try{
            HttpResponse<JsonNode> response = Unirest.get(url)
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .asJson();
            return response.getBody().getObject();
        }
        catch (UnirestException e){
            LOGGER.log(Level.WARNING, "Json called have failed in unirest.", e);
        }
        return null;
    }
}