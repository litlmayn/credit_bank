package ru.litlmayn.calculator.services;

import java.math.BigDecimal;

public interface CreditDataService {
    BigDecimal calculateMonthlyInsurancePayment(BigDecimal amount, Boolean isInsuranceEnabled);

    BigDecimal calculateMonthlyAmountPayment(BigDecimal rate, BigDecimal amount, Integer term);

    BigDecimal calculateFullMonthlyPayment(BigDecimal rate,BigDecimal amount, Integer term, Boolean isInsuranceEnabled);

    BigDecimal calculateTotalAmount(BigDecimal monthlyPayment, Integer term);
}
