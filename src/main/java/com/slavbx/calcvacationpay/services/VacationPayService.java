package com.slavbx.calcvacationpay.services;

import org.springframework.stereotype.Service;

import java.math.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class VacationPayService {
    private final BigDecimal avgDaysInMonth = BigDecimal.valueOf(29.3); //Среднее количество дней в месяце
    private final int scale = 2; //Округление до двух знаков

    public VacationPayService() {
    }

    /* Считаем отпускные по формуле:
        Среднедневной заработок = заработок / среднее количество дней в месяце
        Отпускные = Среднедневной заработок × Количество дней отпуска
    */
    public String calcVacationPay(BigDecimal avgSalary, int vacationDays) {
        BigDecimal avgDailySalary = avgSalary.divide(avgDaysInMonth, scale, RoundingMode.HALF_EVEN); //Среднедневной заработок
        BigDecimal vacationPay = avgDailySalary.multiply(BigDecimal.valueOf(vacationDays)); //Отпускные
        return vacationPay.toPlainString();
    }

    /* Считаем отпускные по формуле:
        Среднедневной заработок = заработок / среднее количество дней в месяце
        Отпускные = Среднедневной заработок × Количество дней отпуска
        (Нерабочие праздничные дни учтены и не оплачиваются)
    */
    public String calcVacationPayByDate(BigDecimal avgSalary, LocalDate start, LocalDate end) {
        long vacationDays = ChronoUnit.DAYS.between(start, end) + 1; //Получаем количество дней отпускных
        int year = start.getYear();

        List<LocalDate> holidays = List.of(  //Список-шаблон с нерабочими праздничными днями
                LocalDate.of(year, 1, 1),
                LocalDate.of(year, 1, 2),
                LocalDate.of(year, 1, 3),
                LocalDate.of(year, 1, 4),
                LocalDate.of(year, 1, 5),
                LocalDate.of(year, 1, 6),
                LocalDate.of(year, 1, 7),
                LocalDate.of(year, 1, 8),
                LocalDate.of(year, 2, 23),
                LocalDate.of(year, 3, 8),
                LocalDate.of(year, 5, 1),
                LocalDate.of(year, 5, 9),
                LocalDate.of(year, 6, 12),
                LocalDate.of(year, 11, 4)
        );

        long vacationWoHolidays = vacationDays; //Вычитаем из оплаты те праздники, которые попадают на отпуск
        for (LocalDate h: holidays) {
            if (h.isEqual(start) || h.isAfter(start) && h.isBefore(end) || h.isEqual(end)) {
                vacationWoHolidays--;
            }
        }
        BigDecimal avgDailySalary = avgSalary.divide(avgDaysInMonth, scale, RoundingMode.HALF_EVEN); //Среднедневной заработок
        BigDecimal vacationPay = avgDailySalary.multiply(BigDecimal.valueOf(vacationWoHolidays)); //Отпускные
        return vacationPay.toPlainString();
    }
}
