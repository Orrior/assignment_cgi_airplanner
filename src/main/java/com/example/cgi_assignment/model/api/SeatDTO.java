package com.example.cgi_assignment.model.api;

import com.example.cgi_assignment.model.enums.SeatTier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.Pair;

@AllArgsConstructor
@Getter
@Setter
public class SeatDTO {
    int seatRow;
    int seatColumn;
    SeatTier seatTier;
    boolean occupied;

    @Override
    public String toString() {
        return (occupied + " SeatRow:" + seatRow + " SeatColumn:"+ seatColumn + " SeatClass: " + seatTier.toString());
    }

    public Pair<Integer, Integer> getSeatLocation(){
        return new Pair<>(seatRow, seatColumn);
    }
}
