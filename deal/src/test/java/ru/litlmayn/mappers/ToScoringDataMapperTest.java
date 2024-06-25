package ru.litlmayn.mappers;

import org.junit.jupiter.api.Test;
import ru.litlmayn.api.dto.EmploymentDto;
import ru.litlmayn.api.dto.FinishRegistrationRequestDto;
import ru.litlmayn.api.dto.LoanOfferDto;
import ru.litlmayn.api.dto.ScoringDataDto;
import ru.litlmayn.api.dto.enums.EmploymentStatus;
import ru.litlmayn.api.dto.enums.Gender;
import ru.litlmayn.api.dto.enums.MaritalStatus;
import ru.litlmayn.api.dto.enums.Position;
import ru.litlmayn.deal.mappers.ToScoringDataMapper;
import ru.litlmayn.deal.models.Client;
import ru.litlmayn.deal.models.Passport;
import ru.litlmayn.deal.models.Statement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ToScoringDataMapperTest {


    @Test
    void toScoringData() {
        Client client = Client.builder()
                .clientId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                .lastName("Иван")
                .firstName("Иванов")
                .middleName("Иванович")
                .birthdate(LocalDate.of(2000, 1, 1))
                .email("ivaov@mail.ru")
                .gender(Gender.MAN)
                .maritalStatus(MaritalStatus.MARRIED)
                .passportId(
                        Passport.builder()
                                .passportId(UUID.fromString("c65e1eb6-4c77-4196-ac7c-9310f78c5024"))
                                .series("1234")
                                .number("123456")
                                .issueBranch("Московская область")
                                .issueDate(LocalDate.of(2020, 2, 22))
                                .build()
                )
                .build();
        FinishRegistrationRequestDto finishRegistrationRequestDto = FinishRegistrationRequestDto.builder()
                .gender(Gender.MAN)
                .maritalStatus(MaritalStatus.MARRIED)
                .dependentAmount(2)
                .passportIssueDate(LocalDate.of(2020, 2, 22))
                .passportIssueBranch("Московская область")
                .employment(
                        EmploymentDto.builder()
                                .employmentStatus(EmploymentStatus.EMPLOYED)
                                .employerINN("7743013902")
                                .salary(new BigDecimal("20000"))
                                .position(Position.WORKER)
                                .workExperienceTotal(30)
                                .workExperienceCurrent(25)
                                .build())
                .accountNumber("12345678901")
                .build();

        Statement statement = Statement.builder()
                .appliedOffer(
                        LoanOfferDto.builder()
                                .requestedAmount(new BigDecimal("100000"))
                                .term(18)
                                .isSalaryClient(false)
                                .isInsuranceEnabled(false)
                                .build()
                )
                .build();

        ScoringDataDto scoringDataDto = ToScoringDataMapper.MAPPER.toScoringData(client, finishRegistrationRequestDto, statement);
        assertNotNull(scoringDataDto);
        assertEquals(scoringDataDto.getAmount(), statement.getAppliedOffer().getRequestedAmount());
        assertEquals(scoringDataDto.getTerm(), statement.getAppliedOffer().getTerm());
        assertEquals(scoringDataDto.getFirstName(), client.getFirstName());
        assertEquals(scoringDataDto.getLastName(), client.getLastName());
        assertEquals(scoringDataDto.getMiddleName(), client.getMiddleName());
        assertEquals(scoringDataDto.getBirthdate(), client.getBirthdate());
        assertEquals(scoringDataDto.getGender(), finishRegistrationRequestDto.getGender());
        assertEquals(scoringDataDto.getPassportNumber(), client.getPassportId().getNumber());
        assertEquals(scoringDataDto.getPassportSeries(), client.getPassportId().getSeries());
        assertEquals(scoringDataDto.getPassportIssueBranch(), finishRegistrationRequestDto.getPassportIssueBranch());
        assertEquals(scoringDataDto.getPassportIssueDate(), finishRegistrationRequestDto.getPassportIssueDate());
        assertEquals(scoringDataDto.getMaritalStatus(), finishRegistrationRequestDto.getMaritalStatus());
        assertEquals(scoringDataDto.getDependentAmount(), finishRegistrationRequestDto.getDependentAmount());
        assertEquals(scoringDataDto.getEmployment().getEmploymentStatus(),
                finishRegistrationRequestDto.getEmployment().getEmploymentStatus());
        assertEquals(scoringDataDto.getEmployment().getEmployerINN(),
                finishRegistrationRequestDto.getEmployment().getEmployerINN());
        assertEquals(scoringDataDto.getEmployment().getSalary(),
                finishRegistrationRequestDto.getEmployment().getSalary());
        assertEquals(scoringDataDto.getEmployment().getPosition(),
                finishRegistrationRequestDto.getEmployment().getPosition());
        assertEquals(scoringDataDto.getEmployment().getWorkExperienceTotal(),
                finishRegistrationRequestDto.getEmployment().getWorkExperienceTotal());
        assertEquals(scoringDataDto.getEmployment().getWorkExperienceCurrent(),
                finishRegistrationRequestDto.getEmployment().getWorkExperienceCurrent());
        assertEquals(scoringDataDto.getAccountNumber(), finishRegistrationRequestDto.getAccountNumber());
        assertEquals(scoringDataDto.getIsInsuranceEnabled(), statement.getAppliedOffer().getIsInsuranceEnabled());
        assertEquals(scoringDataDto.getIsSalaryClient(), statement.getAppliedOffer().getIsSalaryClient());

    }

}