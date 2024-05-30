package ru.litlmayn.api.controllers;

import ru.litlmayn.api.dto.CreditDto;
import ru.litlmayn.api.dto.LoanOfferDto;
import ru.litlmayn.api.dto.LoanStatementRequestDto;
import ru.litlmayn.api.dto.ScoringDataDto;

import java.util.List;

public interface CalculatorController {

    List<LoanOfferDto> offers(LoanStatementRequestDto loanStatementRequestDto);

    CreditDto calc(ScoringDataDto scoringDataDto);
}
