/*
 * Copyright(c) 2014-2019 100 Credit.Ltd. All Rights Reserved.
 * 
 */
package com.example.client;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.JSONObject;
import com.example.util.MD5Util;
import com.example.util.PropertiesUtil;

/**
 * class description
 * 
 * @author Bairong
 * @version 1.0 
 * @date 2019年9月10日 上午10:06:51
 * @since 1.0	
 */
public class HttpTest {

  /** function description
   * 
   * @param args
   */
  public static void main(String[] args) {
//    testHttpGetHello();
    
    try {
      testHttpPostHuaxiang();
    }
    catch (UnsupportedEncodingException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
    
  }

  public static void testHttpPostHuaxiang() throws UnsupportedEncodingException {
    Map<String, Object> reqMap = new HashMap<String, Object>();
    reqMap.put("meal", "ApplyLoanStr");
    String idMd5 = MD5Util.genMd5HexStr("110234199012315678", "UTF-8");
    reqMap.put("id", idMd5);
    String cellMd5 = MD5Util.genMd5HexStr("13612345678", "UTF-8");
    reqMap.put("cell", cellMd5);
    reqMap.put("name", "张三李四");
    
    JSONObject reqJson = new JSONObject(reqMap);
    String jsonString = reqJson.toJSONString();
    System.out.println("POST Request Json: " + jsonString);
    
    String postUrl = PropertiesUtil.getStringValue("httpDataUrl");
    System.out.println("POST Request URL: " + postUrl);
    String resultJson = HttpUtil.sendHttpClientPost(jsonString, postUrl); 
    System.out.println(resultJson);
  }
  
  public static void testHttpGetHello() {
    String httpUrl = PropertiesUtil.getStringValue("httpHelloUrl");    
    String param = "name=Nice:-)Day";
    String result = HttpUtil.sendHttpClientGet(httpUrl+"?"+param);  
    System.out.println("Hello Result:" + result);
    
  }

}
