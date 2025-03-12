package com.example.cgi_assignment.service;

import com.example.cgi_assignment.model.SchedFlight;
import com.example.cgi_assignment.repository.SchedFlightDatabaseRepository;
import com.example.cgi_assignment.repository.SchedFlightRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchedFlightService {

    private final SchedFlightRepositoryI repo;

    @Autowired
    public SchedFlightService(SchedFlightRepositoryI repository) {
        this.repo = repository;
    }

    public List<SchedFlight> getAllSchedFlights(String depIata){
        return repo.findAllByDepIata(depIata);
    }
}
