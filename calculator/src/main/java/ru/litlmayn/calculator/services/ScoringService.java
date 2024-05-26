package ru.litlmayn.calculator.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.litlmayn.calculator.dto.ScoringDataDto;
import ru.litlmayn.calculator.dto.enums.EmploymentStatus;
import ru.litlmayn.calculator.dto.enums.Gender;
import ru.litlmayn.calculator.dto.enums.MaritalStatus;
import ru.litlmayn.calculator.dto.enums.Position;
import ru.litlmayn.calculator.exceptions.RefusalCreditException;
import ru.litlmayn.calculator.utils.CalculateAge;

import java.math.BigDecimal;
import java.math.MathContext;

@Service
@PropertySource("classpath:calculator.properties")
public class ScoringService {

    @Value("${base.rate}")
    private double rate;

    public double totalScoring(ScoringDataDto scoringDataDto) throws RefusalCreditException {
        scoringAge(scoringDataDto);
        scoringPosition(scoringDataDto);
        scoringGender(scoringDataDto);
        scoringMaritalStatus(scoringDataDto);
        scoringTotalAmount(scoringDataDto);
        scoringWorkExperience(scoringDataDto);
        scoringEmploymentStatus(scoringDataDto);
        return rate;
    }

    private void scoringEmploymentStatus(ScoringDataDto scoringDataDto) throws RefusalCreditException {
        if(scoringDataDto.getEmployment().getEmploymentStatus() == EmploymentStatus.UNEMPLOYED) {
            throw new RefusalCreditException("Вам отказано в кредите. scoringEmploymentStatus");
        } 
        switch (scoringDataDto.getEmployment().getEmploymentStatus()) {
            case EmploymentStatus.BUSINESS_OWNER:
                rate -= EmploymentStatus.BUSINESS_OWNER.getChangeRate();
                break;
            case EmploymentStatus.SELF_EMPLOYED:
                rate -= EmploymentStatus.SELF_EMPLOYED.getChangeRate();
                break;
        }
    }


    private void scoringPosition(ScoringDataDto scoringDataDto) {
        switch (scoringDataDto.getEmployment().getPosition()) {
            case Position.TOP_MANAGER:
                rate -= Position.TOP_MANAGER.getChangeRate();
                break;
            case Position.MANAGER:
                rate -= Position.MANAGER.getChangeRate();
                break;
        }
    }

    private void scoringTotalAmount(ScoringDataDto scoringDataDto) throws RefusalCreditException {
        if (scoringDataDto.getAmount().divide(scoringDataDto.getEmployment().getSalary(), MathContext.DECIMAL32)
                .compareTo(BigDecimal.valueOf(25)) > 0) {
            throw new RefusalCreditException("Вам отказано в кредите. scoringTotalAmount");
        }
    }

    private void scoringMaritalStatus(ScoringDataDto scoringDataDto) {
        switch (scoringDataDto.getMaritalStatus()){
            case MaritalStatus.MARRIED:
                rate -= MaritalStatus.MARRIED.getChangeRate();
                break;
            case MaritalStatus.DIVORCED:
                rate += MaritalStatus.DIVORCED.getChangeRate();
                break;
        };
    }

    private void scoringAge(ScoringDataDto scoringDataDto) throws RefusalCreditException {
        int age = CalculateAge.calculateAge(scoringDataDto.getBirthdate());
        if (age < 20 || age > 65) {
            throw new RefusalCreditException("Вам отказано в кредите. scoringAge");
        }
    }

    private void scoringGender(ScoringDataDto scoringDataDto) {
        int age = CalculateAge.calculateAge(scoringDataDto.getBirthdate());
        if (scoringDataDto.getGender().equals(Gender.WOMAN) && age > 32 && age < 60) {
            rate -= 3;
        } else if (scoringDataDto.getGender().equals(Gender.MAN) && age > 30 && age < 55) {
            rate -= 3;
        } else if (scoringDataDto.getGender().equals(Gender.NON_BINARY)) {
            rate += 7;
        }
    }

    private void scoringWorkExperience(ScoringDataDto scoringDataDto) throws RefusalCreditException {
        if (scoringDataDto.getEmployment().getWorkExperienceTotal() < 18 ||
                scoringDataDto.getEmployment().getWorkExperienceCurrent() < 3) {
            throw new RefusalCreditException("Вам отказано в кредите. scoringWorkExperience");
        };
    }


}
