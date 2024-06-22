package ru.litlmayn.deal.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import ru.litlmayn.api.dto.FinishRegistrationRequestDto;
import ru.litlmayn.deal.models.Client;

@Mapper(componentModel = "sprig")
public interface FinishRegistrationToClientMapper {

    FinishRegistrationToClientMapper MAPPER = Mappers.getMapper(FinishRegistrationToClientMapper.class);

    @Mapping(source = "employment.employmentStatus", target = "employmentId.employerStatus")
    @Mapping(source = "passportIssueDate", target = "passportId.issueDate")
    @Mapping(source = "passportIssueBranch", target = "passportId.issueBranch")
    Client toClient(@MappingTarget Client client, FinishRegistrationRequestDto finishRegistrationRequestDto);
}
