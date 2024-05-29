package ru.litlmayn.calculator.services;

import ru.litlmayn.calculator.dto.CreditDto;
import ru.litlmayn.calculator.dto.ScoringDataDto;
import ru.litlmayn.calculator.exceptions.RefusalCreditException;

public interface CreateCreditService {
        CreditDto createCreditDto(ScoringDataDto scoringDataDto) throws RefusalCreditException;
}