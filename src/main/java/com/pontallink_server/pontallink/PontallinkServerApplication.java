package com.pontallink_server.pontallink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PontallinkServerApplication {
	
	public String PORT = System.getenv("PORT");

	public static void main(String[] args) {
		SpringApplication.run(PontallinkServerApplication.class, args);
	}


}
