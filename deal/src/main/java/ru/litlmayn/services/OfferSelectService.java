package ru.litlmayn.services;

import ru.litlmayn.api.dto.LoanOfferDto;

public interface OfferSelectService {
    void updateLoanOfferInStatement(LoanOfferDto loanOfferDto);
}
