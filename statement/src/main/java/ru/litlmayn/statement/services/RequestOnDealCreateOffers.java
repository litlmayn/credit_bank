package ru.litlmayn.statement.services;

import ru.litlmayn.api.dto.LoanOfferDto;
import ru.litlmayn.api.dto.LoanStatementRequestDto;

import java.util.List;

public interface RequestOnDealCreateOffers {

    List<LoanOfferDto> requestOnDealCreateOffers(LoanStatementRequestDto loanStatementRequestDto);

}
