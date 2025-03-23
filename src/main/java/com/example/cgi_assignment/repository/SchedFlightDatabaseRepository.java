package com.example.cgi_assignment.repository;

import com.example.cgi_assignment.model.SchedFlight;
import com.example.cgi_assignment.model.SchedFlightPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedFlightDatabaseRepository extends
        JpaRepository<SchedFlight, SchedFlightPK>,
        JpaSpecificationExecutor<SchedFlight>,
        SchedFlightRepository {
}
