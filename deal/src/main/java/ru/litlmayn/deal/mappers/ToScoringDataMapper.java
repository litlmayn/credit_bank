package ru.litlmayn.deal.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.litlmayn.api.dto.FinishRegistrationRequestDto;
import ru.litlmayn.api.dto.ScoringDataDto;
import ru.litlmayn.deal.models.Client;
import ru.litlmayn.deal.models.Statement;

@Mapper(componentModel = "spring")
public interface ToScoringDataMapper {

    ToScoringDataMapper MAPPER = Mappers.getMapper(ToScoringDataMapper.class);

    @Mapping(source = "client.passportId.series", target = "passportSeries")
    @Mapping(source = "client.passportId.number", target = "passportNumber")
    @Mapping(source = "finishRegistrationRequestDto.gender", target = "gender")
    @Mapping(source = "finishRegistrationRequestDto.maritalStatus", target = "maritalStatus")
    @Mapping(source = "finishRegistrationRequestDto.accountNumber",
            target = "accountNumber")
    @Mapping(source = "finishRegistrationRequestDto.dependentAmount", target = "dependentAmount")
    @Mapping(source = "statement.appliedOffer.requestedAmount", target = "amount")
    @Mapping(source = "statement.appliedOffer.term", target = "term")
    @Mapping(source = "statement.appliedOffer.isInsuranceEnabled", target = "isInsuranceEnabled")
    @Mapping(source = "statement.appliedOffer.isSalaryClient", target = "isSalaryClient")
    ScoringDataDto toScoringData(
            Client client, FinishRegistrationRequestDto finishRegistrationRequestDto, Statement statement
    );
}
