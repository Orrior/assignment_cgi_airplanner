package com.example.cgi_assignment.controller;

import com.example.cgi_assignment.model.SchedFlight;
import com.example.cgi_assignment.model.web.SchedFlightDTO;
import com.example.cgi_assignment.service.SchedFlightService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class SchedFlightController {
    private final SchedFlightService schedFlightService;

    SchedFlightController(SchedFlightService schedFlightService){
        this.schedFlightService = schedFlightService;
    }

    @GetMapping("/flights")
    String getFlights(Model model) {
        model.addAttribute("flights", schedFlightService.getAllSchedFlights());
        model.addAttribute("filters", new SchedFlightDTO());
        return "flights";
    }

    @PostMapping("/flights")
    String getFlights(@ModelAttribute SchedFlightDTO filters, Model model) {
        List<SchedFlight> schedFlightsFiltered = schedFlightService.getAllByFilters(filters);

        model.addAttribute("flights", schedFlightsFiltered);
        model.addAttribute("filters", filters);
        return "flights";
    }
}
