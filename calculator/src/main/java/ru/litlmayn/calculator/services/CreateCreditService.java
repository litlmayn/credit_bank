package ru.litlmayn.calculator.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.litlmayn.calculator.dto.CreditDto;
import ru.litlmayn.calculator.dto.ScoringDataDto;
import ru.litlmayn.calculator.exceptions.RefusalCreditException;

import java.math.BigDecimal;

@Service
public class CreateCreditService {
    private final CalculateCreditDataService calculateCreditDataService;
    private final CreatePaymentScheduleElements createPaymentScheduleElements;
    private final ScoringService scoringService;

    @Autowired
    public CreateCreditService(CalculateCreditDataService calculateCreditDataService, CreatePaymentScheduleElements createPaymentScheduleElements, ScoringService scoringService) {
        this.calculateCreditDataService = calculateCreditDataService;
        this.createPaymentScheduleElements = createPaymentScheduleElements;
        this.scoringService = scoringService;
    }

    public CreditDto createCreditDto(ScoringDataDto scoringDataDto) throws RefusalCreditException {
        BigDecimal rate = BigDecimal.valueOf(scoringService.totalScoring(scoringDataDto));
        CreditDto creditDto = new CreditDto(
                scoringDataDto.getAmount(),
                scoringDataDto.getTerm(),
                scoringDataDto.getIsInsuranceEnabled(),
                scoringDataDto.getIsSalaryClient()
        );
        creditDto.setRate(calculateCreditDataService
                .calculateTotalRate(creditDto.getIsInsuranceEnabled(), creditDto.getIsSalaryClient(), rate));
        creditDto.setMonthlyPayment(calculateCreditDataService.calculateFullMonthlyPayment(
                creditDto.getRate(), creditDto.getAmount(), creditDto.getTerm()
        ));
        creditDto.setPsk(calculateCreditDataService.calculateTotalAmount(
                creditDto.getMonthlyPayment(), creditDto.getTerm()
        ));
        creditDto.setPaymentSchedule(createPaymentScheduleElements.calculatePaymentScheduleElements(creditDto));
        return creditDto;

    }

}
