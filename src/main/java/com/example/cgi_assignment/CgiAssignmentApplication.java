package com.example.cgi_assignment;

import com.example.cgi_assignment.repository.SchedFlightDatabaseRepository;
import com.example.cgi_assignment.util.Seeder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CgiAssignmentApplication {

    private static final Logger log = LoggerFactory.getLogger(CgiAssignmentApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CgiAssignmentApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(SchedFlightDatabaseRepository repository){
        return (args) -> {
            // save a few flights
            repository.saveAll(Seeder.seedList(9));

            // fetch all flights
            log.info("Flights found with findAll():");
            log.info("-----------------------------");
            repository.findAll().forEach(flightSchedule -> {
                log.info("\n" + flightSchedule.toString());
            });
            log.info("");
        };
    }

}
