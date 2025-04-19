package com.jwt_revision.Test_jwt_methods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync

// this is for testing purpose of spring boot jwt project
public class TestJwtMethodsApplication {


	public static void main(String[] args) {
		SpringApplication.run(TestJwtMethodsApplication.class, args);
	}

}
