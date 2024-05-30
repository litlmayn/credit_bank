package ru.litlmayn.calculator.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.litlmayn.calculator.dto.EmploymentDto;
import ru.litlmayn.calculator.dto.ScoringDataDto;
import ru.litlmayn.calculator.dto.enums.EmploymentStatus;
import ru.litlmayn.calculator.dto.enums.Gender;
import ru.litlmayn.calculator.dto.enums.MaritalStatus;
import ru.litlmayn.calculator.dto.enums.Position;
import ru.litlmayn.api.exceptions.RefusalCreditException;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class ScoringServiceImplTest {

    @InjectMocks
    ScoringServiceImpl scoringService;

    @Test
    void totalScoring() throws RefusalCreditException {
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
                LocalDate.of(2000, 2, 2),
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
        assertEquals(scoringService.totalScoring(scoringDataDto), -3d);
    }

    @Test
    void scoringInsAndSal() {
        assertEquals(scoringService.scoringInsAndSal(false, false), 0);
    }
}