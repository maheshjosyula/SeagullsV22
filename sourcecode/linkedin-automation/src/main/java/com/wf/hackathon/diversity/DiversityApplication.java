package com.wf.hackathon.diversity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class DiversityApplication {
	public static void main(String[] args) {
		SpringApplication.run(DiversityApplication.class, args);
	}
}
