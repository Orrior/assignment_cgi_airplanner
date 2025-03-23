package com.example.cgi_assignment.util;

import com.example.cgi_assignment.model.SchedFlight;
import com.example.cgi_assignment.model.Seat;
import com.example.cgi_assignment.model.enums.SeatTier;

import java.time.ZonedDateTime;
import java.util.*;

public class Seeder {
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
            String departureTerminal = "" + random.nextInt(9);
            String departureGate = "" + random.nextInt(9);

            ZonedDateTime arrivalTime = departureTime.plusMinutes(random.nextInt(60, 8 * 60));
            List<String> iatasPlaceholders = Arrays.asList("ABC", "DEF", "GHI", "JKL", "MNO", "PQR", "STU", "VWX");
            String arrivalIata = iatasPlaceholders.get(random.nextInt(iatasPlaceholders.size()));
            String arrivalTerminal = "" + random.nextInt(9);
            String arrivalGate = "" + random.nextInt(9);

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
        LinkedHashMap<SeatTier, Integer> seatsConfig = new LinkedHashMap<>();

        seatsConfig.put(SeatTier.BUSINESS_CLASS_SEAT, 4);
        seatsConfig.put(SeatTier.FIRST_CLASS_SEAT, 5);
        seatsConfig.put(SeatTier.STRETCH_SEAT,2);
        seatsConfig.put(SeatTier.ECONOMY_SEAT, 7);
        int seatsWidth = 6;

        return populateSeats(generateSeats(schedFlight, seatsConfig, seatsWidth), coefficient);
    }

    private static List<Seat> generateSeats(SchedFlight flight, LinkedHashMap<SeatTier, Integer> seatsConfig, Integer seatsWidth){
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

    private static List<Seat> populateSeats(List<Seat> seats, float coefficient) {
        Random random = new Random();

        for (Seat seat : seats) {
            //I.e. if we want to populate 30% of plane coefficient should be 0.3
            if(random.nextFloat() <= coefficient){
                seat.setOwnerName("John Doe");
            }
        }
        return seats;
    }
}
