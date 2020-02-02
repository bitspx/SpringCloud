/*
 * Copyright(c) 2014-2019 100 Credit.Ltd. All Rights Reserved.
 * 
 */
package com.example.client;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.util.PropertiesUtil;

/**
 * class description
 * 
 * @author puxuan.song
 * @version 1.0 
 * @date 2019年9月9日 下午9:44:09
 * @since 1.0	
 */
public class HttpUtil {
  public static final int HTTP_OK = 200;
  private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
  
  public static String sendHttpClientPost(String jsonData, String httpUrl) throws UnsupportedEncodingException {
    // 使用post方式提交数据
    HttpPost httpPost = new HttpPost(httpUrl);

    List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
    nvps.add(new BasicNameValuePair("jsonData", jsonData));  
    nvps.add(new BasicNameValuePair("apiCode", PropertiesUtil.getStringValue("apiCode")));  
//    nvps.add(new BasicNameValuePair("apiCode", "123456"));
    UrlEncodedFormEntity urlEncodeFormEntity = new UrlEncodedFormEntity(nvps, "UTF-8");
    System.out.println("UrlEncodedFormEntity: " + urlEncodeFormEntity.toString()); 
    httpPost.setEntity(urlEncodeFormEntity);
    httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
    System.out.println("Post Request Body:" + httpPost.getEntity().toString());
    CloseableHttpResponse httpResponse = null;
    try {

      //创建HttpClientBuilder  
      HttpClientBuilder httpClientBuilder = HttpClients.custom(); 
      // Sets maximum time to live for persistent connections
      httpClientBuilder.setConnectionTimeToLive(3600, TimeUnit.SECONDS);
      // pro-actively evict expired connections from the connection pool using a background thread
      httpClientBuilder.evictExpiredConnections();
      // set maximum time persistent connections can stay idle while kept alive in the connection pool
      httpClientBuilder.evictIdleConnections(30, TimeUnit.SECONDS);
      //HttpClient  
      CloseableHttpClient client = httpClientBuilder.build();
      long start = System.currentTimeMillis();
   // The underlying HTTP connection is still held by the response object
   // to allow the response content to be streamed directly from the network socket.
   // In order to ensure correct deallocation of system resources
   // the user MUST call CloseableHttpResponse#close() from a finally clause.
   // Please note that if response content is not fully consumed the underlying
   // connection cannot be safely re-used and will be shut down and discarded
   // by the connection manager. 
      httpResponse = client.execute(httpPost);
      long end = System.currentTimeMillis();
      logger.info("HTTP cost time(ms):" + (end-start)); 
      // 获取服务器端返回的状态码和输入流，将输入流转换成字符串
      int respCode = httpResponse.getStatusLine().getStatusCode();
      if (respCode == HTTP_OK) {
        HttpEntity respEntity = httpResponse.getEntity();
        InputStream inputStream = respEntity.getContent();
        return readStreamToString(inputStream, "utf-8");
      }
      else {
        logger.error("Got Http Error Code:" + respCode);
      }

    } catch (Exception ex) {
      ex.printStackTrace();
      logger.error("Got exception:" + ex.getMessage(),ex);
    }
    finally {
      if(httpResponse != null) {
        try {
          httpResponse.close();
        }
        catch (IOException ex) {
          ex.printStackTrace();
          logger.error("close CloseableHttpResponse error!", ex);
        }
      }
    }

    return "";

  }

  /**
   * function description
   * 
   * @param inputStream
   * @param charset
   * @return
   */
  public static String readStreamToString(InputStream inputStream, String charset) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    byte[] data = new byte[1024];
    int len = 0;
    String result = "";
    if (inputStream != null) {
      try {
        // 循环读取输入流中数据到 数组
        while ((len = inputStream.read(data)) != -1) {
          System.out.println("Read length: " + len);
          //写入数组中数据到输出流 
          byteArrayOutputStream.write(data, 0, len);
        }
        //把输出流中数据使用指定编码转为字符串
        result = new String(byteArrayOutputStream.toByteArray(), charset);

      } catch (IOException ex) {
        ex.printStackTrace();
      }
      finally {
        try {
          byteArrayOutputStream.close();
        }
        catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }

    return result;
  }
  
  public static String readStreamToStrByReader(InputStream inputStream, String charset) {
    StringBuilder sb = new StringBuilder();
    BufferedReader bfReader = null;
    try {
      bfReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
      String line;
      int lineNb = 1;
      while ((line = bfReader.readLine()) != null) {
        sb.append(line);
        sb.append("\n");
        System.out.println("Line Number: " + lineNb);
        lineNb ++;
      }
    }
    catch (UnsupportedEncodingException uee) {
      uee.printStackTrace();
    }
    catch (IOException ioe) {
      ioe.printStackTrace();
    }
    finally {
      if(bfReader != null) {
        try {
          bfReader.close();
        }
        catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return sb.toString();
  }
  
  /**
   * Send Http Get Request
   * 
   * @param httpUrl
   * @return
   */
  public static String sendHttpClientGet(String httpUrl) {
    HttpGet httpGet = new HttpGet(httpUrl);

    CloseableHttpResponse httpResponse = null;
    try {

      RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(2000).setConnectionRequestTimeout(1000).setSocketTimeout(5000).build();
      HttpClientBuilder httpClientBuilder = HttpClients.custom();
      // Sets maximum time to live for persistent connections
      httpClientBuilder.setConnectionTimeToLive(3600, TimeUnit.SECONDS);
      // pro-actively evict expired connections from the connection pool using a background thread
      httpClientBuilder.evictExpiredConnections();
      // set maximum time persistent connections can stay idle while kept alive in the connection pool
      httpClientBuilder.evictIdleConnections(60, TimeUnit.SECONDS);
      CloseableHttpClient httpclient = httpClientBuilder.setDefaultRequestConfig(requestConfig).build();
      
      long start = System.currentTimeMillis();
      
      httpResponse = httpclient.execute(httpGet);
      long end = System.currentTimeMillis();
      logger.info("HTTP cost time(ms):" + (end-start)); 
      // 获取服务器端返回的状态码和输入流，将输入流转换成字符串
      int respCode = httpResponse.getStatusLine().getStatusCode();
      if (respCode == HTTP_OK) {
        HttpEntity respEntity = httpResponse.getEntity();
        InputStream inputStream = respEntity.getContent();       
        return readStreamToString(inputStream, "utf-8");
      }
      else {
        logger.error("Got Http Error Code:" + respCode);
      }
    }
    catch (Exception ex) {
      logger.error("Got exception:" + ex.getMessage(),ex);
    }
    finally {
      if(httpResponse != null) {
        try {
          httpResponse.close();
        }
        catch (IOException ex) {
          ex.printStackTrace();
          logger.error("close CloseableHttpResponse error!", ex);
        }
      }
    }
    return "";
  }

}
