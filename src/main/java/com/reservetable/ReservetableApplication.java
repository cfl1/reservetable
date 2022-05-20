package com.reservetable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReservetableApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservetableApplication.class, args);
	}

}
