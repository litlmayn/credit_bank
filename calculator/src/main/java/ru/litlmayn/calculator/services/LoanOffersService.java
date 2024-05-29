package ru.litlmayn.calculator.services;

import ru.litlmayn.calculator.dto.LoanOfferDto;
import ru.litlmayn.calculator.dto.LoanStatementRequestDto;

import java.util.List;

public interface LoanOffersService {
    List<LoanOfferDto> createListOffers(LoanStatementRequestDto loanStatementRequestDto);
}