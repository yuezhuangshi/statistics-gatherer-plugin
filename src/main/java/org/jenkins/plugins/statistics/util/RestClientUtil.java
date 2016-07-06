package org.jenkins.plugins.statistics.util;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jenkins.plugins.statistics.StatisticsConfiguration;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class to make Rest Api calls
 */
public class RestClientUtil {

    private static final Logger LOGGER = Logger.getLogger(
            RestClientUtil.class.getName());
    private static final String RESPONSE_BODY = "RESPONSE_BODY";
    private static final String RESPONSE_STATUS = "RESPONSE_STATUS";

    private RestClientUtil(){
        throw new IllegalAccessError("Utility class");
    }

    private static HttpClient getHttpClient() {
        final HttpClient client = new HttpClient();
        client.getParams().setParameter("http.protocol.version",
                org.apache.commons.httpclient.HttpVersion.HTTP_1_1);
        client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(3, false));
        client.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT,
                StatisticsConfiguration.get().getConnectionTimeout());
        client.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT,
                StatisticsConfiguration.get().getSocketTimeout());
        return client;
    }

    public static Map<String, Object> doPostJSON(final String url, String jsonStringToPost)
            throws IOException, URISyntaxException {
        LOGGER.log(Level.INFO, "Post Url is -> " + url);
        PostMethod method = new PostMethod(url);
        if (jsonStringToPost != null) {
            StringRequestEntity requestEntity = new StringRequestEntity(jsonStringToPost,
                    "application/json; charset=utf-8", null);
            method.setRequestEntity(requestEntity);
        }
        return execute(method);
    }

    private static String responseAsString(HttpMethod method)
            throws IOException {
        String response = null;
        if (method != null) {
            InputStream inputStream = method.getResponseBodyAsStream();
            if (inputStream != null) {
                response = read(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            }
        }
        return response;
    }

    private static Map<String, Object> execute(HttpMethod method)
            throws IOException {
        Map<String, Object> map = new HashMap<>();
        if (method != null) {
            try {
                int responseCode = getHttpClient().executeMethod(method);
                map.put(RESPONSE_STATUS, responseCode);//important to keep it as a first statement
                map.put(RESPONSE_BODY, responseAsString(method));
                return map;
            } finally {
                method.releaseConnection();
            }
        }
        return map;
    }

    private static String read(InputStreamReader in) {
        StringBuilder stringBuilder = new StringBuilder();
        String response = null;
        if (in != null) {
            BufferedReader reader = new BufferedReader(in, 8192);
            try {
                for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                    stringBuilder.append(line);
                }
                response = stringBuilder.toString();
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Failed reading Input stream", e);
            }
        }
        return response;
    }

    /**
     * Helper method to call a service and POST the data.
     *
     * @param url
     * @param modelObj
     */
    public static void postToService(final String url, Object modelObj) {
        String json = JSONUtil.convertToJsonStr(modelObj);
        try {
            Map<String, Object> responseMap = RestClientUtil.doPostJSON(url, json);
            LOGGER.log(Level.INFO, "Status code: " + responseMap.get(RESPONSE_STATUS));
        } catch (URISyntaxException | IOException e) {
            LOGGER.log(Level.WARNING, "Failed during POST call to " + url + " url " +
                    " with JSON " + json, e);
        }
    }
}