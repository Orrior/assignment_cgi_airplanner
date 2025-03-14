package com.example.cgi_assignment.util;

import com.example.cgi_assignment.model.SchedFlight;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Seeder {
    //this class will be used to seed Data

    public static List<SchedFlight> seedList(int amount){
        List<SchedFlight> flights = new ArrayList<>();
        Random random = new Random();

        for (int j = 0; j < amount; j++) {
            StringBuilder flightnumber = new StringBuilder("tt");
            for (int i = 0; i < 3 + random.nextInt(2); i++) {
                flightnumber.append(random.nextInt(10));
            }

            ZonedDateTime departureTime =
                    ZonedDateTime.now().plusMinutes(random.nextInt(60, 24 * 60 * 3)); //Schedule will range flights from 1 hour up to 3 days from now.
            String departureIata = "TLL";
            String departureTerminal = "";
            String departureGate = "";

            ZonedDateTime arrivalTime =
                    departureTime.plusMinutes(random.nextInt(60, 8 * 60)); //Flights length will vary from 1 up to 8 hours.
            String arrivalIata = "LLT"; //TODO! Generate random IATAs, gates and terminals.
            String arrivalTerminal = "";
            String arrivalGate = "";

            flights.add(new SchedFlight(
                    flightnumber.toString(),
                    departureTime,
                    departureIata,
                    departureGate,
                    departureTerminal,
                    arrivalTime,
                    arrivalIata,
                    arrivalGate,
                    arrivalTerminal));
        }
        return flights;
    }
}
