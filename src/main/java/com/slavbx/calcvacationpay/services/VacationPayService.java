package com.slavbx.calcvacationpay.services;

import org.springframework.stereotype.Service;

import java.math.*;

@Service
public class VacationPayService {

    public VacationPayService(){
    }

    public String calcVacationPay(BigDecimal avgSalary, int vacationDays) {
        /* Считаем по формуле:
            Среднедневной заработок = заработок / среднее количество дней в месяце
            Отпускные = Среднедневной заработок × Количество дней отпуска
         */
        BigDecimal avgDaysInMonth = BigDecimal.valueOf(29.3); //Среднее количество дней в месяце
        int scale = 2; //Округление до двух знаков
        BigDecimal avgDailySalary = avgSalary.divide(avgDaysInMonth, scale, RoundingMode.HALF_EVEN); //Среднедневной заработок
        BigDecimal vacationPay = avgDailySalary.multiply(BigDecimal.valueOf(vacationDays)); //Отпускные
//        System.out.println("avgSalary = " + avgSalary);
//        System.out.println("avgDailySalary = " + avgDailySalary);
//        System.out.println("vacationDays = " + vacationDays);
//        System.out.println("avgDaysInMonth = " + avgDaysInMonth);
//        System.out.println("vacationPay = " + vacationPay);
        return vacationPay.toPlainString();
    }
}
