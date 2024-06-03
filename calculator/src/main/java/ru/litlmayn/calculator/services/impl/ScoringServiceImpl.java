package ru.litlmayn.calculator.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import ru.litlmayn.api.dto.ScoringDataDto;
import ru.litlmayn.api.dto.enums.EmploymentStatus;
import ru.litlmayn.api.dto.enums.Gender;
import ru.litlmayn.api.dto.enums.MaritalStatus;
import ru.litlmayn.api.dto.enums.Position;
import ru.litlmayn.calculator.exceptions.RefusalCreditException;
import ru.litlmayn.calculator.services.ScoringService;
import ru.litlmayn.api.utils.CalculateAge;

import java.math.BigDecimal;
import java.math.MathContext;

@Slf4j
@Service
@Scope(value = "prototype", proxyMode= ScopedProxyMode.TARGET_CLASS)
@PropertySource("classpath:calculator.properties")
public class ScoringServiceImpl implements ScoringService {

    @Value("${base.rate}")
    private Double rate = 0d;
    @Value("${scoring.message}")
    private String scoringMessage;

    public Double totalScoring(ScoringDataDto scoringDataDto) throws RefusalCreditException {
        log.info("totalScoring() - start: ScoringDataDto = " + scoringDataDto);
        scoringInsAndSal(scoringDataDto.getIsInsuranceEnabled(), scoringDataDto.getIsSalaryClient());
        scoringAge(scoringDataDto);
        scoringPosition(scoringDataDto);
        scoringGender(scoringDataDto);
        scoringMaritalStatus(scoringDataDto);
        scoringTotalAmount(scoringDataDto);
        scoringWorkExperience(scoringDataDto);
        scoringEmploymentStatus(scoringDataDto);
        log.info("totalScoring() - end: rate = " + rate);
        return rate;
    }

    public Double scoringInsAndSal(Boolean isInsuranceEnabled, Boolean isSalaryClient) {
        log.info("scoringInsAndSal() - start: isInsuranceEnabled = " + isInsuranceEnabled
                + ", isSalaryClient = " + isSalaryClient);
        if (isInsuranceEnabled) {
            rate -= 3;
        }
        if (isSalaryClient){
            rate -= 1;
        }
        log.info("scoringInsAndSal() - end: rate = " + rate);
        return rate;
    }

    private void scoringEmploymentStatus(ScoringDataDto scoringDataDto) throws RefusalCreditException {
        log.info("scoringEmploymentStatus() - start: ScoringDataDto = " + scoringDataDto);
        if(scoringDataDto.getEmployment().getEmploymentStatus() == EmploymentStatus.UNEMPLOYED) {
            log.info(
                    "scoringEmploymentStatus() - end: RefusalCreditException = "
                            + new RefusalCreditException(
                                    "Вам отказано в кредите, так как вы не имеете места работы."
                    ).fillInStackTrace()
            );
            throw new RefusalCreditException("Вам отказано в кредите, так как вы не имеете места работы.");
        } 
        switch (scoringDataDto.getEmployment().getEmploymentStatus()) {
            case EmploymentStatus.BUSINESS_OWNER:
                rate -= EmploymentStatus.BUSINESS_OWNER.getChangeRate();
                break;
            case EmploymentStatus.SELF_EMPLOYED:
                rate -= EmploymentStatus.SELF_EMPLOYED.getChangeRate();
                break;
        }
        log.info("scoringEmploymentStatus() - end: void");
    }


    private void scoringPosition(ScoringDataDto scoringDataDto) {
        log.info("scoringPosition() - start: ScoringDataDto = " + scoringDataDto);
        switch (scoringDataDto.getEmployment().getPosition()) {
            case Position.TOP_MANAGER:
                rate -= Position.TOP_MANAGER.getChangeRate();
                break;
            case Position.MANAGER:
                rate -= Position.MANAGER.getChangeRate();
                break;
        }
        log.info("scoringPosition() - end: void");
    }

    private void scoringTotalAmount(ScoringDataDto scoringDataDto) throws RefusalCreditException {
        // проверка итоговой суммы займа в соотношении к зарплате заемщика. сумма займа меньше 25 зарплат
        log.info("scoringTotalAmount() - start: ScoringDataDto = " + scoringDataDto);
        if (scoringDataDto.getAmount().divide(scoringDataDto.getEmployment().getSalary(), MathContext.DECIMAL32)
                .compareTo(BigDecimal.valueOf(25)) > 0) {
            log.info(
                    "scoringTotalAmount() - end: RefusalCreditException = "
                            + new RefusalCreditException(
                                    "Вам отказано в кредите. Сумма кредита не должна превышать 25 зарплат.")
                            .fillInStackTrace()
            );
            throw new RefusalCreditException("Вам отказано в кредите. Сумма кредита не должна превышать 25 зарплат.");
        }
        log.info("scoringTotalAmount() - end: void");
    }

    private void scoringMaritalStatus(ScoringDataDto scoringDataDto) {
        // проверка семейного положения
        log.info("scoringMaritalStatus() - start: ScoringDataDto = " + scoringDataDto);
        switch (scoringDataDto.getMaritalStatus()){
            case MaritalStatus.MARRIED:
                rate -= MaritalStatus.MARRIED.getChangeRate();
                break;
            case MaritalStatus.DIVORCED:
                rate += MaritalStatus.DIVORCED.getChangeRate();
                break;
        };
        log.info("scoringMaritalStatus() - end: void");
    }

    private void scoringAge(ScoringDataDto scoringDataDto) throws RefusalCreditException {
        // проверка возраста
        log.info("scoringAge() - start: ScoringDataDto = " + scoringDataDto);
        int age = CalculateAge.calculateAge(scoringDataDto.getBirthdate());
        if (age < 20 || age > 65) {
            log.info("scoringAge() - end: RefusalCreditException = "
                + new RefusalCreditException(
                        "Вам отказано в кредите. Для одобрения кредита нуеобходимо достичь 20 летнего возраста."
            ).fillInStackTrace());
            throw new RefusalCreditException(
                    "Вам отказано в кредите. Для одобрения кредита нуеобходимо достичь 20 летнего возраста."
            );
        }
        log.info("scoringAge() - end: void");
    }

    private void scoringGender(ScoringDataDto scoringDataDto) {
        // проверка пола
        log.info("scoringGender() - start: ScoringDataDto = " + scoringDataDto);
        int age = CalculateAge.calculateAge(scoringDataDto.getBirthdate());
        if (scoringDataDto.getGender().equals(Gender.WOMAN) && age > 32 && age < 60) {
            rate -= 3;
        } else if (scoringDataDto.getGender().equals(Gender.MAN) && age > 30 && age < 55) {
            rate -= 3;
        } else if (scoringDataDto.getGender().equals(Gender.NON_BINARY)) {
            rate += 7;
        }
        log.info("scoringGender() - end: void");
    }

    private void scoringWorkExperience(ScoringDataDto scoringDataDto) throws RefusalCreditException {
        // проверка стажа работы
        log.info("scoringWorkExperience() - start: ScoringDataDto = " + scoringDataDto);
        if (scoringDataDto.getEmployment().getWorkExperienceTotal() < 18) {
            log.info("scoringWorkExperience() - end: RefusalCreditException = "
                            + new RefusalCreditException(
                                    "Вам отказано в кредите. Ваш общий стаж работы должен привышать 18 месяцес."
            ).fillInStackTrace());
            throw new RefusalCreditException(
                    "Вам отказано в кредите. Ваш общий стаж работы должен привышать 18 месяцес."
            );
        } else if (scoringDataDto.getEmployment().getWorkExperienceCurrent() < 3) {
            log.info("scoringWorkExperience() - end: RefusalCreditException = "
                    + new RefusalCreditException(
                    "Вам отказано в кредите. Ваш общий стаж работы на данном месте работы должен привышать 3 месяцеса."
            ).fillInStackTrace());
            throw new RefusalCreditException(
                    "Вам отказано в кредите. Ваш общий стаж работы на данном месте работы должен привышать 3 месяцеса."
            );
        }
        log.info("scoringWorkExperience() - end: void");
    }
}
