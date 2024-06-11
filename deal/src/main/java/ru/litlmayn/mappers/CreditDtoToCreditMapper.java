package ru.litlmayn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.litlmayn.api.dto.CreditDto;
import ru.litlmayn.models.Credit;

@Mapper(componentModel = "spring")
public interface CreditDtoToCreditMapper {

    CreditDtoToCreditMapper MAPPER = Mappers.getMapper(CreditDtoToCreditMapper.class);

    @Mapping(source = "creditDto.isInsuranceEnabled", target = "insuranceEnabled")
    @Mapping(source = "creditDto.isSalaryClient", target = "salaryClient")
    Credit toCreditResponse(CreditDto creditDto);
}
