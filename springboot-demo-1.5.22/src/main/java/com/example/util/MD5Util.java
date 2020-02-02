/*
 * Copyright(c) 2014-2019 100 Credit.Ltd. All Rights Reserved.
 * 
 */
package com.example.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.Hex;

/**
 * class description
 * 
 * @author puxuan.song
 * @version 1.0 
 * @date 2019年9月10日 下午1:35:45
 * @since 1.0	
 */
public class MD5Util {
  /**
   * Table for byte to hex string translation.
   */
  public static String hexDigits[] = {"0", "1", "2", "3", "4", "5", "6","7", "8", "9", "a", "b", "c", "d", "e", "f" };
  
  public static String toHexString(byte[] bytes) {
    if (null == bytes) {
        return null;
    }
    // 一个字节转成2个16进制字符,通过位运算实现效率最高 
    StringBuilder sb = new StringBuilder(bytes.length << 1);
    for(int i = 0; i < bytes.length; ++i) {
        int highHex = (bytes[i] & 0xf0) >> 4;
        int lowHex = bytes[i] & 0x0f;
        sb.append(hexDigits[highHex]).append(hexDigits[lowHex]);
    }
    return sb.toString();
 }
  
  private static String md5(String data, String charset) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    MessageDigest md5 = MessageDigest.getInstance("MD5");
    byte b[] = data.getBytes(charset);
    md5.update(b, 0, b.length);
    byte[] digestBytes = md5.digest();
    return toHexString(digestBytes);
  }
  
  private static String md5(String data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    MessageDigest md5 = MessageDigest.getInstance("MD5");
    byte b[] = data.getBytes(CharEncoding.UTF_8);
    md5.update(b, 0, b.length);
    byte[] digestBytes = md5.digest();
    return Hex.encodeHexString(digestBytes);
//    return toHexString(digestBytes);
  }
  
  public static String genMd5HexStr(String msg, String charset) {
    String messageDigest = null;
    try {
      if(charset == null || charset.length() == 0) {
        messageDigest = md5(msg);
      } else {
        messageDigest = md5(msg, charset);
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Md5 Error. Cause: " + e);
    }

    return messageDigest;
  }
  
}
