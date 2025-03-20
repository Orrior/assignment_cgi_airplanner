package com.example.cgi_assignment.util;

import com.example.cgi_assignment.model.SchedFlight;
import com.example.cgi_assignment.model.Seat;
import com.example.cgi_assignment.model.enums.SeatTier;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Seeder {
    //this class will be used to seed Data

    public static List<SchedFlight> seedFlights(int amount){
        List<SchedFlight> flights = new ArrayList<>();
        Random random = new Random();

        for (int j = 0; j < amount; j++) {
            StringBuilder flightnumber = new StringBuilder("tt");
            for (int i = 0; i < 3 + random.nextInt(2); i++) {
                flightnumber.append(random.nextInt(10));
            }

            ZonedDateTime departureTime = ZonedDateTime.now().plusMinutes(random.nextInt(60, 24 * 60 * 3));
            String departureIata = "TLL";
            String departureTerminal = "";
            String departureGate = "";

            ZonedDateTime arrivalTime = departureTime.plusMinutes(random.nextInt(60, 8 * 60));
            String arrivalIata = "LLT"; //TODO! Generate random IATAs, gates and terminals.
            String arrivalTerminal = "";
            String arrivalGate = "";

            SchedFlight flight = new SchedFlight(
                    flightnumber.toString(),
                    departureIata,
                    arrivalIata,
                    departureTime,
                    departureGate,
                    departureTerminal,
                    arrivalTime,
                    arrivalGate,
                    arrivalTerminal);

            if(flights.stream().noneMatch(x ->
                    x.getFlightNumber().equals(flight.getFlightNumber()) &&
                            x.getDepIata().equals(flight.getDepIata()))) {
                flights.add(flight);
            }
        }

        return flights;
    }

    public static List<Seat> seedSeats(SchedFlight schedFlight, float coefficient){
        return populateSeats(generateSeats(schedFlight), coefficient);
    }

    public static List<Seat> generateSeats(SchedFlight flight){
        //TODO! This is hardcoded implementation, should be more configurable later

        HashMap<SeatTier, Integer> seatsConfig = new HashMap<>();

        //TODO! This is not sorted and served not in order!
        seatsConfig.put(SeatTier.BUSINESS_CLASS_SEAT, 4);
        seatsConfig.put(SeatTier.FIRST_CLASS_SEAT, 5);
        seatsConfig.put(SeatTier.STRETCH_SEAT,2);
        seatsConfig.put(SeatTier.ECONOMY_SEAT, 7);

        int seatsWidth = 6;

        List<Seat> seats = new ArrayList<>();
        int rowindex = 1;

        for (var seatClass : seatsConfig.keySet()) {
            int rowDiff = 0;
            for (int row = rowindex; row < rowindex + seatsConfig.get(seatClass) ; row++) {
                for (int column = 1; column <= seatsWidth; column++) {
                    seats.add(new Seat(flight, row, column, seatClass));
                }
                rowDiff++;
            }
            rowindex += rowDiff;
        }

        return seats;
    }

    //I.e. if we wish to populate 30% of plane coefficient should be 0.3
    //TODO: idk if this is good, maybe i should improve this later...
    private static List<Seat> populateSeats(List<Seat> seats, float coefficient) {
        Random random = new Random();

        for (Seat seat : seats) {
            if(random.nextFloat() <= coefficient){
                seat.setOwnerName("John Doe");
            }
        }

        return seats;
    }
}
