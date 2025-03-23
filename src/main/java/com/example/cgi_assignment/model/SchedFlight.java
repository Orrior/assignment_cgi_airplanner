package com.example.cgi_assignment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

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
}
