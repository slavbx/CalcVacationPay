package com.slavbx.calcvacationpay.controllers;

import com.slavbx.calcvacationpay.services.VacationPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api")
public class MainController {
    VacationPayService vacationPayService;

    @Autowired
    public MainController(VacationPayService vacationPayService) {
        this.vacationPayService = vacationPayService;
    }

    @GetMapping("/calculate") //http://localhost:8080/api/calculate?salary=29300&days=10
    public ResponseEntity<?> calcVacationPay(@RequestParam(name = "salary") String salary,
                                             @RequestParam(name = "days") String days) {
        if(salary == null || salary.equals("") || days == null || days.equals("")) {
            return new ResponseEntity<>("Error: Empty text was received!", HttpStatus.BAD_REQUEST);
        } else {
            int vacationDays = Integer.parseInt(days);
            salary = salary.replace(",", "."); //Обход неверного разделителя дробной части
            BigDecimal avgSalary = new BigDecimal(salary);
            return new ResponseEntity<>(vacationPayService.calcVacationPay(avgSalary, vacationDays), HttpStatus.OK);
        }
    }
}
