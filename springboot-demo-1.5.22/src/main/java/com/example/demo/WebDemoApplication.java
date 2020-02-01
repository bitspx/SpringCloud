package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Spring Boot Web(HTTP) Demo Application.
 * Spring Boot actuator 自动增加 下列监控端点: http://localhost:<port>/health, /info.
 * Set management.security.enabled=false in application yaml/properties to access /env, /metrics, /beans, /autoconfig etc
 * 
 * @author Bairong
 * @version 1.0 
 * @date 2019年9月9日 下午1:58:24
 * @since 1.0
 */

@SpringBootApplication
public class WebDemoApplication {
  private static final Logger logger = LoggerFactory.getLogger(WebDemoApplication.class);

	public static void main(String[] args) {
//		SpringApplication.run(WebDemoApplication.class, args);
    SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(WebDemoApplication.class).web(true);
    SpringApplication springApp = springApplicationBuilder.build();
    ConfigurableApplicationContext configAppCtx = springApp.run(args);
    logger.warn("=== SprinbBoot Application start run in " + configAppCtx.toString());
    System.out.println("=== SprinbBoot Application start run in " + configAppCtx.toString()); 
	}

}
