package ru.litlmayn.calculator.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.litlmayn.calculator.services.impl.CreditDataServiceImpl;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreditDataServiceImplTest {

    @InjectMocks
    CreditDataServiceImpl creditDataServiceImpl;


    @Test
    void calculateMonthlyInsuranceFalsePayment() {
        assertEquals(creditDataServiceImpl
                        .calculateMonthlyInsurancePayment(
                                new BigDecimal("100000"), false),
                new BigDecimal("0")
        );
    }

    @Test
    void calculateMonthlyInsuranceTruePayment() {
        assertEquals(creditDataServiceImpl
                        .calculateMonthlyInsurancePayment(
                                new BigDecimal("100000"), true),
                new BigDecimal("100.000")
        );
    }

    @Test
    void calculateMonthlyAmountPayment() {
        assertEquals(
                creditDataServiceImpl.calculateMonthlyAmountPayment(
                        new BigDecimal("18"),
                        new BigDecimal("100000"),
                        18
                ), new BigDecimal("6380.57817652130100000")
        );
    }

    @Test
    void calculateFullMonthlyPaymentInsuranceTrue() {
        assertEquals(
                creditDataServiceImpl.calculateFullMonthlyPayment(
                        new BigDecimal("18"),
                        new BigDecimal("100000"),
                        18,
                        true
                ), new BigDecimal("6480.57817652130100000")
        );
    }

    @Test
    void calculateFullMonthlyPaymentInsuranceFalse() {
        assertEquals(
                creditDataServiceImpl.calculateFullMonthlyPayment(
                        new BigDecimal("18"),
                        new BigDecimal("100000"),
                        18,
                        false
                ), new BigDecimal("6380.57817652130100000")
        );
    }

    @Test
    void calculateTotalAmount() {
        assertEquals(
                creditDataServiceImpl.calculateTotalAmount(
                        new BigDecimal("6380.58"),
                        18
                ), new BigDecimal("114850.44")
        );
    }
}