package ru.litlmayn.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.litlmayn.api.dto.CreditDto;
import ru.litlmayn.api.dto.FinishRegistrationRequestDto;
import ru.litlmayn.api.dto.ScoringDataDto;
import ru.litlmayn.mappers.*;
import ru.litlmayn.models.Client;
import ru.litlmayn.models.Credit;
import ru.litlmayn.models.Statement;
import ru.litlmayn.repositories.ClientRepository;
import ru.litlmayn.repositories.CreditRepository;
import ru.litlmayn.repositories.StatementRepository;
import ru.litlmayn.services.CalculateStatementId;
import ru.litlmayn.utils.FeignServiceUtil;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CalculateStatementIdImpl implements CalculateStatementId {

    private ClientRepository clientRepository;
    private CreditRepository creditRepository;
    private StatementRepository statementRepository;
    private FeignServiceUtil feignServiceUtil;

    @Override
    public void calculateCredit(FinishRegistrationRequestDto finishRegistrationRequestDto, String statementIdString) {
        log.info("calculateCredit() - start: FinishRegistrationRequestDto = " + finishRegistrationRequestDto
            + ", statementIdString = " + statementIdString);
        Statement statement = statementRepository.findStatementByStatementId(
                UUID.fromString(statementIdString)).orElseThrow();
        ScoringDataDto scoringDataDto = createScoringDataDto(statement, finishRegistrationRequestDto);
        CreditDto creditDto = feignServiceUtil.createCreditDto(scoringDataDto);
        Credit credit = CreditDtoToCreditMapper.MAPPER.toCreditResponse(creditDto);
        statement.setCredit(credit);
        statementRepository.save(statement);
        creditRepository.save(credit);
        log.info("calculateCredit() - end: void");
    }

    private ScoringDataDto createScoringDataDto(
            Statement statement, FinishRegistrationRequestDto finishRegistrationRequestDto
    ) {
        log.info("createScoringDataDto() - start: Statement = " + statement
            + ", FinishRegistrationRequestDto = " + finishRegistrationRequestDto);
        Client client = clientRepository.findStatementByClientId(statement.getClientId().getClientId()).orElseThrow();
        ScoringDataDto scoringDataDto = ToScoringDataMapper.MAPPER.toScoringData(
                client, finishRegistrationRequestDto, statement);
        log.info("createScoringDataDto() - end: ScoringDataDto = " + scoringDataDto);
        return scoringDataDto;
    }
}
