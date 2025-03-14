package com.example.cgi_assignment.model.API;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

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
