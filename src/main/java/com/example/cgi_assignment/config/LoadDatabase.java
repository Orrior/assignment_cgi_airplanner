package com.example.cgi_assignment.config;

import com.example.cgi_assignment.repository.SchedFlightDatabaseRepository;
import com.example.cgi_assignment.util.Seeder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    public CommandLineRunner demo(SchedFlightDatabaseRepository repository){
        return (args) -> {
            // save a few flights
            log.info("Seeding In-memory Database... " + repository.saveAll(Seeder.seedList(9)));
        };
    }
}
