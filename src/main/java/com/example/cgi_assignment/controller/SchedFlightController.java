package com.example.cgi_assignment.controller;

import com.example.cgi_assignment.model.API.SchedFlightDTO;
import com.example.cgi_assignment.model.SchedFlight;
import com.example.cgi_assignment.service.SchedFlightService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        System.out.println("FILTERED RESULT: " + schedFlightsFiltered.size());
        for (SchedFlight schedFlight : schedFlightsFiltered) {
            System.out.println(schedFlight);
        }

        model.addAttribute("flights", filters);
        model.addAttribute("filters", filters);
        return "flights";
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    List<SchedFlight> findByParams(SchedFlightController sortParameters) {
        throw new RuntimeException("not implemented yet!");
    }
}
