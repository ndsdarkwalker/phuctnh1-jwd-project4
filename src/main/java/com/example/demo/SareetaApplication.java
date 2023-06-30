package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SareetaApplication {
	private static final Logger logger = LogManager.getLogger(SareetaApplication.class);

	public static void main(String[] args) {
		System.setProperty("log4j.configurationFile", "log4j2.xml");
		logger.info("Log entry:");
		SpringApplication.run(SareetaApplication.class, args);
	}

}
