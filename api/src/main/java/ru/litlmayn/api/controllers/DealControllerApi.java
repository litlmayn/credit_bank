package ru.litlmayn.api.controllers;

import ru.litlmayn.api.dto.FinishRegistrationRequestDto;
import ru.litlmayn.api.dto.LoanOfferDto;
import ru.litlmayn.api.dto.LoanStatementRequestDto;

import java.util.List;
import java.util.UUID;

public interface DealControllerApi {

    List<LoanOfferDto> statement(LoanStatementRequestDto loanStatementRequestDto);

    void offerSelect(LoanOfferDto loanOfferDto);

    void createCredit(FinishRegistrationRequestDto finishRegistrationRequestDto, String statementIdString);

}
