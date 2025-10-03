package com.springcrm.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for telemetry and metrics collection.
 * Sets up Prometheus metrics for monitoring the CRM application.
 */
@Configuration
public class TelemetryConfig {
    
    /**
     * Counter for tracking API requests
     */
    @Bean
    public Counter apiRequestCounter(MeterRegistry meterRegistry) {
        return Counter.builder("crm_api_requests_total")
                .description("Total number of API requests")
                .tag("application", "spring-crm")
                .register(meterRegistry);
    }
    
    /**
     * Counter for tracking authentication attempts
     */
    @Bean
    public Counter authAttemptsCounter(MeterRegistry meterRegistry) {
        return Counter.builder("crm_auth_attempts_total")
                .description("Total number of authentication attempts")
                .tag("application", "spring-crm")
                .register(meterRegistry);
    }
    
    /**
     * Counter for tracking successful logins
     */
    @Bean
    public Counter successfulLoginsCounter(MeterRegistry meterRegistry) {
        return Counter.builder("crm_successful_logins_total")
                .description("Total number of successful logins")
                .tag("application", "spring-crm")
                .register(meterRegistry);
    }
    
    /**
     * Counter for tracking failed logins
     */
    @Bean
    public Counter failedLoginsCounter(MeterRegistry meterRegistry) {
        return Counter.builder("crm_failed_logins_total")
                .description("Total number of failed logins")
                .tag("application", "spring-crm")
                .register(meterRegistry);
    }
    
    /**
     * Counter for tracking user registrations
     */
    @Bean
    public Counter userRegistrationsCounter(MeterRegistry meterRegistry) {
        return Counter.builder("crm_user_registrations_total")
                .description("Total number of user registrations")
                .tag("application", "spring-crm")
                .register(meterRegistry);
    }
    
    /**
     * Counter for tracking contact creations
     */
    @Bean
    public Counter contactCreationsCounter(MeterRegistry meterRegistry) {
        return Counter.builder("crm_contact_creations_total")
                .description("Total number of contact creations")
                .tag("application", "spring-crm")
                .register(meterRegistry);
    }
    
    /**
     * Counter for tracking account creations
     */
    @Bean
    public Counter accountCreationsCounter(MeterRegistry meterRegistry) {
        return Counter.builder("crm_account_creations_total")
                .description("Total number of account creations")
                .tag("application", "spring-crm")
                .register(meterRegistry);
    }
    
    /**
     * Counter for tracking task creations
     */
    @Bean
    public Counter taskCreationsCounter(MeterRegistry meterRegistry) {
        return Counter.builder("crm_task_creations_total")
                .description("Total number of task creations")
                .tag("application", "spring-crm")
                .register(meterRegistry);
    }
    
    /**
     * Counter for tracking lead creations
     */
    @Bean
    public Counter leadCreationsCounter(MeterRegistry meterRegistry) {
        return Counter.builder("crm_lead_creations_total")
                .description("Total number of lead creations")
                .tag("application", "spring-crm")
                .register(meterRegistry);
    }
    
    /**
     * Counter for tracking opportunity creations
     */
    @Bean
    public Counter opportunityCreationsCounter(MeterRegistry meterRegistry) {
        return Counter.builder("crm_opportunity_creations_total")
                .description("Total number of opportunity creations")
                .tag("application", "spring-crm")
                .register(meterRegistry);
    }
    
    /**
     * Counter for tracking campaign creations
     */
    @Bean
    public Counter campaignCreationsCounter(MeterRegistry meterRegistry) {
        return Counter.builder("crm_campaign_creations_total")
                .description("Total number of campaign creations")
                .tag("application", "spring-crm")
                .register(meterRegistry);
    }
    
    /**
     * Timer for tracking API response times
     */
    @Bean
    public Timer apiResponseTimer(MeterRegistry meterRegistry) {
        return Timer.builder("crm_api_response_time")
                .description("API response time")
                .tag("application", "spring-crm")
                .register(meterRegistry);
    }
    
    /**
     * Counter for tracking database operations
     */
    @Bean
    public Counter databaseOperationsCounter(MeterRegistry meterRegistry) {
        return Counter.builder("crm_database_operations_total")
                .description("Total number of database operations")
                .tag("application", "spring-crm")
                .register(meterRegistry);
    }
    
    /**
     * Counter for tracking errors
     */
    @Bean
    public Counter errorCounter(MeterRegistry meterRegistry) {
        return Counter.builder("crm_errors_total")
                .description("Total number of errors")
                .tag("application", "spring-crm")
                .register(meterRegistry);
    }
}
