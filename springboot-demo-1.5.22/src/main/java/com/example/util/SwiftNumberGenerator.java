/*
 * Copyright(c) 2014-2019 100 Credit.Ltd. All Rights Reserved.
 * 
 */
package com.example.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * class description
 * 
 * @author puxuan.song
 * @version 1.0 
 * @date 2019年9月18日 下午5:29:01
 * @since 1.0	
 */
public class SwiftNumberGenerator {
  private static Logger logger = LoggerFactory.getLogger(SwiftNumberGenerator.class);
  
  public static String TIME_MS_PATTERN = "yyyyMMddHHmmssSSS";
  public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(TIME_MS_PATTERN);
  private static SwiftNumberGenerator Instance = new SwiftNumberGenerator();
  
  private static int SEQ_ID;

  static {
    //初始设置随机值，大于等于1000
    SEQ_ID = new Random().nextInt(10000);
    if(SEQ_ID < 1000 ) {
      SEQ_ID = 1000 + SEQ_ID;
    }
    logger.warn("The initial SEQ_ID is {}", SEQ_ID);
  }
  /**
   * Singleton Pattern.
   *
   */
  private SwiftNumberGenerator() {   }
  
  public static SwiftNumberGenerator getSwiftNumberGenerator() {
    return Instance;
  }
  
  private synchronized int getNextSeqId() {
    return SEQ_ID ++;    
  }
  
  public String getSwiftNumber(String apiCode) {
    String dateTimeMs = dateTimeFormatter.format(LocalDateTime.now());
    String nextSeq = String.valueOf(getNextSeqId());   
    String seqNum = nextSeq.substring(nextSeq.length() - 4);
    StringBuilder sb = new StringBuilder();
    sb.append(apiCode).append("_").append(dateTimeMs);
    sb.append("_").append(seqNum);
    return sb.toString();    
  }

}
