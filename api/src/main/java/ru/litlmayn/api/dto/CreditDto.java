package ru.litlmayn.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "Полностью расчитанное кредитное предложения")
public class CreditDto {

    @Schema(description = "Запрощенная сумма")
    private BigDecimal amount;

    @Schema(description = "Срок кредита")
    private Integer term;

    @Schema(description = "Сумма ежемесячного платежа")
    private BigDecimal monthlyPayment;

    @Schema(description = "Итоговая ставка")
    private BigDecimal rate;

    @Schema(description = "Полная стоимость кредита")
    private BigDecimal psk;

    @Schema(description = "Куплена ли страховка")
    private Boolean isInsuranceEnabled;

    @Schema(description = "Является ли зарплатным клиентом")
    private Boolean isSalaryClient;

    @Schema(description = "График ежемесячных платежей")
    private List<PaymentScheduleElementDto> paymentSchedule;
}
