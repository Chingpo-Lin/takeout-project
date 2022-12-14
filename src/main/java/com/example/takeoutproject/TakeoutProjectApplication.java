package com.example.takeoutproject;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@MapperScan("com.example.takeoutproject.mapper")
@ServletComponentScan
@EnableCaching // enable spring cache
@EnableTransactionManagement
public class TakeoutProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TakeoutProjectApplication.class, args);
		log.info("successfully run spring boot project...");
	}
}
