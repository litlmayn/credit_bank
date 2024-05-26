package ru.litlmayn.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
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

    public LoanOfferDto(Boolean isInsuranceEnabled, Boolean isSalaryClient) {
        this.isInsuranceEnabled = isInsuranceEnabled;
        this.isSalaryClient = isSalaryClient;
    }
}
