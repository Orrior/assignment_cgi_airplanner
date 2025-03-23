package com.example.cgi_assignment.controller;

import com.example.cgi_assignment.model.SchedFlight;
import com.example.cgi_assignment.model.SchedFlightPK;
import com.example.cgi_assignment.model.Seat;
import com.example.cgi_assignment.model.web.MessagesDTO;
import com.example.cgi_assignment.model.web.SeatsDTO;
import com.example.cgi_assignment.service.SchedFlightService;
import com.example.cgi_assignment.service.SeatService;
import com.example.cgi_assignment.util.SeatPicker;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class SeatController {
    private final SchedFlightService schedFlightService;
    private final SeatService seatService;

    SeatController(SchedFlightService schedFlightService, SeatService seatService) {
        this.schedFlightService = schedFlightService;
        this.seatService = seatService;
    }

    @GetMapping("/seats")
    String getSeats(
            @RequestParam(name="flightnumber") String flightnumber,
            @RequestParam(name="depiata") String depIata,
            @RequestParam(name="arriata") String arrIata,
            Model model,
            RedirectAttributes redirectAttributes) {

        if(flightnumber.isEmpty() || depIata.isEmpty() || arrIata.isEmpty()){
            return redirectErrorToFlights(redirectAttributes,"Invalid flight parameters!");
        }
        Optional<SchedFlight> schedFlight = schedFlightService.findById(new SchedFlightPK(flightnumber,depIata,arrIata));
        if (schedFlight.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "flight with such parameters not found");
        }

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
            @RequestParam(name="windowpreferred", required = false, defaultValue = "false") Boolean windowPreferred,
            @RequestParam(name ="ticketsnumber", required = false) Integer ticketsNumber,
            Model model,
            RedirectAttributes redirectAttributes) {
        if(flightnumber.isEmpty() || depIata.isEmpty() || arrIata.isEmpty()){
            return redirectErrorToFlights(redirectAttributes,
                    "Invalid flight parameters!");
        }
        Optional<SchedFlight> schedFlight = schedFlightService.findById(new SchedFlightPK(flightnumber,depIata,arrIata));
        if (schedFlight.isEmpty()) {
            return redirectErrorToFlights(redirectAttributes,
                    "Flight with such parameters is not found.");
        }
        if(windowPreferred != null){
            model.addAttribute("windowpreferred", windowPreferred);
        }

        SeatsDTO seatsDTO = seatService.getOrSeedSeats(schedFlight.get());
        List<Pair<Integer, Integer>> recommendedSeats;

        if(ticketsNumber != null) {
            try{
                recommendedSeats = SeatPicker.filterSeatsTier(seatsDTO, tiers);
                recommendedSeats = SeatPicker.pickMultipleSeats(ticketsNumber, seatsDTO, recommendedSeats, windowPreferred);
            } catch (Exception e){
                return redirectErrorToSeats(redirectAttributes,
                        flightnumber,
                        depIata,
                        arrIata,
                        "Error:" + e.getMessage());
            }
        } else {
            recommendedSeats = SeatPicker.filterSeats(seatsDTO, tiers, windowPreferred);
        }

        model.addAttribute("flight", schedFlight.get());
        model.addAttribute("seats", seatsDTO);
        model.addAttribute("recommendedSeats", recommendedSeats);
        model.addAttribute("tiers", tiers);

        return "seat";
    }

    // If seats are successfully ordered, redirects to /flights
    @PostMapping("/seats/order")
    public String orderSeats(
            @RequestParam(name="flightnumber") String flightnumber,
            @RequestParam(name="depiata") String depIata,
            @RequestParam(name="arriata") String arrIata,
            @RequestParam(name="pickedseats") List<String> pickedSeats,
            @RequestParam(name="registername") String ownerName,
            RedirectAttributes redirectAttributes) {

        List<Pair<Integer, Integer>> pickedSeatsParsed = new ArrayList<>();
        if(ownerName == null || ownerName.isEmpty()) {
            return redirectErrorToSeats(redirectAttributes, flightnumber, depIata, arrIata,
                    "Please enter username that will be associated with ordered seat tickets");
        }

        if(flightnumber.isEmpty() || depIata.isEmpty() || arrIata.isEmpty()){
            return redirectErrorToFlights(redirectAttributes,
                    "Invalid flight parameters!");
        }
        Optional<SchedFlight> schedFlight = schedFlightService.findById(new SchedFlightPK(flightnumber,depIata,arrIata));
        if (schedFlight.isEmpty()) {
            return redirectErrorToFlights(redirectAttributes,"Flight with such parameters is not found.");
        }

        try{
            for (String pickedSeat : pickedSeats) {
                List<Integer> parsedSeat = Arrays.stream(pickedSeat.split("_")).map(Integer::parseInt).toList();
                pickedSeatsParsed.add(new Pair<>(parsedSeat.get(0), parsedSeat.get(1)));
            }
        } catch (Exception e) {
            return redirectErrorToFlights(redirectAttributes,
                    "Something went wrong during ticket ordering... Try again later.");
        }

        List<Seat> updatedSeats = new ArrayList<>();
        List<Seat> seats = seatService.getSeats(schedFlight.get());
        SeatsDTO seatsDto = seatService.mapSeats(seats);

        //check one final time seats are not occupied, then update objects
        for (Pair<Integer, Integer> pickedSeat : pickedSeatsParsed) {
            if(seatsDto.getSeat(pickedSeat.a, pickedSeat.b).isOccupied()){
                return redirectErrorToSeats(redirectAttributes,flightnumber,depIata,arrIata,
                        "chosen seats are already taken!");
            }
            Seat seat = seats.stream().filter(x ->
                    x.getSeatRow() == pickedSeat.a && x.getSeatColumn() == pickedSeat.b).findAny().get();
            seat.setOwnerName(ownerName);
            updatedSeats.add(seat);
        }
        seatService.saveSeatsAndFlush(updatedSeats);

        MessagesDTO message = new MessagesDTO();
        message.addMessage("You has successfully bought tickets for flight %s. Number of tickets: %d"
                        .formatted(flightnumber, pickedSeats.size()));
        redirectAttributes.addFlashAttribute("messageDto", message);
        return "redirect:/flights";
    }

    private String redirectErrorToFlights(RedirectAttributes redirectAttributes, String errorMessage){
        MessagesDTO messagesDTO = new MessagesDTO();
        messagesDTO.addErrors(errorMessage);
        redirectAttributes.addFlashAttribute("messageDto", messagesDTO);
        return "redirect:/flights";
    }

    private String redirectErrorToSeats(RedirectAttributes redirectAttributes,
                                        String flightnumber,
                                        String depIata,
                                        String arrIata,
                                        String errorMessage){
        MessagesDTO messagesDTO = new MessagesDTO();
        messagesDTO.addErrors(errorMessage);

        redirectAttributes.addAttribute("flightnumber", flightnumber);
        redirectAttributes.addAttribute("depiata", depIata);
        redirectAttributes.addAttribute("arriata", arrIata);
        redirectAttributes.addFlashAttribute("messageDto", messagesDTO);

        return "redirect:/seats";
    }

    @GetMapping("/greeting")
    public String debugFlight(@RequestParam(name="flightnumber") String flightnumber,
                              @RequestParam(name="depiata") String depIata,
                              @RequestParam(name="arriata") String arrIata,
                              Model model) {

        Optional<SchedFlight> schedFlight = schedFlightService.findById(new SchedFlightPK(flightnumber,depIata,arrIata));
        if (schedFlight.isEmpty()) {
            throw new IllegalArgumentException("Flight with such parameters was not found.");
        }
        SeatsDTO seatsDTO = seatService.getOrSeedSeats(schedFlight.get());
        List<Seat> seats = seatService.getSeats(schedFlight.get());

        model.addAttribute("flight", schedFlight.get());
        model.addAttribute("seatsDTO", seatsDTO);
        model.addAttribute("seats", seats);

        return "greeting";
    }
}
