package ru.litlmayn.mappers;

import org.junit.jupiter.api.Test;
import ru.litlmayn.api.dto.LoanStatementRequestDto;
import ru.litlmayn.deal.mappers.LoanOfferRequestToClientMapper;
import ru.litlmayn.deal.models.Client;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LoanOfferRequestToClientMapperTest {

    @Test
    void toClientResponse() {
        LoanStatementRequestDto loanStatementRequestDto = LoanStatementRequestDto.builder()
                .amount(new BigDecimal("100000"))
                .term(18)
                .firstName("Иван")
                .lastName("Иванов")
                .middleName("Иванович")
                .email("ivanov@mail.ru")
                .birthdate(LocalDate.of(2000, 1,1 ))
                .passportNumber("1234")
                .passportSeries("123456")
                .build();
        Client client = LoanOfferRequestToClientMapper.MAPPER.toClientResponse(loanStatementRequestDto);

        assertNotNull(client);
        assertEquals(client.getFirstName(), loanStatementRequestDto.getFirstName());
        assertEquals(client.getLastName(), loanStatementRequestDto.getLastName());
        assertEquals(client.getMiddleName(), loanStatementRequestDto.getMiddleName());
        assertEquals(client.getEmail(), loanStatementRequestDto.getEmail());
        assertEquals(client.getBirthdate(), loanStatementRequestDto.getBirthdate());
        assertEquals(client.getPassportId().getNumber(), loanStatementRequestDto.getPassportNumber());
        assertEquals(client.getPassportId().getSeries(), loanStatementRequestDto.getPassportSeries());
    }
}