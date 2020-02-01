package com.example.util;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.example.util.SwiftNumberGenerator;

public class SwiftNumberGeneratorTest {
  private SwiftNumberGenerator sng = null;

  @Before
  public void setUp() throws Exception {
    sng = SwiftNumberGenerator.getSwiftNumberGenerator();
  }

  @After
  public void tearDown() throws Exception {
    sng = null;
  }

  @Test
  public void testGetSwiftNumber() {
    String sn = sng.getSwiftNumber("12345");
    System.out.println("swift number:"+sn);
    System.out.println("swift number length:"+sn.length());
    assertTrue(sn.startsWith("12345"));
    assertTrue(sn.length() == (5+23)); 
    
    sn = sng.getSwiftNumber(null);
    assertTrue(sn.startsWith("null"));
    System.out.println("swift number:"+sn);
    assertEquals(27, sn.length()); 
    
    sn = sng.getSwiftNumber("");
    assertTrue(sn.startsWith("_20"));
    System.out.println("swift number:"+sn);
  }

}
