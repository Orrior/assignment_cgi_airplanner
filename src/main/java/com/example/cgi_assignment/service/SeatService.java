package com.example.cgi_assignment.service;

import com.example.cgi_assignment.model.SchedFlight;
import com.example.cgi_assignment.model.Seat;
import com.example.cgi_assignment.model.web.SeatDTO;
import com.example.cgi_assignment.model.web.SeatsDTO;
import com.example.cgi_assignment.repository.SeatRepository;
import com.example.cgi_assignment.util.Seeder;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class SeatService {

    private final SeatRepository repo;

    @Autowired
    public SeatService(SeatRepository repository) {
        this.repo = repository;
    }

    public void saveSeatsAndFlush(List<Seat> seats){
        repo.saveAllAndFlush(seats);
    }

    public SeatsDTO getOrSeedSeats(SchedFlight schedFlight) {
        float seedCoefficient = 0.3f;

        List<Seat> seats = getSeats(schedFlight);
        if(seats.size() == 0) {
            seats = Seeder.seedSeats(schedFlight, seedCoefficient);
            saveSeatsAndFlush(seats);
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

            occupied = seat.getOwnerName() != null && !seat.getOwnerName().isEmpty();

            seatsDtos.put(
                    new Pair<>(seat.getSeatRow(), seat.getSeatColumn()),
                    new SeatDTO(seat.getSeatRow(), seat.getSeatColumn(), seat.getSeatTier(), occupied));
        }
        return new SeatsDTO(maxRow, maxColumn, letters, seatsDtos);
    }

}
