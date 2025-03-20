package com.example.cgi_assignment.util;

import com.example.cgi_assignment.model.Seat;
import com.example.cgi_assignment.model.api.SeatDTO;
import com.example.cgi_assignment.model.api.SeatsDTO;
import com.example.cgi_assignment.model.enums.SeatTier;
import org.antlr.v4.runtime.misc.Pair;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class SeatPicker {

    public static List<Pair<Integer, Integer>> filterSeats(SeatsDTO seatsDTO, List<Integer> seatTiers, Boolean windowSeatPreferred){
        List<Pair<Integer,Integer>> recommendedSeats = new ArrayList<>();
        if(windowSeatPreferred){
            recommendedSeats = pickSeatsNearWindows(seatsDTO, filterSeatsTier(seatsDTO,seatTiers));
        } else if(seatTiers != null){
            recommendedSeats = filterSeatsTier(seatsDTO, seatTiers);
        }
        return recommendedSeats;
    }

    // If seat tier not chosen - provide all seats.
    public static List<Pair<Integer, Integer>> filterSeatsTier(SeatsDTO seatsDTO, List<Integer> seatTiers){
        List<Pair<Integer, Integer>> recommendedSeats = new ArrayList<>();
        if(seatTiers == null){
            return new ArrayList<>();
        }

        SeatDTO seat;
        for (int i = 1; i <= seatsDTO.rows; i++) {
            for (int j = 1; j <= seatsDTO.columns ; j++) {
                seat = seatsDTO.getSeat(i, j);
                if(seatTiers.contains(seat.getSeatTier().ordinal()) && !seat.isOccupied()){
                    recommendedSeats.add(seat.getSeatLocation());
                }
            }
        }

        return recommendedSeats;
    }

    private static List<Pair<Integer, Integer>> pickSeatsNearWindows(SeatsDTO seatsDto, List<Pair<Integer,Integer>> filteredSeatLocations){
        List<Pair<Integer, Integer>> recommendedSeats = new ArrayList<>();

        for (Pair<Integer, Integer> pos : filteredSeatLocations) {
            SeatDTO seat = seatsDto.getSeat(pos.a, pos.b);

            if(!seat.isOccupied() && (seat.getSeatColumn() == 1 || seat.getSeatColumn() == seatsDto.columns)){
                recommendedSeats.add(pos);
            }
        }
        return recommendedSeats;
    }

    public static List<Pair<Integer,Integer>> pickMultipleSeats(int persons, SeatsDTO seatsDTO, List<Pair<Integer,Integer>> filteredSeatLocations, boolean windowSeatPreferred) {
        //check whether it is enough free seats
        if(seatsDTO.seats.values().stream().filter(x -> x.isOccupied()).count() < persons){
            throw new IllegalArgumentException("Requested seats number has exceeded available capacity");
        }

        List<List<List<Pair<Integer,Integer>>>> seatsData = new ArrayList<>();
        for (int rows = 1; rows <= seatsDTO.rows; rows++) {
            List<List<Pair<Integer,Integer>>> row = new ArrayList<>();
            List<Pair<Integer,Integer>> rowConsecutive = new ArrayList<>();

            for (int columns = 1; columns <= seatsDTO.columns; columns++) {
                Pair<Integer, Integer> position = new Pair<>(rows, columns);
                if(filteredSeatLocations.contains(position)) {
                    rowConsecutive.add(new Pair<>(rows,columns));
                    // If we have enough seats, return
                    if(rowConsecutive.size() >= persons){
                        if(!windowSeatPreferred){
                            return rowConsecutive;
                        }
                        if(rowConsecutive.contains(new Pair<>(rows, 0)) || columns == seatsDTO.columns){
                            return rowConsecutive;
                        }
                    }
                } else{
                    if(rowConsecutive.size() > 0){
                        row.add(rowConsecutive);
                        rowConsecutive = new ArrayList<>();
                    }
                }
            }
            if(rowConsecutive.size() > 0){
                row.add(rowConsecutive);
            }
            if(row.size() > 0){
                seatsData.add(row);
            }
        }

        //sort by continuous seats in row
        var firstSort = seatsData.stream().sorted(Comparator.comparingInt(List::size)).toList();
//        var secondSort = firstSort.stream().sorted(Comparator.comparingInt(x -> x.stream().max(Comparator.comparing(List::size)).get().size())).toList();
        var secondSort = new ArrayList<>(firstSort.stream().sorted(Comparator.comparingInt(x -> x.stream().flatMap(List::stream).toList().size())).toList());
        Collections.reverse(secondSort);

        List<Pair<Integer, Integer>> recommendedPlaces = new ArrayList<>();
        int personsleft = persons;

        for (List<List<Pair<Integer, Integer>>> nestedSeats : secondSort) {
            for (List<Pair<Integer, Integer>> seatsList : nestedSeats) {

            }
        }

        return new ArrayList<>();
    }
}
