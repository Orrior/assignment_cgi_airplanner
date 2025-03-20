package com.example.cgi_assignment.util;

import com.example.cgi_assignment.model.api.SchedFlightDTO;
import com.example.cgi_assignment.model.SchedFlight;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
public class SpecificationBuilder {
    public static Specification<SchedFlight> buildSpecsByFilter(SchedFlightDTO filter) {
        // https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getDepTime() != null && !filter.getDepTime().isEmpty()) {
                // Преобразуем строку в LocalDate (предполагается формат "yyyy-MM-dd")
                LocalDate date = LocalDate.parse(filter.getDepTime());
                ZonedDateTime startOfDay = date.atStartOfDay(ZoneId.systemDefault());
                ZonedDateTime endOfDay = startOfDay.plusDays(1);
                predicates.add(criteriaBuilder.between(root.get("depTime"), startOfDay, endOfDay));
            }

            if (filter.getArrTime() != null && !filter.getArrTime().isEmpty()) {
                LocalDate date = LocalDate.parse(filter.getArrTime());
                ZonedDateTime startOfDay = date.atStartOfDay(ZoneId.systemDefault());
                ZonedDateTime endOfDay = startOfDay.plusDays(1);
                predicates.add(criteriaBuilder.between(root.get("arrTime"), startOfDay, endOfDay));
            }

            if (filter.getFlightNumber() != null && !filter.getFlightNumber().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("flightNumber"), filter.getFlightNumber()));
            }

            if (filter.getDepIata() != null && !filter.getDepIata().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("depIata"), filter.getDepIata()));
            }

            if (filter.getArrIata() != null && !filter.getArrIata().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("arrIata"), filter.getArrIata()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
