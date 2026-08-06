package com.helpguru;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * HelpGuru Emergency Response Platform
 * Single Deployable Modular Monolith Backend
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class HelpGuruApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelpGuruApplication.class, args);
    }
}
