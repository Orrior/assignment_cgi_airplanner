package com.example.cgi_assignment.model;

import com.example.cgi_assignment.model.enums.SeatTier;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    UUID id;

    @ManyToOne
            @JoinColumns({
                    @JoinColumn(name = "flight_number", referencedColumnName = "flight_number"),
                    @JoinColumn(name = "dep_iata", referencedColumnName = "dep_iata"),
                    @JoinColumn(name = "arr_iata", referencedColumnName = "arr_iata"),
            })
    SchedFlight flight;
    String ownerName;

    int seatRow;
    int seatColumn;
    SeatTier seatTier;

    public Seat(SchedFlight flight, int seatRow, int seatColumn, SeatTier seatTier) {
        this.flight = flight;
        this.seatRow = seatRow;
        this.seatColumn = seatColumn;
        this.seatTier = seatTier;
    }
}
