package ru.litlmayn.calculator.services;

import ru.litlmayn.calculator.dto.ScoringDataDto;
import ru.litlmayn.calculator.exceptions.RefusalCreditException;

public interface ScoringService {
    double totalScoring(ScoringDataDto scoringDataDto) throws RefusalCreditException;
    double scoringInsAndSal(Boolean isInsuranceEnabled, Boolean isSalaryClient);
}