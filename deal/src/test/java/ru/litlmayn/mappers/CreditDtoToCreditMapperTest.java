package ru.litlmayn.mappers;

import org.junit.jupiter.api.Test;
import ru.litlmayn.api.dto.CreditDto;
import ru.litlmayn.api.dto.PaymentScheduleElementDto;
import ru.litlmayn.models.Credit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreditDtoToCreditMapperTest {

    @Test
    void toCreditResponse() {

        CreditDto creditDto = CreditDto.builder()
                .amount(new BigDecimal("100000"))
                .term(18)
                .monthlyPayment(new BigDecimal("7000"))
                .rate(new BigDecimal("22"))
                .psk(new BigDecimal("129000"))
                .isInsuranceEnabled(false)
                .isSalaryClient(false)
                .paymentSchedule(List.of(
                        PaymentScheduleElementDto.builder()
                                .number(1)
                                .date(LocalDate.of(2024,2,2))
                                .totalPayment(new BigDecimal("7000"))
                                .interestPayment(new BigDecimal("500"))
                                .debtPayment(new BigDecimal("6500"))
                                .remainingDebt(new BigDecimal("122000"))
                                .build()
                ))
                .build();

        Credit credit = CreditDtoToCreditMapper.MAPPER.toCreditResponse(creditDto);

        assertNotNull(credit);
        assertEquals(credit.getAmount(), creditDto.getAmount());
        assertEquals(credit.getTerm(), creditDto.getTerm());
        assertEquals(credit.getMonthlyPayment(), creditDto.getMonthlyPayment());
        assertEquals(credit.getRate(), creditDto.getRate());
        assertEquals(credit.getPsk(), creditDto.getPsk());
        assertEquals(credit.getPaymentSchedule(), creditDto.getPaymentSchedule());
        assertEquals(credit.getSalaryClient(), creditDto.getIsSalaryClient());
        assertEquals(credit.getInsuranceEnabled(), creditDto.getIsInsuranceEnabled());
    }

}