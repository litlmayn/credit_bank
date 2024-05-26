package ru.litlmayn.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.litlmayn.calculator.utils.MinimumDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Schema(description = "Данные для расчета кредитного предложения")
public class LoanStatementRequestDto {
    @NonNull
    @Min(value = 30000, message = "Минимальная сумма кредита 30 000.")
    @Schema(description = "Сумма кредита")
    private BigDecimal amount;

    @NonNull
    @Min(value = 6, message = "Минимальный срок на который выдается кредит - 6 месяцев.")
    @Schema(description = "Срок кредита")
    private Integer term;

    @NonNull
    @Size(min =2, max = 30, message = "Размер имени не соответствует ожидаемому")
    @Schema(description = "Имя")
    private String firstName;

    @NonNull
    @Size(min =2, max = 30, message = "Размер фамилии не соответствует ожидаемому")
    @Schema(description = "Фамилия")
    private String lastName;

    @NonNull
    @Size(min =2, max = 30, message = "Размер отчества не соответствует ожидаемому")
    @Schema(description = "Отчество")
    private String middleName;

    @NonNull
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Невалидный email")
    @Schema(description = "Email")
    private String email;

    @NonNull
    @MinimumDate(message = "Вы должны быть старще 18 лет.")
    @Schema(description = "Дата рождения")
    private LocalDate birthdate;

    @NonNull
    @Size(min = 4, max = 4, message = "Серия паспорта состоит из 4 цифр.")
    @Schema(description = "Серия паспорта")
    private String passportSeries;

    @NonNull
    @Size(min = 6, max = 6, message = "Номер паспорта состоит из 6 цифр.")
    @Schema(description = "Номер паспорта")
    private String passportNumber;
}
