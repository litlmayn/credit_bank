package ru.litlmayn.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.litlmayn.api.dto.enums.Gender;
import ru.litlmayn.api.dto.enums.MaritalStatus;

import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@Builder
@Schema(description = "Расширенные данные для получения кредита")
public class FinishRegistrationRequestDto {

    @Schema(description = "Пол", example = "MAN")
    private Gender gender;

    @Schema(description = "Семейное положение", example = "MARRIED")
    private MaritalStatus maritalStatus;

    @Schema(description = "Количество иждевенцев", example = "2")
    private Integer dependentAmount;

    @Past(message = "Дата выпуска паспорта не может быть позже чем сегодняшний день")
    @Schema(description = "Дата выпуска паспорта", example = "2020-02-22")
    private LocalDate passportIssueDate;

    @Schema(description = "Отдел выпуска паспорта", example = "Московский район")
    private String passportIssueBranch;

    @Schema(description = "Должность на работе")
    private EmploymentDto employment;

    @Schema(description = "Номер счета", example = "12345678901")
    private String accountNumber;
}
