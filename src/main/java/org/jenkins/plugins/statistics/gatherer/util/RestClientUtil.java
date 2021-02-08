package org.jenkins.plugins.statistics.gatherer.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author jasper
 */
public final class RestClientUtil {

    private static final Logger LOGGER = Logger.getLogger(RestClientUtil.class.getName());

    public static final String APPLICATION_JSON = "application/json";
    public static final String ACCEPT = "accept";
    public static final String CONTENT_TYPE = "Content-Type";

    protected RestClientUtil() {
        throw new IllegalAccessError("Utility class");
    }

    public static void postToService(final String url, Object object) {
        if (PropertyLoader.getShouldSendApiHttpRequests()) {
            try {
                Request request = new Request.Builder()
                    .addHeader(ACCEPT, APPLICATION_JSON)
                    .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                    .url(url)
                    .post(RequestBody.create(MediaType.parse(APPLICATION_JSON), JSON.toJSONString(object)))
                    .build();

                ClientHolder.Instance.getClient().newCall(request).enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LOGGER.log(Level.WARNING, "Statistics gather request for url[POST] " + url + " has failed", e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        int responseCode = response.code();
                        LOGGER.log(Level.FINE, "Statistics gather request for url[POST] " + url + " completed with status " + responseCode);
                        response.close();
                    }
                });
            } catch (Throwable e) {
                LOGGER.log(Level.WARNING, "Unable to request statistics gather url[POST] " + url, e);
            }
        }
    }

    public static JSONObject getJson(final String url) {
        Request request = new Request.Builder()
            .addHeader(ACCEPT, APPLICATION_JSON)
            .addHeader(CONTENT_TYPE, APPLICATION_JSON)
            .url(url)
            .get()
            .build();

        try(Response response = ClientHolder.Instance.getClient().newCall(request).execute()) {
            return JSON.parseObject(response.body().string());
        }
        catch (IOException e){
            LOGGER.log(Level.WARNING, "The request for url[GET] " + url + " has failed", e);
        }
        return null;
    }

    enum ClientHolder {
        // Singleton
        Instance;

        private transient OkHttpClient client;

        ClientHolder(){
            client = new OkHttpClient();
        }

        public OkHttpClient getClient() {
            return client;
        }
    }

}