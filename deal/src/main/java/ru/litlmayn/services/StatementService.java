package ru.litlmayn.services;

import ru.litlmayn.api.dto.LoanOfferDto;
import ru.litlmayn.api.dto.LoanStatementRequestDto;

import java.util.List;

public interface StatementService {
    List<LoanOfferDto> createAnswerStatement(LoanStatementRequestDto loanStatementRequestDto);
}
