package ru.litlmayn.calculator.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.litlmayn.calculator.dto.CreditDto;
import ru.litlmayn.calculator.dto.ScoringDataDto;
import ru.litlmayn.calculator.exceptions.RefusalCreditException;
import ru.litlmayn.calculator.services.CreateCreditService;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
@AllArgsConstructor
public class CreateCreditServiceImpl implements CreateCreditService {
    private final CreditDataServiceImpl creditDataServiceImpl;
    private final PaymentElementsServiceImpl paymentElementsServiceImpl;
    private final ScoringServiceImpl scoringServiceImpl;


    public CreditDto createCreditDto(ScoringDataDto scoringDataDto) throws RefusalCreditException {
        // создане окончательного кредитного предложения
        log.info("Старт метода 'createCreditDto' класса 'CreateCreditService'");
        log.info("Входные данные метода модель 'ScoringDataDto' " + scoringDataDto);
        BigDecimal monthlyPayment = creditDataServiceImpl.calculateFullMonthlyPayment(
                BigDecimal.valueOf(scoringServiceImpl.totalScoring(scoringDataDto)),
                scoringDataDto.getAmount(),
                scoringDataDto.getTerm(),
                scoringDataDto.getIsInsuranceEnabled()
        );
        System.out.println(monthlyPayment);
        CreditDto creditDto = CreditDto.builder()
                .amount(scoringDataDto.getAmount())
                .term(scoringDataDto.getTerm())
                .monthlyPayment(monthlyPayment.setScale(2, RoundingMode.HALF_UP))
                .rate(BigDecimal.valueOf(scoringServiceImpl.totalScoring(scoringDataDto)))
                .isInsuranceEnabled(scoringDataDto.getIsInsuranceEnabled())
                .isSalaryClient(scoringDataDto.getIsSalaryClient())
                .psk(creditDataServiceImpl.calculateTotalAmount(monthlyPayment, scoringDataDto.getTerm())
                        .setScale(2, RoundingMode.HALF_UP))
                .build();
        creditDto.setPaymentSchedule(paymentElementsServiceImpl.calculatePaymentScheduleElements(creditDto));
        log.info("Метод вернул модель 'CreditDto' со значениями " + creditDto);
        return creditDto;

    }

}
