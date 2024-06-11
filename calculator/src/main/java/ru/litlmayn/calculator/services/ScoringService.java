package ru.litlmayn.calculator.services;

import ru.litlmayn.api.dto.ScoringDataDto;
import ru.litlmayn.api.exceptions.RefusalCreditException;

public interface ScoringService {
    Double totalScoring(ScoringDataDto scoringDataDto) throws RefusalCreditException;
    Double scoringInsAndSal(Boolean isInsuranceEnabled, Boolean isSalaryClient);
}