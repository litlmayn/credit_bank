package ru.litlmayn.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import ru.litlmayn.calculator.dto.enums.EmploymentStatus;
import ru.litlmayn.calculator.dto.enums.Position;

import java.math.BigDecimal;

@Getter
@Setter
@Schema(description = "Данные о должности на работе")
public class EmploymentDto {
    @Schema(description = "Статус занятости", example = "WORKER")
    private EmploymentStatus employmentStatus;
    @Schema(description = "ИНН работодателя")
    private String employerINN;
    @Schema(description = "Размер заработной платы")
    private BigDecimal salary;
    @Schema(description = "Позиция на работе", example = "WORKER")
    private Position position;
    @Schema(description = "Стаж работы полный")
    private Integer workExperienceTotal;
    @Schema(description = "Стаж работы в текущей компании")
    private Integer workExperienceCurrent;
}
