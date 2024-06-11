package ru.litlmayn.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.litlmayn.api.dto.enums.EmploymentStatus;
import ru.litlmayn.api.dto.enums.Position;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Данные о должности на работе")
public class EmploymentDto {

    @Schema(description = "Статус занятости", example = "EMPLOYED")
    private EmploymentStatus employmentStatus;

    @Schema(description = "ИНН работодателя", example = "7743013902")
    private String employerINN;

    @Schema(description = "Размер заработной платы", example = "20000")
    private BigDecimal salary;

    @Schema(description = "Позиция на работе", example = "WORKER")
    private Position position;

    @Schema(description = "Стаж работы полный", example = "30")
    private Integer workExperienceTotal;

    @Schema(description = "Стаж работы в текущей компании", example = "25")
    private Integer workExperienceCurrent;
}
