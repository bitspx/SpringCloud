package com.spring.cloud.eureka;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {
  public static void main(String[] args) {
    System.out.println("== start eureka server");
    new SpringApplicationBuilder(EurekaServerApplication.class).web(true).run(args);
    System.out.println("== start eureka server done");
  }
}
