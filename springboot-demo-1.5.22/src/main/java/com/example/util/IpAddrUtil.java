/*
 * Copyright(c) 2014-2019 100 Credit.Ltd. All Rights Reserved.
 * 
 */
package com.example.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * class description
 * 
 * @author Bairong
 * @version 1.0
 * @date 2019年9月18日 下午9:57:12
 * @since 1.0
 */
public class IpAddrUtil {
  private static final Logger logger = LoggerFactory.getLogger(IpAddrUtil.class);
  public static String LocalHostIpAddr = null;
  public static String LocalhostIpLastByte = null;

  public static int initLocalhostIpAddr() {
    try {
      InetAddress localHostAddr = InetAddress.getLocalHost();
      String hostName = localHostAddr.getHostName();
      String localAddrStr = localHostAddr.getHostAddress();
      logger.warn("localHost IP address : " + localAddrStr + ", hostname:" + hostName);
      InetAddress[] addrList = InetAddress.getAllByName(hostName);
      logger.warn("find hostname IP address array size:" + addrList.length);

      List<InetAddress> siteLocalAddrs = new ArrayList<InetAddress>();
      for (InetAddress addr : addrList) {
        if (!(addr instanceof Inet4Address) || addr.isLoopbackAddress() || addr.isLinkLocalAddress()) {
          continue;
        }
        else if (addr.isSiteLocalAddress()) {
          logger.warn("Find Site Local Addr:" + addr.getHostAddress());
          siteLocalAddrs.add(addr);
        }
      }

      String ipAddrStr = null;
      if (siteLocalAddrs.isEmpty()) {
        ipAddrStr = localAddrStr;
        logger.warn("Not find Site Local Addr, use localHostAddr:" + localAddrStr);
      }
      if (siteLocalAddrs.size() == 1) {
        ipAddrStr = siteLocalAddrs.get(0).getHostAddress();
        logger.warn("Find one Site Local Addr, use it:" + ipAddrStr);
      }
      else if (siteLocalAddrs.size() > 1) {
        ipAddrStr = siteLocalAddrs.get(0).getHostAddress();
        logger.error("Find More Than One Site Local Addr, Use First One:" + ipAddrStr);
      }

      StringBuilder suffixBuilder = new StringBuilder();
      String ipLastByte = ipAddrStr.split("\\.")[3];
      String ipThirdByte = ipAddrStr.split("\\.")[2];

      int lastIp = Integer.parseInt(ipLastByte);
      String lastIpHex = Integer.toHexString(lastIp);

      int thirdIp = Integer.parseInt(ipThirdByte);
      String thirdIpHex = Integer.toHexString(thirdIp);

      if (thirdIpHex.length() == 1) {
        suffixBuilder.append("0");
      }
      suffixBuilder.append(thirdIpHex.toUpperCase());
      if (lastIpHex.length() == 1) {
        suffixBuilder.append("0");
      }
      suffixBuilder.append(lastIpHex.toUpperCase());

      LocalhostIpLastByte = suffixBuilder.toString();
      LocalHostIpAddr = ipAddrStr;
      logger.warn("LocalhostIpLastByte: " + LocalhostIpLastByte + ", LocalhostIpAddr:" + LocalHostIpAddr);
    }
    catch (UnknownHostException e) {
      logger.error("Got exception for localhost IP Address:" + e.getMessage());
      return -1;
    }

    if (LocalhostIpLastByte != null && LocalhostIpLastByte.length() == 4) {
      logger.warn("Got Localhost IP Success! LocalhostIpLastByte: " + LocalhostIpLastByte);
      return 0;
    }
    else {
      logger.error("Got Localhost IP Failed! LocalhostIpLastByte: " + LocalhostIpLastByte);
      return -1;
    }
  }
}
