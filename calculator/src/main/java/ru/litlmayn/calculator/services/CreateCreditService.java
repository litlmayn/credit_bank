package ru.litlmayn.calculator.services;

import ru.litlmayn.api.dto.CreditDto;
import ru.litlmayn.api.dto.ScoringDataDto;
import ru.litlmayn.api.exceptions.RefusalCreditException;

public interface CreateCreditService {
        CreditDto createCreditDto(ScoringDataDto scoringDataDto) throws RefusalCreditException;
}