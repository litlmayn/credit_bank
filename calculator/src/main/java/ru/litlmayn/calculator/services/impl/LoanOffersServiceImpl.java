package ru.litlmayn.calculator.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.litlmayn.api.dto.LoanOfferDto;
import ru.litlmayn.api.dto.LoanStatementRequestDto;
import ru.litlmayn.calculator.services.CreditDataService;
import ru.litlmayn.calculator.services.LoanOffersService;
import ru.litlmayn.calculator.services.ScoringService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
@PropertySource("classpath:application.properties")
public class LoanOffersServiceImpl implements LoanOffersService {

    private final CreditDataService creditDataService;
    private final ScoringService scoring;

    public List<LoanOfferDto> createListOffers(LoanStatementRequestDto loanStatementRequestDto) {
        // метод для создания 4 первоначальных кредитных прелложений
        log.info("createListOffers() - start: loanStatementRequestDto={}", loanStatementRequestDto);
        List<LoanOfferDto> list = List.of(
                createOffer(loanStatementRequestDto, false, false),
                createOffer(loanStatementRequestDto, false, true),
                createOffer(loanStatementRequestDto, true, false),
                createOffer(loanStatementRequestDto, true, true)
        );
        log.info("createListOffers() - end: List<LoanOfferDto>={}", list.toString());
        return list;
    }

    private LoanOfferDto createOffer(
            LoanStatementRequestDto loanStatementRequestDto,
            Boolean isInsuranceEnabled,
            Boolean isSalaryClient
    ) {
        // метод для создания первоначального кредитного прелложения
        log.info("createOffer() - start: loanStatementRequestDto={}, isInsuranceEnabled={}, isSalaryClient={}",
                loanStatementRequestDto, isInsuranceEnabled, isSalaryClient);
        BigDecimal rate = BigDecimal.valueOf(scoring.scoringInsAndSal(
                        isInsuranceEnabled, isSalaryClient));
        BigDecimal monthlyPayment = creditDataService
                .calculateFullMonthlyPayment(
                        rate, loanStatementRequestDto.getAmount(),
                        loanStatementRequestDto.getTerm(),
                        isInsuranceEnabled);
        LoanOfferDto loanOfferDto = LoanOfferDto.builder()
                .statementId(UUID.randomUUID())
                .requestedAmount(loanStatementRequestDto.getAmount())
                .totalAmount(creditDataService.calculateTotalAmount(
                                monthlyPayment, loanStatementRequestDto.getTerm())
                        .setScale(2, RoundingMode.HALF_UP))
                .term(loanStatementRequestDto.getTerm())
                .monthlyPayment(monthlyPayment.setScale(2, RoundingMode.HALF_UP))
                .rate(rate.setScale(2, RoundingMode.HALF_UP))
                .isInsuranceEnabled(isInsuranceEnabled)
                .isSalaryClient(isSalaryClient)
                .build();
        log.info("createOffer() - end: LoanOfferDto={}", loanOfferDto);
        return loanOfferDto;
    }
}
