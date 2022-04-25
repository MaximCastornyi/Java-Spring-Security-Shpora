package com.netsdevs.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


//dlya filtrov dobavlayets package filter



@SpringBootApplication
@ComponentScans({ 
	            @ComponentScan("com.netsdevs.controller"), 
	            @ComponentScan("com.netsdevs.config")})
@EnableJpaRepositories("com.netsdevs.repository")
@EntityScan("com.netsdevs.model")
@EnableWebSecurity(debug = true) //for filtering + dobavim stroku logging.level.org.springframework.security.web.FilterChainProxy=DEBUG v nastroiki
public class EazyBankBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EazyBankBackendApplication.class, args);
	}

}
