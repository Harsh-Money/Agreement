package com.app.agreement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.app")
@ComponentScan(basePackages = "com.app")
@EnableJpaRepositories(basePackages = "com.app")
public class AgreementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgreementApplication.class, args);
	}

}
