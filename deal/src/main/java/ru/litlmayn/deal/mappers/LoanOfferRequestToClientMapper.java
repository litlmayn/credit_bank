package ru.litlmayn.deal.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.litlmayn.api.dto.LoanStatementRequestDto;
import ru.litlmayn.deal.models.Client;

@Mapper(componentModel = "spring")
public interface LoanOfferRequestToClientMapper {

    LoanOfferRequestToClientMapper MAPPER = Mappers.getMapper(LoanOfferRequestToClientMapper.class);

    @Mapping(source = "birthdate", target = "birthdate", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "passportSeries", target = "passportId.series")
    @Mapping(source = "passportNumber", target = "passportId.number")
    Client toClientResponse(LoanStatementRequestDto loanStatementRequestDto);
}