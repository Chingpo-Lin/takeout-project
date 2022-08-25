package com.example.takeoutproject;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@MapperScan("com.example.takeoutproject.mapper")
public class TakeoutProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TakeoutProjectApplication.class, args);
		log.info("successfully run spring boot project...");
	}
}
