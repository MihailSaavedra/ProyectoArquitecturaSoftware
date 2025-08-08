package com.espe.proyecto.publicaciones_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PublicacionesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PublicacionesServiceApplication.class, args);
	}

}
