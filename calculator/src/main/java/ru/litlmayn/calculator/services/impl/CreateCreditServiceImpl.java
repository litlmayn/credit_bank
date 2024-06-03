package ru.litlmayn.calculator.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.litlmayn.api.dto.CreditDto;
import ru.litlmayn.api.dto.ScoringDataDto;
import ru.litlmayn.calculator.exceptions.RefusalCreditException;
import ru.litlmayn.calculator.services.CreateCreditService;
import ru.litlmayn.calculator.services.CreditDataService;
import ru.litlmayn.calculator.services.PaymentElementsService;
import ru.litlmayn.calculator.services.ScoringService;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
@AllArgsConstructor
public class CreateCreditServiceImpl implements CreateCreditService {
    private final CreditDataService creditDataService;
    private final PaymentElementsService paymentElementsService;
    private final ScoringService scoringService;


    public CreditDto createCreditDto(ScoringDataDto scoringDataDto) throws RefusalCreditException {
        // создане окончательного кредитного предложения
        log.info("createCreditDto() - start: ScoringDataDto = " + scoringDataDto);
        BigDecimal monthlyPayment = creditDataService.calculateFullMonthlyPayment(
                BigDecimal.valueOf(scoringService.totalScoring(scoringDataDto)),
                scoringDataDto.getAmount(),
                scoringDataDto.getTerm(),
                scoringDataDto.getIsInsuranceEnabled()
        );
        CreditDto creditDto = CreditDto.builder()
                .amount(scoringDataDto.getAmount())
                .term(scoringDataDto.getTerm())
                .monthlyPayment(monthlyPayment.setScale(2, RoundingMode.HALF_UP))
                .rate(BigDecimal.valueOf(scoringService.totalScoring(scoringDataDto)))
                .isInsuranceEnabled(scoringDataDto.getIsInsuranceEnabled())
                .isSalaryClient(scoringDataDto.getIsSalaryClient())
                .psk(creditDataService.calculateTotalAmount(monthlyPayment, scoringDataDto.getTerm())
                        .setScale(2, RoundingMode.HALF_UP))
                .build();
        creditDto.setPaymentSchedule(paymentElementsService.calculatePaymentScheduleElements(creditDto));
        log.info("createCreditDto() - end: CreditDto = " + creditDto);
        return creditDto;
    }
}
