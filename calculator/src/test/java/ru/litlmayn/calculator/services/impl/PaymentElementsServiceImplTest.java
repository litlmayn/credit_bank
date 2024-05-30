package ru.litlmayn.calculator.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.litlmayn.api.dto.CreditDto;
import ru.litlmayn.api.dto.PaymentScheduleElementDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentElementsServiceImplTest {

    @Mock
    CreditDataServiceImpl creditDataService;
    @InjectMocks
    PaymentElementsServiceImpl paymentElementsService;

    @Test
    void calculatePaymentScheduleElements() {
        CreditDto creditDto = new CreditDto(
                new BigDecimal("100000"),
                18,
                new BigDecimal("6572.87"),
                new BigDecimal("22"),
                new BigDecimal("118311.57"),
                false,
                false,
                null
        );

        when(creditDataService.calculateMonthlyInsurancePayment(
                new BigDecimal("100000"), false))
                .thenReturn(new BigDecimal("0"));
        when(creditDataService.calculateMonthlyAmountPayment(
                new BigDecimal("22"),
                new BigDecimal("100000"),
                18))
                .thenReturn(new BigDecimal("6572.87"));

        List<PaymentScheduleElementDto> elementsDto = paymentElementsService
                .calculatePaymentScheduleElements(creditDto);
        PaymentScheduleElementDto firstElement = elementsDto.getFirst();

        assertEquals(firstElement.getNumber(), 1);
        assertEquals(firstElement.getDate(), LocalDate.now().plusMonths(1));
        assertEquals(firstElement.getInterestPayment(), new BigDecimal("0.00"));
        assertEquals(firstElement.getDebtPayment(), new BigDecimal("6572.87"));
        assertEquals(firstElement.getRemainingDebt(), new BigDecimal("111738.70"));
    }
}