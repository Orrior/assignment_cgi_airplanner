package com.example.cgi_assignment.model.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SchedFlightDTO {
    String flightNumber;

    String depTime;
    String depIata;

    String arrTime;
    String arrIata;
}
