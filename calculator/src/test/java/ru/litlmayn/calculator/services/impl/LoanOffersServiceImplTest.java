package ru.litlmayn.calculator.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.litlmayn.api.dto.LoanStatementRequestDto;
import ru.litlmayn.calculator.services.CreditDataService;
import ru.litlmayn.calculator.services.ScoringService;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanOffersServiceImplTest {
    @Mock
    CreditDataService creditDataService;
    @Mock
    ScoringService scoringService;

    @InjectMocks
    LoanOffersServiceImpl loanOffersService;

    @Test
    void createListOffers() {
        LoanStatementRequestDto request = new LoanStatementRequestDto(
                new BigDecimal("100000"),
                18,
                "Ivan",
                "Ivanov",
                "Ivanovich",
                "ivanov@mail.ru",
                LocalDate.of(2000, 1, 1),
                "1234",
                "123456"
        );
        when(scoringService.scoringInsAndSal(
                false, false)).thenReturn(25d);
        when(creditDataService.calculateFullMonthlyPayment(
                new BigDecimal("25.0"),
                new BigDecimal("100000"),
                18,
                false
        )).thenReturn(new BigDecimal("6719.18"));
        when(creditDataService.calculateTotalAmount(
                new BigDecimal("6719.18"),
                18
        )).thenReturn(new BigDecimal("120945.27"));

        when(scoringService.scoringInsAndSal(
                false, true)).thenReturn(24d);
        when(creditDataService.calculateFullMonthlyPayment(
                new BigDecimal("24.0"),
                new BigDecimal("100000"),
                18,
                false
        )).thenReturn(new BigDecimal("6670.21"));
        when(creditDataService.calculateTotalAmount(
                new BigDecimal("6670.21"),
                18
        )).thenReturn(new BigDecimal("120063.78"));

        when(scoringService.scoringInsAndSal(
                true, false)).thenReturn(22d);
        when(creditDataService.calculateFullMonthlyPayment(
                new BigDecimal("22.0"),
                new BigDecimal("100000"),
                18,
                true
        )).thenReturn(new BigDecimal("6672.87"));
        when(creditDataService.calculateTotalAmount(
                new BigDecimal("6672.87"),
                18
        )).thenReturn(new BigDecimal("120111.57"));

        when(scoringService.scoringInsAndSal(
                true, true)).thenReturn(21d);
        when(creditDataService.calculateFullMonthlyPayment(
                new BigDecimal("21.0"),
                new BigDecimal("100000"),
                18,
                true
        )).thenReturn(new BigDecimal("6624.49"));
        when(creditDataService.calculateTotalAmount(
                new BigDecimal("6624.49"),
                18
        )).thenReturn(new BigDecimal("119240.86"));

        var list = loanOffersService.createListOffers(request);
        var firstElement = list.getFirst();

        assertEquals(firstElement.getRequestedAmount(), new BigDecimal("100000"));
        assertEquals(firstElement.getTotalAmount(), new BigDecimal("120945.27"));
        assertEquals(firstElement.getTerm(), 18);
        assertEquals(firstElement.getMonthlyPayment(), new BigDecimal("6719.18"));
        assertEquals(firstElement.getRate(), new BigDecimal("25.00"));
        assertEquals(firstElement.getIsInsuranceEnabled(), false);
        assertEquals(firstElement.getIsSalaryClient(), false);
    }
}