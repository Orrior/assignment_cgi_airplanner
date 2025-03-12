package com.example.cgi_assignment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Getter
public class SchedFlightPK implements Serializable {
    private String flightNumber;
    private String depIata;
    private String arrIata;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchedFlightPK schedFlightPK)) return false;
        return flightNumber.equals(schedFlightPK.flightNumber)
                && depIata.equals(schedFlightPK.depIata)
                && arrIata.equals(schedFlightPK.arrIata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightNumber, depIata, arrIata);
    }
}
