package ru.litlmayn.deal.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.litlmayn.api.dto.LoanOfferDto;
import ru.litlmayn.api.dto.LoanStatementRequestDto;
import ru.litlmayn.api.dto.enums.ApplicationStatus;
import ru.litlmayn.api.dto.enums.ChangeType;
import ru.litlmayn.deal.mappers.LoanOfferRequestToClientMapper;
import ru.litlmayn.deal.models.Client;
import ru.litlmayn.deal.models.Statement;
import ru.litlmayn.deal.models.StatusHistory;
import ru.litlmayn.deal.repositories.ClientRepository;
import ru.litlmayn.deal.repositories.StatementRepository;
import ru.litlmayn.deal.services.StatementService;
import ru.litlmayn.deal.utils.FeignServiceUtil;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class StatementServiceImpl implements StatementService {

    private ClientRepository clientRepository;
    private StatementRepository statementRepository;
    private FeignServiceUtil feignServiceUtil;

    @Override
    public List<LoanOfferDto> createAnswerStatement(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("createAnswerStatement() - start: LoanStatementRequestDto={}", loanStatementRequestDto);
        Client client = createClient(loanStatementRequestDto);
        clientRepository.save(client);
        Statement statement = createStatement(client);
        statementRepository.save(statement);
        List<LoanOfferDto> offerDtoList = updateStatementId(
                feignServiceUtil.createLoanOffersDto(loanStatementRequestDto), statement.getStatementId()
        );
        log.info("createAnswerStatement() - end: List<LoanOfferDto>={}", offerDtoList);
        return offerDtoList;
    }

    private Statement createStatement(Client client) {
        log.info("createStatement() - start: Client={}", client);
        Statement statement = Statement.builder()
                .statementId(UUID.randomUUID())
                .clientId(clientRepository.findById(client.getClientId()).orElse(null))
                .status(ApplicationStatus.PREPARE_DOCUMENTS)
                .creationDate(new Date())
                .statusHistory(
                        StatusHistory.builder()
                                .status(ApplicationStatus.PREPARE_DOCUMENTS)
                                .time(new Date())
                                .changeType(ChangeType.AUTOMATIC)
                                .build())
                .build();
        log.info("createStatement() - end: Statement={}", statement);
        return statement;
    }

    private Client createClient(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("createClient() - start: LoanStatementRequestDto={}", loanStatementRequestDto);
        Client client = LoanOfferRequestToClientMapper.MAPPER.toClientResponse(loanStatementRequestDto);
        log.info("createClient() - end: Client={}", client);
        return client;
    }

    private List<LoanOfferDto> updateStatementId(List<LoanOfferDto> offerDtoList, UUID statementId) {
        log.info("updateStatementId() - start: List<LoanOfferDto>={}, statementId={}", offerDtoList, statementId);
        for (LoanOfferDto offer: offerDtoList) {
            offer.setStatementId(statementId);
        }
        log.info("updateStatementId() - end: List<LoanOfferDto>={}", offerDtoList);
        return offerDtoList;
    }
}