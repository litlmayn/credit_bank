package ru.litlmayn.calculator.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.litlmayn.api.dto.CreditDto;
import ru.litlmayn.api.dto.EmploymentDto;
import ru.litlmayn.api.dto.ScoringDataDto;
import ru.litlmayn.api.dto.enums.EmploymentStatus;
import ru.litlmayn.api.dto.enums.Gender;
import ru.litlmayn.api.dto.enums.MaritalStatus;
import ru.litlmayn.api.dto.enums.Position;
import ru.litlmayn.api.exceptions.RefusalCreditException;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCreditServiceImplTest {

    @Mock
    CreditDataServiceImpl creditDataService;
    @Mock
    PaymentElementsServiceImpl paymentElementsService;
    @Mock
    ScoringServiceImpl scoringService;
    @InjectMocks
    CreateCreditServiceImpl createCreditService;

    @Test
    void createCreditDtoValidData() throws RefusalCreditException{
        ScoringDataDto scoringDataDto = new ScoringDataDto(
                new BigDecimal("100000"),
                18,
                "Ivan",
                "Ivanov",
                "Ivanovich",
                Gender.MAN,
                LocalDate.of(2000, 1, 1),
                "1234",
                "123456",
                LocalDate.of(2000, 2, 2),
                "Московский район",
                MaritalStatus.MARRIED,
                2,
                new EmploymentDto(
                        EmploymentStatus.EMPLOYED,
                        "7743013902",
                        new BigDecimal("20000"),
                        Position.WORKER,
                        30,
                        25
                ),
                "12345678901",
                false,
                false
        );

        when(creditDataService.calculateFullMonthlyPayment(
                new BigDecimal("25.0"),
                new BigDecimal("100000"),
                18,
                false))
                .thenReturn(new BigDecimal("6719.18"));
        when(scoringService.totalScoring(
                scoringDataDto))
                .thenReturn(25d);
        when(creditDataService.calculateTotalAmount(
                new BigDecimal("6719.18"),
                18))
                .thenReturn(new BigDecimal("118311.57"));
        when(paymentElementsService.calculatePaymentScheduleElements(any()))
                .thenReturn(null);


        CreditDto creditDto = createCreditService.createCreditDto(scoringDataDto);
        assertEquals(creditDto.getAmount(), new BigDecimal("100000"));
        assertEquals(creditDto.getTerm(), 18);
        assertEquals(creditDto.getMonthlyPayment(), new BigDecimal("6719.18"));
        assertEquals(creditDto.getRate(), new BigDecimal("25.0"));
        assertEquals(creditDto.getPsk(), new BigDecimal("118311.57"));
        assertEquals(creditDto.getIsInsuranceEnabled(), false);
        assertEquals(creditDto.getIsSalaryClient(), false);

    }
}