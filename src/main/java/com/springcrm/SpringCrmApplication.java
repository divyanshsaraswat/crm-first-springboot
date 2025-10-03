package com.springcrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Main Spring Boot application class for the CRM system.
 * 
 * Features:
 * - REST API for CRM operations
 * - JWT-based authentication
 * - Role-based authorization
 * - Prometheus metrics
 * - Swagger/OpenAPI documentation
 * - MSSQL database integration
 * - Environment variable support (same as Node.js project)
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class SpringCrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCrmApplication.class, args);
    }
}
