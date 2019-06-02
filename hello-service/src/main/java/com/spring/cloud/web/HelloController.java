package com.spring.cloud.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import com.netflix.appinfo.EurekaInstanceConfig;
//import com.netflix.appinfo.InstanceInfo;

@RestController
public class HelloController {
  private final Logger logger = org.slf4j.LoggerFactory.getLogger(HelloController.class);
  
  @Autowired
  WebApplicationContext webAppCtx;
  
  @Autowired
  DiscoveryClient client;
  
  @Autowired
  EurekaInstanceConfig config;
  
//  @Autowired
//  InstanceInfo info;
  
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
    System.out.println("host:"+serviceInstance.getHost()+",URI:"+serviceInstance.getUri());     
    
    List<ServiceInstance> instanceList = client.getInstances("hello-service");
    System.out.println(instanceList);
    
    System.out.println("instanceId from Config:"+config.getInstanceId());
    
    return "Hello from HelloController";
  }
}
