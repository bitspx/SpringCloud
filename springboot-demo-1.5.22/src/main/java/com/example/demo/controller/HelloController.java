package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

@RestController
public class HelloController {
  private static final Logger logger = LoggerFactory.getLogger(HelloController.class);
  
  @Autowired
  WebApplicationContext webAppCtx;
  
  
  @RequestMapping(value={"/hello","/greeting"}, method={RequestMethod.GET, RequestMethod.POST}, 
          produces="text/plain;charset=utf-8")
  public String hello(String name, HttpServletRequest request) {
    logger.info("greeting time:" + System.currentTimeMillis());

    System.out.println("ServerName:"+ request.getServerName());
    System.out.println("ServerPort:"+ request.getServerPort());
    
    System.out.println("RemoteAddr:"+ request.getRemoteAddr());
    System.out.println("RemoteHost:"+ request.getRemoteHost());
    System.out.println("RemotePort:"+ request.getRemotePort());
    
    logger.info("Servlet Path:"  + request.getServletPath()); 
    logger.warn("WebAppContext ServletContext:"+webAppCtx.getServletContext().getServerInfo());
    
    System.out.println("WebAppContext ServletContext:"+webAppCtx.getServletContext().getServerInfo()); 
    
    if(name == null) {
      return "Greeting from HelloController: Welcome!";
    }
    else {
      return "Greeting from HelloController: Hello "+ name;
    }

  }
}
