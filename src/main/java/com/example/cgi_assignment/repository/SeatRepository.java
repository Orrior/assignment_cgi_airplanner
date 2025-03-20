package com.example.cgi_assignment.repository;

import com.example.cgi_assignment.model.SchedFlight;
import com.example.cgi_assignment.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SeatRepository extends JpaRepository<Seat, UUID> {
    List<Seat> findAll();
    List<Seat> findAllByFlight(SchedFlight flight);
}
