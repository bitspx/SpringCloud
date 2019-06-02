package com.spring.cloud.web;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

@RestController
public class HelloController {
  private final Logger logger = org.slf4j.LoggerFactory.getLogger(HelloController.class);
  
  @Autowired
  WebApplicationContext webAppCtx;
  
  @Autowired
  DiscoveryClient client;
  
  @RequestMapping(value={"/hello","/greeting"}, method=RequestMethod.GET)
  public String hello(String name, HttpServletRequest request) {
    logger.info("greeting time:" + System.currentTimeMillis());
    
    System.out.println("LocalName:"+ request.getLocalName());
    System.out.println("LocalAddr:"+ request.getLocalAddr());
    System.out.println("LocalPort:"+ request.getLocalPort());

    System.out.println("ServerName:"+ request.getServerName());
    System.out.println("ServerPort:"+ request.getServerPort());
    
    System.out.println("RemoteAddr:"+ request.getRemoteAddr());
    System.out.println("RemoteHost:"+ request.getRemoteHost());
    System.out.println("RemotePort:"+ request.getRemotePort());
    
    System.out.println("WebAppContext ServletContext:"+webAppCtx.getServletContext().getServerInfo());
    
    ServiceInstance serviceInstance = client.getLocalServiceInstance();
    System.out.println("/hello, host:"+serviceInstance.getHost()); 
    
    
    return "Hello from HelloController";
  }
}
