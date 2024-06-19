package ru.litlmayn.api.controllers;

import ru.litlmayn.api.dto.LoanOfferDto;
import ru.litlmayn.api.dto.LoanStatementRequestDto;

import java.util.List;

public interface StatementController {

    List<LoanOfferDto> createOffers(LoanStatementRequestDto loanStatementRequestDto);

    void choiceOffer(LoanOfferDto loanOfferDto);

}
