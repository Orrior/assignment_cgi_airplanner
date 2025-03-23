package com.example.cgi_assignment.model.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.Pair;

import java.util.HashMap;

@AllArgsConstructor
@Getter
@Setter
public class SeatsDTO {
    public int rows;
    public int columns;
    public char[] columnLetters;
    public HashMap<Pair<Integer, Integer>, SeatDTO> seats;
    public SeatDTO getSeat(int row, int column){
        Pair<Integer, Integer> coords = new Pair<>(row,column);
        SeatDTO seatDTO = seats.get(coords);
        return seatDTO;
    }
}
