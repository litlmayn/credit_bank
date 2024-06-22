package ru.litlmayn.deal.services;

import ru.litlmayn.api.dto.FinishRegistrationRequestDto;

public interface CalculateStatementId {
    void calculateCredit(FinishRegistrationRequestDto finishRegistrationRequestDto, String statementIdString);
}
