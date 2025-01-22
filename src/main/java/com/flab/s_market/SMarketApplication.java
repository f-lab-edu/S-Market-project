package com.flab.s_market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(SMarketApplication.class, args);
	}

}
