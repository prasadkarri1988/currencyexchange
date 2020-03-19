package com.conichi.codechallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.conichi.codechallenge.exception.CustomErrorDecoder;

import brave.sampler.Sampler;

@SpringBootApplication
@EnableFeignClients("com.conichi.codechallenge")
@EnableDiscoveryClient
public class Server {

	public static void main(String[] args) {
		SpringApplication.run(Server.class, args);
	}

	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}
	
}
