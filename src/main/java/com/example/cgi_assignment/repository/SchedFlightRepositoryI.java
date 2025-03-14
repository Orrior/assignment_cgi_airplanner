package com.example.cgi_assignment.repository;

import com.example.cgi_assignment.model.API.SchedFlightDTO;
import com.example.cgi_assignment.model.SchedFlight;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface SchedFlightRepositoryI {
    List<SchedFlight> findAllByDepIata(String depIata);
    List<SchedFlight> findAll();
    List<SchedFlight> findAll(Specification<SchedFlight> spec);

}
