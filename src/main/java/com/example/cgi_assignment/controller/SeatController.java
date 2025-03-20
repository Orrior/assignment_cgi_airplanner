package com.example.cgi_assignment.controller;

import com.example.cgi_assignment.model.SchedFlight;
import com.example.cgi_assignment.model.SchedFlightPK;
import com.example.cgi_assignment.model.Seat;
import com.example.cgi_assignment.model.api.SeatDTO;
import com.example.cgi_assignment.model.api.SeatsDTO;
import com.example.cgi_assignment.model.enums.SeatTier;
import com.example.cgi_assignment.service.SchedFlightService;
import com.example.cgi_assignment.service.SeatService;
import com.example.cgi_assignment.util.SeatPicker;
import com.example.cgi_assignment.util.Seeder;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Controller
public class SeatController {

    private SchedFlightService schedFlightService;
    private SeatService seatService;

    SeatController(SchedFlightService schedFlightService, SeatService seatService) {
        this.schedFlightService = schedFlightService;
        this.seatService = seatService;
    }



    @GetMapping("/seats")
    String getSeats(
            @RequestParam(name="flightnumber") String flightnumber,
            @RequestParam(name="depiata") String depIata,
            @RequestParam(name="arriata") String arrIata,
            Model model){

        if(flightnumber.isEmpty() || depIata.isEmpty() || arrIata.isEmpty()){
            return "redirect:/flights";
        }
        Optional<SchedFlight> schedFlight = schedFlightService.findById(new SchedFlightPK(flightnumber,depIata,arrIata));
        if (schedFlight.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "flight with such parameters not found");
        }
        // TODO! if no seats found by db - generate them and add them to db.

        SeatsDTO seatsDTO = seatService.getOrSeedSeats(schedFlight.get());

        model.addAttribute("flight", schedFlight.get());
        model.addAttribute("seats", seatsDTO);

        return "seat";
    }

    @PostMapping("/seats")
    String getSeatsFilter(
            @RequestParam(name="flightnumber") String flightnumber,
            @RequestParam(name="depiata") String depIata,
            @RequestParam(name="arriata") String arrIata,
            @RequestParam(name="tier", required = false) List<Integer> tiers,
            @RequestParam(name="windowPreferred", required = false, defaultValue = "false") Boolean windowPreferred,
            Model model){

        if(flightnumber.isEmpty() || depIata.isEmpty() || arrIata.isEmpty()){
            return "redirect:/flights";
        }
        Optional<SchedFlight> schedFlight = schedFlightService.findById(new SchedFlightPK(flightnumber,depIata,arrIata));
        if (schedFlight.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "flight with such parameters not found");
        }
        // TODO! if no seats found by db - generate them and add them to db.
        SeatsDTO seatsDTO = seatService.getOrSeedSeats(schedFlight.get());

        List<Pair<Integer, Integer>> recommendedSeats = SeatPicker.filterSeats(seatsDTO, tiers, windowPreferred);

        model.addAttribute("flight", schedFlight.get());
        model.addAttribute("seats", seatsDTO);
        model.addAttribute("recommendedSeats", recommendedSeats);
        model.addAttribute("tiers", tiers);

        return "seat";
    }

    @PostMapping("/seats/order")
    public String orderSeats(
            @RequestParam(name="flightnumber") String flightnumber,
            @RequestParam(name="depiata") String depIata,
            @RequestParam(name="arriata") String arrIata,
            @RequestParam(name="pickedseats") List<String> pickedSeats) {
        List<Pair<Integer, Integer>> pickedSeatsParsed = new ArrayList<>();
        //TODO try-catch here!
        for (String pickedSeat : pickedSeats) {
            List<Integer> parsedSeat = Arrays.stream(pickedSeat.split(",")).map(Integer::parseInt).toList();
        }


        throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "not implemented yet!");
    }

}
