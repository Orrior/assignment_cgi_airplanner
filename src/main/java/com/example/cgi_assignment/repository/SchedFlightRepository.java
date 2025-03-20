package com.example.cgi_assignment.repository;

import com.example.cgi_assignment.model.SchedFlight;
import com.example.cgi_assignment.model.SchedFlightPK;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface SchedFlightRepository {
    List<SchedFlight> findAllByDepIata(String depIata);
    List<SchedFlight> findAll();
    List<SchedFlight> findAll(Specification<SchedFlight> spec);
    Optional<SchedFlight> findById(SchedFlightPK id);

}
