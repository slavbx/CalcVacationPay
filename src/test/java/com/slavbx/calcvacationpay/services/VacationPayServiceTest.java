package com.slavbx.calcvacationpay.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
class VacationPayServiceTest {
    @Autowired
    VacationPayService vacationPayService;

    @Test
    void calcVacationPay() {
        BigDecimal avgSalary = BigDecimal.valueOf(29300);
        int vacationDays = 10;
        assertThat(vacationPayService.calcVacationPay(avgSalary, vacationDays)).isNotNull();
        assertThat(vacationPayService.calcVacationPay(avgSalary, vacationDays).getClass()).isEqualTo(String.class);
        assertThat(vacationPayService.calcVacationPay(avgSalary, vacationDays)).isEqualTo("10000.00");
    }

    @Test
    void calcVacationPayByDate() {
        BigDecimal avgSalary = BigDecimal.valueOf(29300);
        LocalDate start = LocalDate.of(2024, 1, 23);
        LocalDate end = LocalDate.of(2024, 2, 12);
        assertThat(vacationPayService.calcVacationPayByDate(avgSalary, start, end)).isNotNull();
        assertThat(vacationPayService.calcVacationPayByDate(avgSalary, start, end).getClass()).isEqualTo(String.class);
        assertThat(vacationPayService.calcVacationPayByDate(avgSalary, start, end)).isEqualTo("21000.00");
    }
}