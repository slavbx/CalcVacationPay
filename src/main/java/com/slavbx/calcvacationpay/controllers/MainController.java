package com.slavbx.calcvacationpay.controllers;

import com.slavbx.calcvacationpay.services.VacationPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@RestController
@RequestMapping("/api")
public class MainController {
    VacationPayService vacationPayService;

    @Autowired
    public MainController(VacationPayService vacationPayService) {
        this.vacationPayService = vacationPayService;
    }

    @GetMapping("/calculate") //http://localhost:8080/api/calculate?salary=29300&days=10
    public ResponseEntity<String> calculate(@RequestParam(name = "salary") BigDecimal avgSalary,
                                            @RequestParam(name = "days") int vacationDays) {
        return new ResponseEntity<>(vacationPayService.calcVacationPay(avgSalary, vacationDays), HttpStatus.OK);
    }

    @GetMapping("/calculate-by-date") //http://localhost:8080/api/calculate-by-date?salary=29300&start=2024-01-01&end=2024-01-18
    public ResponseEntity<String> calculateByDate(@RequestParam(name = "salary") BigDecimal avgSalary,
                                                  @RequestParam(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                                  @RequestParam(name = "end"  ) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        if (start.getYear() != end.getYear()) {
            return new ResponseEntity<>("Error: Start date year not compare end date year!", HttpStatus.BAD_REQUEST);
        } else if (Period.between(start, end).getDays() < 0) {
            return new ResponseEntity<>("Error: Start date greater than end date!", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(vacationPayService.calcVacationPayByDate(avgSalary, start, end), HttpStatus.OK);
        }
    }
}
