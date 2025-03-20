package com.example.cgi_assignment.service;

import com.example.cgi_assignment.model.SchedFlight;
import com.example.cgi_assignment.model.SchedFlightPK;
import com.example.cgi_assignment.model.Seat;
import com.example.cgi_assignment.model.api.SeatDTO;
import com.example.cgi_assignment.model.api.SeatsDTO;
import com.example.cgi_assignment.repository.SeatRepository;
import com.example.cgi_assignment.util.Seeder;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

@Service
public class SeatService {

    private SeatRepository repo;

    @Autowired
    public SeatService(SeatRepository repository) {
        this.repo = repository;
    }

    public SeatsDTO getOrSeedSeats(SchedFlight schedFlight) {
        float seedCoefficient = 0.3f;

        // Get seats.
        List<Seat> seats = getSeats(schedFlight);

        // If seats list is empty - generate seats.
        if(seats.size() == 0) {
            seats = Seeder.seedSeats(schedFlight, 0.3f);
        }

        return mapSeats(seats);
    }

    public List<Seat> getSeats(SchedFlight flight) {
        return repo.findAllByFlight(flight);
    }

    public SeatsDTO mapSeats(List<Seat> seats){
        HashMap<Pair<Integer, Integer>,SeatDTO> seatsDtos = new HashMap<>();
        char[] letters = "ABCDEK".toCharArray();
        int maxRow = 1;
        int maxColumn = 1;
        boolean occupied;

        for (Seat seat : seats) {

            if(maxRow < seat.getSeatRow()){
                maxRow = seat.getSeatRow();
            }
            if(maxColumn < seat.getSeatColumn()){
                maxColumn = seat.getSeatColumn();
            }

            if (seat.getOwnerName() == null || seat.getOwnerName().isEmpty()) {
                occupied = false;
            } else{
                occupied = true;
            }

            seatsDtos.put(
                    new Pair<>(seat.getSeatRow(), seat.getSeatColumn()),
                    new SeatDTO(seat.getSeatRow(), seat.getSeatColumn(), seat.getSeatTier(), occupied));
        }
        return new SeatsDTO(maxRow, maxColumn, letters, seatsDtos);
    }

}
