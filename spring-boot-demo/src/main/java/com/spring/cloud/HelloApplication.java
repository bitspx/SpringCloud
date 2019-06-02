package com.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

@EnableDiscoveryClient
@SpringBootApplication
public class HelloApplication {
 
	public static void main(String[] args) {

		SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(HelloApplication.class).web(true);
    SpringApplication springApp = springApplicationBuilder.build();
    ConfigurableApplicationContext configAppCtx = springApp.run(args);
    System.out.println("== HelloApplication start run in " + configAppCtx.toString()); 
	}
}
