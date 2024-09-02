package com.slavbx.calcvacationpay.controllers;

import com.slavbx.calcvacationpay.services.VacationPayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
//@ExtendWith(MockitoExtension.class)
class MainControllerTest {
    //@Mock
    @Autowired
    VacationPayService vacationPayService;

    //@InjectMocks
    @Autowired
    MainController mainController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
    }

    @Test
    void calculate() throws Exception {
        //when(vacationPayService.calcVacationPay(BigDecimal.valueOf(29300), 10)).thenReturn("10000.00");
        mockMvc.perform(get("/api/calculate?salary=29300&days=10"))
                .andExpect(status().isOk())
                .andExpect(content().string("10000.00"));

        mockMvc.perform(get("/api/calculate?salary=29300&days="))
                .andExpect(status().isBadRequest());
    }

    @Test
    void calculateByDate() throws Exception {
        //when(vacationPayService.calcVacationPay(BigDecimal.valueOf(29300), 10)).thenReturn("10000.00");
        mockMvc.perform(get("/api/calculate?salary=29300&start=2024-01-01&end=2024-01-18"))
                .andExpect(status().isOk())
                .andExpect(content().string("10000.00"));

        mockMvc.perform(get("/api/calculate?salary=29300&start=2024-01-01&end="))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/api/calculate?salary=29300&start=2023-01-01&end=2024-01-18"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: Start date year not compare end date year!"));

        mockMvc.perform(get("/api/calculate?salary=29300&start=2024-02-01&end=2024-01-18"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: Start date greater than end date!"));
    }
}