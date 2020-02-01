/*
 * Copyright(c) 2014-2019 100 Credit.Ltd. All Rights Reserved.
 * 
 */
package com.example.demo.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.ApplyLoanService;
import com.example.util.PropertiesUtil;
import com.example.util.SwiftNumberGenerator;

/**
 * 只是作为技术代码示例，业务逻辑不是完整的。
 * 
 * @author Bairong
 * @version 1.0 
 * @date 2019年9月11日 上午9:59:17
 * @since 1.0	
 */
@RestController
public class HuaxiangController {
  private static final Logger logger = LoggerFactory.getLogger(HuaxiangController.class);
  public static final String EMPTY_JSON = "{}";
  
  @Autowired
  WebApplicationContext webAppCtx;
  
  @Autowired
  ApplyLoanService alService;
  
  @RequestMapping(value = "/huaxiang/v1/get_report", method=RequestMethod.POST,
          produces = "application/json;charset=UTF-8",
          consumes="application/x-www-form-urlencoded;charset=utf-8")
  public String getReport(String apiCode, String jsonData, HttpServletRequest request) throws IOException {
    logger.info("Get request for URL path:"  + request.getServletPath() + ", apiCode:" + apiCode); 
    logger.warn("WebAppContext ServletContext:"+webAppCtx.getServletContext().getServerInfo());
    
    JSONObject jsonResult = new JSONObject(true);
    String swift_number = SwiftNumberGenerator.getSwiftNumberGenerator().getSwiftNumber(apiCode);
    jsonResult.put("swift_number", swift_number);
    JSONObject jsonFlag = new JSONObject(true);
    jsonResult.put("Flag", jsonFlag);
    // 检查请求参数
    String allowApiCode = PropertiesUtil.getStringValue("apiCode");
    if(allowApiCode.equalsIgnoreCase(apiCode)) {
      JSONObject jsonParam = JSON.parseObject(jsonData);
      String meal = jsonParam.getString("meal");
      if(meal !=null && (meal.equalsIgnoreCase("ApplyLoanStr") )) {
        // 调用服务处理请求，结果放到  jsonResult 中
        alService.handleRequest(apiCode, jsonParam, jsonResult);        
      }
      else {
        logger.warn("The meal name is not correct: {}", meal);
        jsonResult.fluentPut("code", "100012");
        jsonResult.put("ErrMsg", "Meal parameter Error");
      }      
    }
    else {
      // apiCode is not correct
      logger.warn("apiCode is not equal to configured: {}", apiCode);
      jsonResult.fluentPut("code", "100003");
      jsonResult.put("ErrMsg", "Apicode Error");
    }
    logger.info("JsonResult: " + jsonResult);
    return jsonResult.toJSONString();
  }

}
