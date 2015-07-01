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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class to make Rest Api calls
 */
public class RestClientUtil {
  private static final Logger LOGGER  = Logger.getLogger(
      RestClientUtil.class.getName());
  public static final int HTTP_OK_BEGIN = 200;
  public static final String RESPONSE_BODY = "RESPONSE_BODY";
  public static final String RESPONSE_STATUS = "RESPONSE_STATUS";

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

  public static Map<String, Object> doPostJSON(final String url, String sJsonText)
      throws  IOException, URISyntaxException {
    LOGGER.log(Level.INFO, "Post Url is -> " + url);
    PostMethod method = new PostMethod(url);
    if (sJsonText != null) {
      StringRequestEntity reqEntity = new StringRequestEntity(sJsonText,
          "application/json; charset=utf-8", null);
      method.setRequestEntity(reqEntity);
    }
    return execute(method);
  }

  public static Map<String, Object> doPutJson(final String url, String jsonStr) throws
      IOException, URISyntaxException {
    LOGGER.log(Level.INFO, "Put Url is -> " + url);
    PutMethod method = new PutMethod(url);
    if (jsonStr != null){
      StringRequestEntity reqEntity = new StringRequestEntity(jsonStr,
          "application/json; charset=utf-8", null);
      method.setRequestEntity(reqEntity);
    }
    return execute(method);
  }

  private static String responseAsString(HttpMethod method)
      throws IOException {
    String s = null;
    if (method != null) {
      InputStream in = method.getResponseBodyAsStream();
      if (in != null) {
        s = read(new InputStreamReader(in, StandardCharsets.UTF_8));
      }
    }
    return s;
  }

  private static Map<String, Object> execute(HttpMethod method)
      throws IOException {
    Map<String, Object> map = new HashMap<String, Object>();
    if (method != null) {
      try {
        int i = getHttpClient().executeMethod(method);
        map.put(RESPONSE_STATUS, i);//important to keep it as a first statement
        map.put(RESPONSE_BODY, responseAsString(method));
        return map;
      } finally {
        method.releaseConnection();
      }
    }
    return map;
  }

  private static String read(InputStreamReader in) {
    StringBuilder sb = new StringBuilder();
    String s = null;
    if (in != null) {
      BufferedReader r = new BufferedReader(in, 8192);
      try {
        for (String line = r.readLine(); line != null; line = r.readLine()) {
          sb.append(line);
        }
        s = sb.toString();
      } catch (IOException e) {
        LOGGER.log(Level.WARNING, "Failed reading Input stream", e);
      }
    }
    return s;
  }

  /**
   * Helper method to call a service and POST the data.
   *
   * @param url
   * @param modelObj
   */
  public static void postToService(final String url, Object modelObj) {
    String jsonStr = JSONUtil.convertToJsonStr(modelObj);
    try {
      Map<String, Object> responseMap = RestClientUtil.doPostJSON(url, jsonStr);
      LOGGER.log(Level.INFO, "Status code: "+ responseMap.get(RESPONSE_STATUS));
    } catch (IOException e) {
      LOGGER.log(Level.WARNING, "Failed during POST call to "+ url +" url " +
          " with JSON " +jsonStr , e);
    } catch (URISyntaxException e) {
      LOGGER.log(Level.WARNING, "Failed during POST call to "+ url +" url " +
          " with JSON " +jsonStr , e);
    }
  }

  /**
   *
   * @param url
   * @param modelObj
   */
  public static void putToService(final String url, Object modelObj) {
    String jsonStr = JSONUtil.convertToJsonStr(modelObj);
    try {
      Map<String, Object> responseMap = RestClientUtil.doPutJson(url, jsonStr);
      LOGGER.log(Level.INFO, "Status code: "+ responseMap.get(RESPONSE_STATUS));
    } catch (IOException e) {
      LOGGER.log(Level.WARNING, "Failed during PUT call to "+ url +" url " +
          " with JSON " +jsonStr, e);
    } catch (URISyntaxException e) {
      LOGGER.log(Level.WARNING, "Failed during PUT call to "+ url +" url " +
          " with JSON " +jsonStr, e);
    }
  }
}