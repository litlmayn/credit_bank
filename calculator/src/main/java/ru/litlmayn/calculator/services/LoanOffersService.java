package ru.litlmayn.calculator.services;

import ru.litlmayn.api.dto.LoanOfferDto;
import ru.litlmayn.api.dto.LoanStatementRequestDto;

import java.util.List;

public interface LoanOffersService {
    List<LoanOfferDto> createListOffers(LoanStatementRequestDto loanStatementRequestDto);
}