package ru.litlmayn.mappers;

import org.junit.jupiter.api.Test;
import ru.litlmayn.api.dto.EmploymentDto;
import ru.litlmayn.api.dto.FinishRegistrationRequestDto;
import ru.litlmayn.api.dto.enums.EmploymentStatus;
import ru.litlmayn.api.dto.enums.Gender;
import ru.litlmayn.api.dto.enums.MaritalStatus;
import ru.litlmayn.api.dto.enums.Position;
import ru.litlmayn.deal.mappers.FinishRegistrationToClientMapper;
import ru.litlmayn.models.Client;
import ru.litlmayn.models.Employment;
import ru.litlmayn.models.Passport;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

class FinishRegistrationToClientMapperTest {

    @Test
    void toClient() {
        Client client = Client.builder()
                .clientId(UUID.fromString("ea51104b-200e-4867-bdb3-7bfd634d3302"))
                .lastName("Иванов")
                .firstName("Иван")
                .middleName("Иванович")
                .birthdate(LocalDate.of(2000, 1, 1))
                .email("ivanov@mail.ru")
                .gender(Gender.MAN)
                .maritalStatus(MaritalStatus.MARRIED)
                .dependentAmount(2)
                .passportId(
                        Passport.builder()
                                .passportId(UUID.fromString("78474e75-2efd-475c-b333-99e9c1ad4911"))
                                .series("1234")
                                .number("123456")
                                .build()
                )
                .employmentId(
                        Employment.builder()
                                .employmentId(UUID.fromString("ce83b5e7-4838-4c28-a5ca-9d04b82d12af"))
                                .employerStatus(EmploymentStatus.EMPLOYED)
                                .employerINN("7743013902")
                                .salary(new BigDecimal("20000"))
                                .position(Position.WORKER)
                                .workExperienceTotal(30)
                                .workExperienceCurrent(25)
                                .build()
                )
                .accountNumber("12345678901")
                .build();

        FinishRegistrationRequestDto finishRegistrationRequestDto = FinishRegistrationRequestDto.builder()
                .gender(Gender.MAN)
                .maritalStatus(MaritalStatus.MARRIED)
                .dependentAmount(2)
                .passportIssueBranch("Московский район")
                .passportIssueDate(LocalDate.of(2020, 2, 2))
                .employment(
                        EmploymentDto.builder()
                                .employmentStatus(EmploymentStatus.EMPLOYED)
                                .employerINN("7743013902")
                                .salary(new BigDecimal("20000"))
                                .position(Position.WORKER)
                                .workExperienceTotal(30)
                                .workExperienceCurrent(25)
                                .build()
                )
                .accountNumber("12345678901")
                .build();

        Client totalClient = FinishRegistrationToClientMapper.MAPPER.toClient(client, finishRegistrationRequestDto);

        assertEquals(
                totalClient.getPassportId().getIssueBranch(), finishRegistrationRequestDto.getPassportIssueBranch()
        );
        assertEquals(totalClient.getPassportId().getIssueDate(), finishRegistrationRequestDto.getPassportIssueDate());
    }
}