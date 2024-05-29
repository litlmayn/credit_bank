package ru.litlmayn.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@Schema(description = "Кредитное предложение")
public class LoanOfferDto {
    @Schema(description = "id заявки")
    private UUID statementId;
    @Schema(description = "Запрошенная сумма")
    private BigDecimal requestedAmount;
    @Schema(description = "Итоговая сумма")
    private BigDecimal totalAmount;
    @Schema(description = "Срок кредита")
    private Integer term;
    @Schema(description = "Ежемесячный платеж")
    private BigDecimal monthlyPayment;
    @Schema(description = "Процентная ставка")
    private BigDecimal rate;
    @Schema(description = "Включена ли страховка")
    private Boolean isInsuranceEnabled;
    @Schema(description = "Ялвяется ли заемщик зарплатным клиентом")
    private Boolean isSalaryClient;

}
