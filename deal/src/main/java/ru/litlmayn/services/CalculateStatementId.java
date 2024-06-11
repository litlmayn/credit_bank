package ru.litlmayn.services;

import ru.litlmayn.api.dto.FinishRegistrationRequestDto;

public interface CalculateStatementId {
    void calculateCredit(FinishRegistrationRequestDto finishRegistrationRequestDto, String statementIdString);
}
