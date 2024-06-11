package ru.litlmayn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.litlmayn.api.dto.FinishRegistrationRequestDto;
import ru.litlmayn.api.dto.ScoringDataDto;
import ru.litlmayn.models.Client;
import ru.litlmayn.models.Statement;

@Mapper(componentModel = "spring")
public interface ToScoringDataMapper {

    ToScoringDataMapper MAPPER = Mappers.getMapper(ToScoringDataMapper.class);

    @Mapping(source = "client.firstName", target = "firstName")
    @Mapping(source = "client.lastName", target = "lastName")
    @Mapping(source = "client.middleName", target = "middleName")
    @Mapping(source = "client.birthdate", target = "birthdate")
    @Mapping(source = "client.passportId.series", target = "passportSeries")
    @Mapping(source = "client.passportId.number", target = "passportNumber")
    @Mapping(source = "client.passportId.issueBranch", target = "passportIssueBranch")
    @Mapping(source = "client.passportId.issueDate", target = "passportIssueDate")
    @Mapping(source = "finishRegistrationRequestDto.gender", target = "gender")
    @Mapping(source = "finishRegistrationRequestDto.maritalStatus", target = "maritalStatus")
    @Mapping(
            source = "finishRegistrationRequestDto.employment.employmentStatus",
            target = "employment.employmentStatus"
    )
    @Mapping(source = "finishRegistrationRequestDto.employment.employerINN", target = "employment.employerINN")
    @Mapping(source = "finishRegistrationRequestDto.employment.salary", target = "employment.salary")
    @Mapping(source = "finishRegistrationRequestDto.employment.position", target = "employment.position")
    @Mapping(
            source = "finishRegistrationRequestDto.employment.workExperienceTotal",
            target = "employment.workExperienceTotal"
    )
    @Mapping(
            source = "finishRegistrationRequestDto.employment.workExperienceCurrent",
            target = "employment.workExperienceCurrent"
    )
    @Mapping(source = "finishRegistrationRequestDto.accountNumber",
            target = "accountNumber")
    @Mapping(source = "statement.appliedOffer.requestedAmount", target = "amount")
    @Mapping(source = "statement.appliedOffer.term", target = "term")
    @Mapping(source = "statement.appliedOffer.isInsuranceEnabled", target = "isInsuranceEnabled")
    @Mapping(source = "statement.appliedOffer.isSalaryClient", target = "isSalaryClient")
    ScoringDataDto toScoringData(
            Client client, FinishRegistrationRequestDto finishRegistrationRequestDto, Statement statement
    );
}
