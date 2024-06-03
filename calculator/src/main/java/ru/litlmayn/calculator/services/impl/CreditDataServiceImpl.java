package ru.litlmayn.calculator.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.litlmayn.calculator.services.CreditDataService;

import java.math.BigDecimal;
import java.math.MathContext;


@Slf4j
@Service
public class CreditDataServiceImpl implements CreditDataService {


    public BigDecimal calculateMonthlyInsurancePayment(BigDecimal amount, Boolean isInsuranceEnabled) {
        // расчет ежемесячного платежа по страховке
        log.info("calculateMonthlyInsurancePayment() - start: amount = " + amount
                + ", isInsuranceEnabled = " + isInsuranceEnabled);
        if (!isInsuranceEnabled) {
            log.info("calculateMonthlyInsurancePayment() - end: monthlyInsurancePayment = 0");
            return new BigDecimal(0);
        }
        BigDecimal monthlyInsurancePayment = amount.multiply(
                new BigDecimal("0.001"));
        log.info("calculateMonthlyInsurancePayment() - end: monthlyInsurancePayment = " + monthlyInsurancePayment);
        return monthlyInsurancePayment;
    }

    public BigDecimal calculateMonthlyAmountPayment(BigDecimal rate, BigDecimal amount, Integer term) {
        // метод для расчета ежемесячного платежа который уменьшает долг
        log.info("calculateMonthlyAmountPayment() - start: rate = " + rate
                + ", amount = " + amount + ", term = " + term);
        BigDecimal monthlyRate = rate.divide(BigDecimal.valueOf(12), MathContext.DECIMAL64)
                .divide(BigDecimal.valueOf(100), MathContext.DECIMAL64);
        BigDecimal monthlyAmountPayment = amount.multiply(monthlyRate.add(monthlyRate
                .divide((BigDecimal.valueOf(1)
                        .add(monthlyRate))
                        .pow(term)
                        .subtract(BigDecimal.valueOf(1)), MathContext.DECIMAL64
                ))
        );
        log.info("calculateMonthlyAmountPayment() - end: monthlyAmountPayment = " + monthlyAmountPayment);
        return monthlyAmountPayment;
    }

    public BigDecimal calculateFullMonthlyPayment(
            BigDecimal rate,BigDecimal amount, Integer term, Boolean isInsuranceEnabled
    ) {
        // метод для расчета ежемесячного полного ежемесячного платежа
        log.info("calculateFullMonthlyPayment() - start: amount = " + amount + ", term = " + term
                + ", isInsuranceEnabled = " + isInsuranceEnabled);
        BigDecimal fullMonthlyPayment = calculateMonthlyAmountPayment(rate, amount, term)
                .add(calculateMonthlyInsurancePayment(amount, isInsuranceEnabled));
        log.info("calculateFullMonthlyPayment() - end: fullMonthlyPayment=" + fullMonthlyPayment);
        return fullMonthlyPayment;
    }

    public BigDecimal calculateTotalAmount(BigDecimal monthlyPayment, Integer term) {
        // расчет итоговой суммы кредита, умножаем ежемесячный платеж на количество месяцнв
        log.info("calculateTotalAmount() - start: monthlyPayment = " + monthlyPayment + ", term = " + term);
        BigDecimal totalAmount = monthlyPayment.multiply(BigDecimal.valueOf(term));
        log.info("calculateTotalAmount() - end: totalAmount = " + totalAmount);
        return totalAmount;
    }
}
