package ru.litlmayn.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Schema(description = "Расписание платежа")
public class PaymentScheduleElementDto {
    @Schema(description = "Номер платежа")
    private Integer number;
    @Schema(description = "Дата платежа")
    private LocalDate date;
    @Schema(description = "Всего к оплате")
    private BigDecimal totalPayment;
    @Schema(description = "Выплата процентов")
    private BigDecimal interestPayment;
    @Schema(description = "Выплата долга")
    private BigDecimal debtPayment;
    @Schema(description = "Оставшийся долг")
    private BigDecimal remainingDebt;

    public PaymentScheduleElementDto(
            Integer number, LocalDate date,
            BigDecimal totalPayment, BigDecimal interestPayment,
            BigDecimal debtPayment, BigDecimal remainingDebt) {
        this.number = number;
        this.date = date;
        this.totalPayment = totalPayment;
        this.interestPayment = interestPayment;
        this.debtPayment = debtPayment;
        this.remainingDebt = remainingDebt;
    }
}
