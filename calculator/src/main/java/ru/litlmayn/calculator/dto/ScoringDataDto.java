package ru.litlmayn.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.litlmayn.calculator.dto.enums.Gender;
import ru.litlmayn.calculator.dto.enums.MaritalStatus;
import ru.litlmayn.calculator.utils.MinimumDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Schema(description = "Полные данные о заемщике")
public class ScoringDataDto {

    @NonNull
    @Min(value = 30000, message = "Минимальная сумма кредита 30 000.")
    @Schema(description = "Сумма кредита", example = "100000")
    private BigDecimal amount;

    @NonNull
    @Min(value = 6, message = "Минимальный срок на который выдается кредит - 6 месяцев.")
    @Schema(description = "Срок кредита", example = "18")
    private Integer term;

    @NonNull
    @Size(min =2, max = 30, message = "Размер имени не соответствует ожидаемому")
    @Schema(description = "Имя", example = "Иван")
    private String firstName;

    @NonNull
    @Size(min =2, max = 30, message = "Размер фамилии не соответствует ожидаемому")
    @Schema(description = "Фамиля", example = "Иванов")
    private String lastName;

    @Size(min =2, max = 30, message = "Размер отчества не соответствует ожидаемому")
    @Schema(description = "Отчество", example = "Иванович")
    private String middleName;

    @Schema(description = "Пол", example = "MAN")
    private Gender gender;

    @NonNull
    @MinimumDate(message = "Вы должны быть старще 18 лет.")
    @Schema(description = "День рождения", example = "2000-01-01")
    private LocalDate birthdate;

    @NonNull
    @Size(min = 4, max = 4, message = "Серия паспорта состоит из 4 цифр.")
    @Schema(description = "Серия паспорта", example = "1234")
    private String passportSeries;

    @NonNull
    @Size(min = 6, max = 6, message = "Номер паспорта состоит из 6 цифр.")
    @Schema(description = "Номер паспорта", example = "123456")
    private String passportNumber;

    @Past(message = "Дата выпуска паспорта не может быть позже чем сегодняшний день")
    @Schema(description = "Дата выпуска паспорта", example = "2020-02-22")
    private LocalDate passportIssueDate;

    @Schema(description = "Отедл выпуска паспорта", example = "Московский район")
    private String passportIssueBranch;

    @Schema(description = "Семейное положение", example = "MARRIED")
    private MaritalStatus maritalStatus;

    @Schema(description = "Количество иждевенцев", example = "2")
    private Integer dependentAmount;

    @Schema(description = "Должность на работе")
    private EmploymentDto employment;

    @Schema(description = "Номер счета", example = "12345678901")
    private String accountNumber;

    @Schema(description = "Куплена ли страховка", example = "false")
    private Boolean isInsuranceEnabled;

    @Schema(description = "Является ли зарплатным клиентом", example = "false")
    private Boolean isSalaryClient;
}
