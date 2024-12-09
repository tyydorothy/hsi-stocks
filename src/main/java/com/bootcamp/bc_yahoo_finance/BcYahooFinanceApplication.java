package com.bootcamp.bc_yahoo_finance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BcYahooFinanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BcYahooFinanceApplication.class, args);
	}

}

// when to create a new springboot app (microservices) netflix, twitter
// Redis: availability, integrity
// 
