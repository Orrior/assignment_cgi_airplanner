package com.example.cgi_assignment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@IdClass(SchedFlightPK.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchedFlight {
    @Id
    @Column(name = "flight_number")
    String flightNumber;
    @Id
    @Column(name = "dep_iata")
    String depIata;
    @Id
    @Column(name = "arr_iata")
    String arrIata;

    ZonedDateTime depTime;
    String depTerminal;
    String depGate;
    ZonedDateTime arrTime;
    String arrTerminal;
    String arrGate;

    public String toString(){
        return "Flight Number:" + this.flightNumber + "\n Departure: {"
                + this.depTime
                + " IATA:" + this.depIata
                + " Gate" + this.depGate
                + " Terminal: " + this.depTerminal + "}\n Arrival: {"
                + this.arrTime
                + " IATA:" + this.arrIata
                + " Gate" + this.arrGate
                + " Terminal: " + this.arrTerminal + "}";
    }

    public SchedFlightPK getId(){
        return new SchedFlightPK(flightNumber, depIata, arrIata);
    }
}
