package com.example.cgi_assignment.service;

import com.example.cgi_assignment.model.API.SchedFlightDTO;
import com.example.cgi_assignment.model.SchedFlight;
import com.example.cgi_assignment.repository.SchedFlightDatabaseRepository;
import com.example.cgi_assignment.repository.SchedFlightRepositoryI;
import com.example.cgi_assignment.util.SpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class SchedFlightService {

    private final SchedFlightRepositoryI repo;

    @Autowired
    public SchedFlightService(SchedFlightRepositoryI repository) {
        this.repo = repository;
    }

    public List<SchedFlight> getAllSchedFlights(){
        return repo.findAll();
        //TODO pagination?
    }

    public List<SchedFlight> getAllByFilters(SchedFlightDTO filter) {
        return repo.findAll(SpecificationBuilder.buildSpecsByFilter(filter));
        //Filter by Date
    }
}
