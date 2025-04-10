package com.adeprogramming.javagis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Main application class for the nterprise JavaGIS Application.
 * This application provides geospatial data management capabilities with STAC integration
 * for environmental monitoring and agricultural use cases.
 */
@SpringBootApplication
@EnableJpaAuditing
public class JavagisApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavagisApplication.class, args);
	}

}
