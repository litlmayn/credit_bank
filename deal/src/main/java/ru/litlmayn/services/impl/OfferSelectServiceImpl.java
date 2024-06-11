package ru.litlmayn.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.litlmayn.api.dto.LoanOfferDto;
import ru.litlmayn.api.dto.enums.ApplicationStatus;
import ru.litlmayn.api.dto.enums.ChangeType;
import ru.litlmayn.models.Statement;
import ru.litlmayn.models.StatusHistory;
import ru.litlmayn.repositories.StatementRepository;
import ru.litlmayn.services.OfferSelectService;

import java.util.Date;

@Slf4j
@AllArgsConstructor
@Service
public class OfferSelectServiceImpl implements OfferSelectService {

    private StatementRepository statementRepository;

    @Override
    public void updateLoanOfferInStatement(LoanOfferDto loanOfferDto) {
        log.info("updateLoanOfferInStatement() - start: LoanOfferDto = " + loanOfferDto);
        Statement statement = statementRepository.findStatementByStatementId(
                loanOfferDto.getStatementId()).orElseThrow();
        statement.setStatus(ApplicationStatus.PREPARE_DOCUMENTS);
        statement.setStatusHistory(
                StatusHistory.builder()
                        .status(ApplicationStatus.PREPARE_DOCUMENTS)
                        .time(new Date())
                        .changeType(ChangeType.AUTOMATIC)
                        .build());
        statement.setAppliedOffer(loanOfferDto);
        statementRepository.save(statement);
        log.info("updateLoanOfferInStatement() - end: void");
    }
}
