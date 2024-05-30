package ru.litlmayn.calculator.services;

import ru.litlmayn.api.dto.ScoringDataDto;
import ru.litlmayn.api.exceptions.RefusalCreditException;

public interface ScoringService {
    double totalScoring(ScoringDataDto scoringDataDto) throws RefusalCreditException;
    double scoringInsAndSal(Boolean isInsuranceEnabled, Boolean isSalaryClient);
}