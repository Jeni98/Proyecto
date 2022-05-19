package com.example.ventas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class VentasApplication {


	public static void main(String[] args) {
		SpringApplication.run(VentasApplication.class, args);
	}

}
