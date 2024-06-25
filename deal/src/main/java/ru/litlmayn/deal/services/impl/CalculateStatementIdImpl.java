package ru.litlmayn.deal.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.litlmayn.api.dto.CreditDto;
import ru.litlmayn.api.dto.FinishRegistrationRequestDto;
import ru.litlmayn.api.dto.ScoringDataDto;
import ru.litlmayn.deal.mappers.CreditDtoToCreditMapper;
import ru.litlmayn.deal.mappers.FinishRegistrationToClientMapper;
import ru.litlmayn.deal.mappers.ToScoringDataMapper;
import ru.litlmayn.deal.models.Client;
import ru.litlmayn.deal.models.Credit;
import ru.litlmayn.deal.models.Statement;
import ru.litlmayn.deal.repositories.ClientRepository;
import ru.litlmayn.deal.repositories.CreditRepository;
import ru.litlmayn.deal.repositories.StatementRepository;
import ru.litlmayn.deal.services.CalculateStatementId;
import ru.litlmayn.deal.utils.FeignServiceUtil;

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
        log.info("calculateCredit() - start: FinishRegistrationRequestDto={}, statementIdString={}",
                finishRegistrationRequestDto, statementIdString);
        Statement statement = statementRepository.findStatementByStatementId(
                UUID.fromString(statementIdString)).orElseThrow();
        ScoringDataDto scoringDataDto = createScoringDataDto(statement, finishRegistrationRequestDto);
        CreditDto creditDto = feignServiceUtil.createCreditDto(scoringDataDto);
        Credit credit = CreditDtoToCreditMapper.MAPPER.toCreditResponse(creditDto);
        statement.setCredit(credit);
        creditRepository.save(credit);
        statementRepository.save(statement);
        log.info("calculateCredit() - end: void");
    }

    private ScoringDataDto createScoringDataDto(
            Statement statement, FinishRegistrationRequestDto finishRegistrationRequestDto
    ) {
        log.info("createScoringDataDto() - start: Statement={}, FinishRegistrationRequestDto={}",
                statement, finishRegistrationRequestDto);
        Client client = clientRepository.findStatementByClientId(statement.getClientId().getClientId()).orElseThrow();
        ScoringDataDto scoringDataDto = ToScoringDataMapper.MAPPER.toScoringData(
                client, finishRegistrationRequestDto, statement);
        client = FinishRegistrationToClientMapper.MAPPER.toClient(client, finishRegistrationRequestDto);
        clientRepository.save(client);
        log.info("createScoringDataDto() - end: ScoringDataDto={}", scoringDataDto);
        return scoringDataDto;
    }
}
