package com.example.cgi_assignment.service;

import com.example.cgi_assignment.model.web.SchedFlightDTO;
import com.example.cgi_assignment.model.SchedFlight;
import com.example.cgi_assignment.model.SchedFlightPK;
import com.example.cgi_assignment.repository.SchedFlightRepository;
import com.example.cgi_assignment.util.SpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchedFlightService {

    private final SchedFlightRepository repo;

    @Autowired
    public SchedFlightService(SchedFlightRepository repository) {
        this.repo = repository;
    }

    public List<SchedFlight> getAllSchedFlights(){
        return repo.findAll();
    }

    public List<SchedFlight> getAllByFilters(SchedFlightDTO filter) {
        return repo.findAll(SpecificationBuilder.buildSpecsByFilter(filter));
    }

    public Optional<SchedFlight> findById(SchedFlightPK id) {
        return repo.findById(id);
    }
}
