package ru.litlmayn.calculator.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.litlmayn.calculator.dto.CreditDto;
import ru.litlmayn.calculator.dto.EmploymentDto;
import ru.litlmayn.calculator.dto.PaymentScheduleElementDto;
import ru.litlmayn.calculator.dto.ScoringDataDto;
import ru.litlmayn.calculator.dto.enums.EmploymentStatus;
import ru.litlmayn.calculator.dto.enums.Gender;
import ru.litlmayn.calculator.dto.enums.MaritalStatus;
import ru.litlmayn.calculator.dto.enums.Position;
import ru.litlmayn.api.exceptions.RefusalCreditException;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCreditServiceImplTest {

    @Mock
    CreditDataServiceImpl creditDataServiceImpl;
    @Mock
    PaymentElementsServiceImpl paymentElementsService;
    @Mock
    ScoringServiceImpl scoringServiceImpl;
    @InjectMocks
    CreateCreditServiceImpl createCreditServiceImpl;

    @Test
    void createCreditDto() throws RefusalCreditException{
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
                        EmploymentStatus.WORKER,
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

        when(creditDataServiceImpl.calculateFullMonthlyPayment(
                new BigDecimal("25.0"),
                new BigDecimal("100000"),
                18,
                false))
                .thenReturn(new BigDecimal("6719.18"));
        when(scoringServiceImpl.totalScoring(
                scoringDataDto))
                .thenReturn(25d);
        when(creditDataServiceImpl.calculateTotalAmount(
                new BigDecimal("6719.18"),
                18))
                .thenReturn(new BigDecimal("118311.57"));
        when(paymentElementsService.calculatePaymentScheduleElements(any()))
                .thenReturn(null);


        CreditDto creditDto = createCreditServiceImpl.createCreditDto(scoringDataDto);
        assertEquals(creditDto.getAmount(), new BigDecimal("100000"));
        assertEquals(creditDto.getTerm(), 18);
        assertEquals(creditDto.getMonthlyPayment(), new BigDecimal("6719.18"));
        assertEquals(creditDto.getRate(), new BigDecimal("25.0"));
        assertEquals(creditDto.getPsk(), new BigDecimal("118311.57"));
        assertEquals(creditDto.getIsInsuranceEnabled(), false);
        assertEquals(creditDto.getIsSalaryClient(), false);

    }
}