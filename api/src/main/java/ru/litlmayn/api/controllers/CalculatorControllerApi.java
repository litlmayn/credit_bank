package ru.litlmayn.api.controllers;

import ru.litlmayn.api.dto.CreditDto;
import ru.litlmayn.api.dto.LoanOfferDto;
import ru.litlmayn.api.dto.LoanStatementRequestDto;
import ru.litlmayn.api.dto.ScoringDataDto;
import ru.litlmayn.api.exceptions.RefusalCreditException;

import java.util.List;

public interface CalculatorControllerApi {

    List<LoanOfferDto> offers(LoanStatementRequestDto loanStatementRequestDto);

    CreditDto calc(ScoringDataDto scoringDataDto) throws RefusalCreditException;
}
