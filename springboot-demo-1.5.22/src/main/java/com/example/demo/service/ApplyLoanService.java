/*
 * Copyright(c) 2014-2019 100 Credit.Ltd. All Rights Reserved.
 * 
 */
package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;

/**
 * Apply Loan Service.
 * 
 * @author puxuan.song
 * @version 1.0 
 * @date 2019年9月19日 下午1:49:40
 * @since 1.0	
 */
@Service("ApplyLoanService")
public class ApplyLoanService {
  private static final Logger logger = LoggerFactory.getLogger(ApplyLoanService.class);
          
  public void handleRequest(String apiCode, JSONObject jsonParam, JSONObject jsonResult) {
    
    jsonResult.put("code", "00");

    JSONObject jsonAls = new JSONObject(true);
    JSONObject d7Json = new JSONObject(true);
    JSONObject d7IdJson = new JSONObject(true);
    JSONObject d7CellJson = new JSONObject(true);
    JSONObject bankJson = new JSONObject();
    bankJson.put("allnum", "2");
    d7IdJson.put("bank", bankJson);
    JSONObject nbankJson = new JSONObject();
    nbankJson.put("orgnum", "3");
    d7CellJson.put("nbank", nbankJson);
    d7Json.put("id", d7IdJson);
    d7Json.put("cell", d7CellJson);
    jsonAls.put("d7", d7Json);
    
    jsonResult.put("ApplyLoanStr", jsonAls);
    
    JSONObject m1Json = new JSONObject();
    JSONObject m1CellJson = new JSONObject();
    JSONObject pdlJson = new JSONObject();
    pdlJson.put("allnum", "1");
    m1CellJson.put("pdl", pdlJson);
    m1Json.put("cell", m1CellJson);
    jsonAls.put("m1", m1Json);
    // other logic according by real business
    
    JSONObject jsonFlag = jsonResult.getJSONObject("Flag");
    jsonFlag.put("ApplyLoanStr".toLowerCase(), "1"); 
    // to add more according by real logic
    
    logger.debug("JsonReust: " + jsonResult); 
  }

}
