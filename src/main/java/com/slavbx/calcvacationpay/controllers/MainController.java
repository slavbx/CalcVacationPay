package com.slavbx.calcvacationpay.controllers;

import com.slavbx.calcvacationpay.services.VacationPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.Map;

@RestController
public class MainController {
    VacationPayService vacationPayService;

    @Autowired
    public MainController(VacationPayService vacationPayService) {
        this.vacationPayService = vacationPayService;
    }

    @GetMapping("/calculate") //http://localhost:8080/calculate?salary=29300&days=10
    public ResponseEntity<Map<String, String>> calculate1(@RequestParam(name = "salary") BigDecimal avgSalary,
                                                          @RequestParam(name = "days") int vacationDays) {
        //Ответ от сервиса кладём в map для cериализации в json
        return new ResponseEntity<>(Collections.singletonMap("response", vacationPayService.calcVacationPay(avgSalary, vacationDays)), HttpStatus.OK);
    }

    @GetMapping("/calculate-by-date") //http://localhost:8080/calculate-by-date?salary=29300&start=2024-01-01&end=2024-01-18
    public ResponseEntity<Map<String, String>> calculateByDate(@RequestParam(name = "salary") BigDecimal avgSalary,
                                                  @RequestParam(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                                  @RequestParam(name = "end"  ) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        if (start.getYear() != end.getYear()) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Start date year not compare end date year!"), HttpStatus.BAD_REQUEST);
        } else if (Period.between(start, end).getDays() < 0) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Start date greater than end date!"), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("response", vacationPayService.calcVacationPayByDate(avgSalary, start, end)), HttpStatus.OK);
        }
    }
}
