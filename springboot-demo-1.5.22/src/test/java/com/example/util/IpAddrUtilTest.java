package com.example.util;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IpAddrUtilTest {

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testIpAddrLast2Bytes() {
    int ret = IpAddrUtil.initLocalhostIpAddr();
    assertEquals(0, ret);
    System.out.println(IpAddrUtil.LocalhostIpLastByte);
    assertEquals(4, IpAddrUtil.LocalhostIpLastByte.length());
  }

}
