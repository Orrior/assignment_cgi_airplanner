package com.example.cgi_assignment.repository;

import com.example.cgi_assignment.model.SchedFlight;

import java.util.List;

public interface SchedFlightRepositoryI {
    List<SchedFlight> findAllByDepIata(String depIata);
}
