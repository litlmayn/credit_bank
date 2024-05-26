package ru.litlmayn.calculator.services;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;

@Service

public class CalculateCreditDataService {


    public BigDecimal calculateTotalRate(Boolean isInsuranceEnabled, Boolean isSalaryClient, BigDecimal rate) {
        if (isInsuranceEnabled) {
            rate = rate.subtract(BigDecimal.valueOf(3));
        }
        if (isSalaryClient){
            rate = rate.subtract(BigDecimal.valueOf(1));
        }
        return rate;
    }

    public BigDecimal calculateMonthlyInsurancePayment(BigDecimal amount) {
        // расчет ежемесячного платежа по страховке
        return amount.multiply(BigDecimal.valueOf(0.001));
    }

    public BigDecimal calculateMonthlyAmountPayment(BigDecimal rate,BigDecimal amount, Integer term) {
        // метод для расчета ежемесячного платежа который уменьшает долг
        BigDecimal monthlyRate = rate.divide(BigDecimal.valueOf(12), MathContext.DECIMAL64)
                .divide(BigDecimal.valueOf(100), MathContext.DECIMAL64);
        return amount.multiply(monthlyRate.add(monthlyRate
                                .divide((BigDecimal.valueOf(1)
                                        .add(monthlyRate))
                                        .pow(term)
                                        .subtract(BigDecimal.valueOf(1)), MathContext.DECIMAL64
                        ))
                );
    }

    public BigDecimal calculateFullMonthlyPayment(BigDecimal rate,BigDecimal amount, Integer term) {
        // метод для расчета ежемесячного полного ежемесячного платежа
        return calculateMonthlyAmountPayment(rate, amount, term).add(calculateMonthlyInsurancePayment(amount));
    }

    public BigDecimal calculateTotalAmount(BigDecimal monthlyPayment, Integer term) {
        // расчет итоговой суммы кредита, умножаем ежемесячный платеж на количество месяцнв
        return monthlyPayment.multiply(BigDecimal.valueOf(term));
    }
}
