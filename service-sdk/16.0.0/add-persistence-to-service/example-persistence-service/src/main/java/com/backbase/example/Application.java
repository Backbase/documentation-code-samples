package com.backbase.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// tag::application-annotations[]
@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories
@EntityScan
public class Application extends SpringBootServletInitializer {
// end::application-annotations[]
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
