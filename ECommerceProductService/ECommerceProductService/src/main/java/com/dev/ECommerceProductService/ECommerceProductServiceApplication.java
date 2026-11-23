package com.dev.ECommerceProductService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
public class ECommerceProductServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ECommerceProductServiceApplication.class, args);
	}

}
