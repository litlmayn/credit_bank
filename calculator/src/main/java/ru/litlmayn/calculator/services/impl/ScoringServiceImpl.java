package ru.litlmayn.calculator.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import ru.litlmayn.calculator.dto.ScoringDataDto;
import ru.litlmayn.calculator.dto.enums.EmploymentStatus;
import ru.litlmayn.calculator.dto.enums.Gender;
import ru.litlmayn.calculator.dto.enums.MaritalStatus;
import ru.litlmayn.calculator.dto.enums.Position;
import ru.litlmayn.calculator.exceptions.RefusalCreditException;
import ru.litlmayn.calculator.services.ScoringService;
import ru.litlmayn.calculator.utils.CalculateAge;

import java.math.BigDecimal;
import java.math.MathContext;

@Slf4j
@Service
@Scope(value = "prototype", proxyMode= ScopedProxyMode.TARGET_CLASS)
@PropertySource("classpath:calculator.properties")
public class ScoringServiceImpl implements ScoringService {

    @Value("${base.rate}")
    private double rate;

    public double totalScoring(ScoringDataDto scoringDataDto) throws RefusalCreditException {
        log.info("Вызов метода 'totalScoring' сервиса 'ScoringService'");
        log.info("На вход метод получил класс 'ScoringDataDto': " + scoringDataDto);
        scoringInsAndSal(scoringDataDto.getIsInsuranceEnabled(), scoringDataDto.getIsSalaryClient());
        scoringAge(scoringDataDto);
        scoringPosition(scoringDataDto);
        scoringGender(scoringDataDto);
        scoringMaritalStatus(scoringDataDto);
        scoringTotalAmount(scoringDataDto);
        scoringWorkExperience(scoringDataDto);
        scoringEmploymentStatus(scoringDataDto);
        log.info("Метод вернул поле 'rate' со значением: " + rate);
        return rate;
    }

    public double scoringInsAndSal(Boolean isInsuranceEnabled, Boolean isSalaryClient) {
        log.info("Вызван метод 'scoringInsAndSal' класса 'ScoringService'");
        log.info("На вход метод поля isInsuranceEnabled которое равно "
                + isInsuranceEnabled + "поле isSalaryClient равное " + isSalaryClient);
        if (isInsuranceEnabled) {
            rate -= 3;
        }
        if (isSalaryClient){
            rate -= 1;
        }
        log.info("Метод вернул поле 'rate' со значением: " + rate);
        return rate;
    }

    private void scoringEmploymentStatus(ScoringDataDto scoringDataDto) throws RefusalCreditException {
        log.info("Вызван метод 'scoringEmploymentStatus' класса 'ScoringService'");
        log.info("На вход метод получил класс 'ScoringDataDto': " + scoringDataDto);
        if(scoringDataDto.getEmployment().getEmploymentStatus() == EmploymentStatus.UNEMPLOYED) {
            log.info(
                    "Метод scoringEmploymentStatus вернул"
                            + new RefusalCreditException("Вам отказано в кредите.").fillInStackTrace()
            );
            throw new RefusalCreditException("Вам отказано в кредите.");
        } 
        switch (scoringDataDto.getEmployment().getEmploymentStatus()) {
            case EmploymentStatus.BUSINESS_OWNER:
                rate -= EmploymentStatus.BUSINESS_OWNER.getChangeRate();
                break;
            case EmploymentStatus.SELF_EMPLOYED:
                rate -= EmploymentStatus.SELF_EMPLOYED.getChangeRate();
                break;
        }
        log.info("Метод 'scoringEmploymentStatus' класса 'ScoringService' закончил выполнения логики.");
    }


    private void scoringPosition(ScoringDataDto scoringDataDto) {
        log.info("Вызван метод 'scoringPosition' класса 'ScoringService'");
        log.info("На вход метод получил класс 'ScoringDataDto': " + scoringDataDto);
        switch (scoringDataDto.getEmployment().getPosition()) {
            case Position.TOP_MANAGER:
                rate -= Position.TOP_MANAGER.getChangeRate();
                break;
            case Position.MANAGER:
                rate -= Position.MANAGER.getChangeRate();
                break;
        }
        log.info("Метод 'scoringPosition' класса 'ScoringService' закончил выполнения логики.");
    }

    private void scoringTotalAmount(ScoringDataDto scoringDataDto) throws RefusalCreditException {
        // проверка итоговой суммы займа в соотношении к зарплате заемщика. сумма займа меньше 25 зарплат
        log.info("Вызван метод 'scoringTotalAmount' класса 'ScoringService'");
        log.info("На вход метод получил класс 'ScoringDataDto': " + scoringDataDto);
        if (scoringDataDto.getAmount().divide(scoringDataDto.getEmployment().getSalary(), MathContext.DECIMAL32)
                .compareTo(BigDecimal.valueOf(25)) > 0) {
            log.info(
                    "Метод scoringTotalAmount вернул"
                            + new RefusalCreditException("Вам отказано в кредите.").fillInStackTrace()
            );
            throw new RefusalCreditException("Вам отказано в кредите. scoringTotalAmount");
        }
        log.info("Метод 'scoringTotalAmount' класса 'ScoringService' закончил выполнения логики.");
    }

    private void scoringMaritalStatus(ScoringDataDto scoringDataDto) {
        // проверка семейного положения
        log.info("Вызван метод 'scoringMaritalStatus' класса 'ScoringService'");
        log.info("На вход метод получил класс 'ScoringDataDto': " + scoringDataDto);
        switch (scoringDataDto.getMaritalStatus()){
            case MaritalStatus.MARRIED:
                rate -= MaritalStatus.MARRIED.getChangeRate();
                break;
            case MaritalStatus.DIVORCED:
                rate += MaritalStatus.DIVORCED.getChangeRate();
                break;
        };
        log.info("Метод 'scoringMaritalStatus' класса 'ScoringService' закончил выполнения логики.");
    }

    private void scoringAge(ScoringDataDto scoringDataDto) throws RefusalCreditException {
        // проверка возраста
        log.info("Вызван метод 'scoringAge' класса 'ScoringService'");
        log.info("На вход метод получил класс 'ScoringDataDto'= " + scoringDataDto);
        int age = CalculateAge.calculateAge(scoringDataDto.getBirthdate());
        if (age < 20 || age > 65) {
            throw new RefusalCreditException("Вам отказано в кредите. scoringAge");
        }
        log.info("Метод 'scoringAge' класса 'ScoringService' закончил выполнения логики.");
    }

    private void scoringGender(ScoringDataDto scoringDataDto) {
        // проверка пола
        log.info("Вызван метод 'scoringGender' класса 'ScoringService'");
        log.info("На вход метод получил класс 'ScoringDataDto'=" + scoringDataDto);
        int age = CalculateAge.calculateAge(scoringDataDto.getBirthdate());
        if (scoringDataDto.getGender().equals(Gender.WOMAN) && age > 32 && age < 60) {
            rate -= 3;
        } else if (scoringDataDto.getGender().equals(Gender.MAN) && age > 30 && age < 55) {
            rate -= 3;
        } else if (scoringDataDto.getGender().equals(Gender.NON_BINARY)) {
            rate += 7;
        }
        log.info("Метод 'scoringGender' класса 'ScoringService' закончил выполнения логики.");
    }

    private void scoringWorkExperience(ScoringDataDto scoringDataDto) throws RefusalCreditException {
        // проверка стажа работы
        log.info("Вызван метод 'scoringWorkExperience' класса 'ScoringService'");
        log.info("На вход метод получил класс 'ScoringDataDto'=" + scoringDataDto);
        if (scoringDataDto.getEmployment().getWorkExperienceTotal() < 18 ||
                scoringDataDto.getEmployment().getWorkExperienceCurrent() < 3) {
            log.info(
                    "Метод scoringWorkExperience вернул"
                            + new RefusalCreditException("Вам отказано в кредите.").fillInStackTrace()
            );
            throw new RefusalCreditException("Вам отказано в кредите. scoringWorkExperience");
        };
        log.info("Метод 'scoringWorkExperience' класса 'ScoringService' закончил выполнения логики.");
    }
}
