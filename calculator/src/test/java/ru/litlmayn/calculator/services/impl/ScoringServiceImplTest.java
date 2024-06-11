package ru.litlmayn.calculator.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.litlmayn.api.dto.EmploymentDto;
import ru.litlmayn.api.dto.ScoringDataDto;
import ru.litlmayn.api.dto.enums.EmploymentStatus;
import ru.litlmayn.api.dto.enums.Gender;
import ru.litlmayn.api.dto.enums.MaritalStatus;
import ru.litlmayn.api.dto.enums.Position;
import ru.litlmayn.api.exceptions.RefusalCreditException;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class ScoringServiceImplTest {

    @InjectMocks
    ScoringServiceImpl scoringService;

    @Test
    void totalScoringValidData() throws RefusalCreditException {
        ScoringDataDto scoringDataDto = new ScoringDataDto(
                new BigDecimal("100000"),
                18,
                "Ivan",
                "Ivanov",
                "Ivanovich",
                Gender.MAN,
                LocalDate.of(2000, 1, 1),
                "1234",
                "123456",
                LocalDate.of(2010, 2, 2),
                "Московский район",
                MaritalStatus.MARRIED,
                2,
                new EmploymentDto(
                        EmploymentStatus.EMPLOYED,
                        "7743013902",
                        new BigDecimal("20000"),
                        Position.WORKER,
                        30,
                        25
                ),
                "12345678901",
                false,
                false
        );
        assertEquals(scoringService.totalScoring(scoringDataDto), -3d);
    }

    @Test
    void totalScoringInvalidEmploymentStatus() {
        ScoringDataDto scoringDataDto = new ScoringDataDto(
                new BigDecimal("100000"),
                18,
                "Ivan",
                "Ivanov",
                "Ivanovich",
                Gender.MAN,
                LocalDate.of(2000, 1, 1),
                "1234",
                "123456",
                LocalDate.of(2010, 2, 2),
                "Московский район",
                MaritalStatus.MARRIED,
                2,
                new EmploymentDto(
                        EmploymentStatus.UNEMPLOYED,
                        "7743013902",
                        new BigDecimal("20000"),
                        Position.WORKER,
                        30,
                        25
                ),
                "12345678901",
                false,
                false
        );
        RefusalCreditException exception = assertThrows(RefusalCreditException.class,
                () -> {scoringService.totalScoring(scoringDataDto);},
                "Ожидаемое исключение - RefusalCreditException.");
        assertEquals(exception.getMessage(),
                "Вам отказано в кредите, так как вы не имеете места работы.");
    }

    @Test
    void totalScoringInvalidAmount() {
        ScoringDataDto scoringDataDto = new ScoringDataDto(
                new BigDecimal("100000"),
                18,
                "Ivan",
                "Ivanov",
                "Ivanovich",
                Gender.MAN,
                LocalDate.of(2000, 1, 1),
                "1234",
                "123456",
                LocalDate.of(2010, 2, 2),
                "Московский район",
                MaritalStatus.MARRIED,
                2,
                new EmploymentDto(
                        EmploymentStatus.WORKER,
                        "7743013902",
                        new BigDecimal("2000"),
                        Position.WORKER,
                        30,
                        25
                ),
                "12345678901",
                false,
                false
        );
        RefusalCreditException exception = assertThrows(RefusalCreditException.class,
                () -> {scoringService.totalScoring(scoringDataDto);},
                "Ожидаемое исключение - RefusalCreditException.");
        assertEquals(exception.getMessage(),
                "Вам отказано в кредите. Сумма кредита не должна превышать 25 зарплат.");
    }

    @Test
    void totalScoringInvalidAge() {
        ScoringDataDto scoringDataDto = new ScoringDataDto(
                new BigDecimal("100000"),
                18,
                "Ivan",
                "Ivanov",
                "Ivanovich",
                Gender.MAN,
                LocalDate.of(2020, 1, 1),
                "1234",
                "123456",
                LocalDate.of(2010, 2, 2),
                "Московский район",
                MaritalStatus.MARRIED,
                2,
                new EmploymentDto(
                        EmploymentStatus.WORKER,
                        "7743013902",
                        new BigDecimal("20000"),
                        Position.WORKER,
                        30,
                        25
                ),
                "12345678901",
                false,
                false
        );
        RefusalCreditException exception = assertThrows(RefusalCreditException.class,
                () -> {scoringService.totalScoring(scoringDataDto);},
                "Ожидаемое исключение - RefusalCreditException.");
        assertEquals(exception.getMessage(),
                "Вам отказано в кредите. Для одобрения кредита нуеобходимо достичь 20 летнего возраста.");
    }

    @Test
    void totalScoringInvalidWorkExperienceTotal() {
        ScoringDataDto scoringDataDto = new ScoringDataDto(
                new BigDecimal("100000"),
                18,
                "Ivan",
                "Ivanov",
                "Ivanovich",
                Gender.MAN,
                LocalDate.of(2000, 1, 1),
                "1234",
                "123456",
                LocalDate.of(2010, 2, 2),
                "Московский район",
                MaritalStatus.MARRIED,
                2,
                new EmploymentDto(
                        EmploymentStatus.WORKER,
                        "7743013902",
                        new BigDecimal("20000"),
                        Position.WORKER,
                        10,
                        25
                ),
                "12345678901",
                false,
                false
        );
        RefusalCreditException exception = assertThrows(RefusalCreditException.class,
                () -> {scoringService.totalScoring(scoringDataDto);},
                "Ожидаемое исключение - RefusalCreditException.");
        assertEquals(exception.getMessage(),
                "Вам отказано в кредите. Ваш общий стаж работы должен привышать 18 месяцес.");
    }

    @Test
    void totalScoringInvalidWorkExperienceCurrent() {
        ScoringDataDto scoringDataDto = new ScoringDataDto(
                new BigDecimal("100000"),
                18,
                "Ivan",
                "Ivanov",
                "Ivanovich",
                Gender.MAN,
                LocalDate.of(2000, 1, 1),
                "1234",
                "123456",
                LocalDate.of(2010, 2, 2),
                "Московский район",
                MaritalStatus.MARRIED,
                2,
                new EmploymentDto(
                        EmploymentStatus.WORKER,
                        "7743013902",
                        new BigDecimal("20000"),
                        Position.WORKER,
                        30,
                        2
                ),
                "12345678901",
                false,
                false
        );
        RefusalCreditException exception = assertThrows(RefusalCreditException.class,
                () -> {scoringService.totalScoring(scoringDataDto);},
                "Ожидаемое исключение - RefusalCreditException.");
        assertEquals(
                exception.getMessage(),
                "Вам отказано в кредите. Ваш общий стаж работы на данном месте работы должен привышать 3 месяцеса."
        );
    }

    @Test
    void scoringInsAndSalFalseFalse() {
        assertEquals(scoringService.scoringInsAndSal(false, false), 0);
    }

    @Test
    void scoringInsAndSalTrueFalse() {
        assertEquals(scoringService.scoringInsAndSal(true, false), -3.0);
    }

    @Test
    void scoringInsAndSalFalseTrue() {
        assertEquals(scoringService.scoringInsAndSal(false, true), -1.0);
    }

    @Test
    void scoringInsAndSalTrueTrue() {
        assertEquals(scoringService.scoringInsAndSal(true, true), -4.0);
    }
}