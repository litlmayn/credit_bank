package ru.litlmayn.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Кредитное предложение")
public class LoanOfferDto {

    @Schema(description = "id заявки")
    private UUID statementId;

    @Schema(description = "Запрошенная сумма", example = "100000")
    private BigDecimal requestedAmount;

    @Schema(description = "Итоговая сумма", example = "120945.27")
    private BigDecimal totalAmount;

    @Schema(description = "Срок кредита", example = "18")
    private Integer term;

    @Schema(description = "Ежемесячный платеж", example = "6719.18")
    private BigDecimal monthlyPayment;

    @Schema(description = "Процентная ставка", example = "25")
    private BigDecimal rate;

    @Schema(description = "Включена ли страховка", example = "false")
    private Boolean isInsuranceEnabled;

    @Schema(description = "Ялвяется ли заемщик зарплатным клиентом", example = "false")
    private Boolean isSalaryClient;
}
