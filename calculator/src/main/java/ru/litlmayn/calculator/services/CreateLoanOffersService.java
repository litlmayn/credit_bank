package ru.litlmayn.calculator.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.litlmayn.calculator.dto.LoanOfferDto;
import ru.litlmayn.calculator.dto.LoanStatementRequestDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@PropertySource("classpath:calculator.properties")
public class CreateLoanOffersService {
    @Value("${base.rate}")
    private double rate;
    private final CalculateCreditDataService calculateLoanTermsService;


    @Autowired
    public CreateLoanOffersService(CalculateCreditDataService calculateCreditDataService) {
        this.calculateLoanTermsService = calculateCreditDataService;
    }


    public List<LoanOfferDto> createListLoanOffersDto(
            LoanStatementRequestDto loanStatementRequestDto
    ) {
        List<LoanOfferDto> loanOfferDtoList = new ArrayList<>();
        loanOfferDtoList.add(createLoanOfferDto(loanStatementRequestDto, false, false));
        loanOfferDtoList.add(createLoanOfferDto(loanStatementRequestDto, true, false));
        loanOfferDtoList.add(createLoanOfferDto(loanStatementRequestDto, false, true));
        loanOfferDtoList.add(createLoanOfferDto(loanStatementRequestDto, true, true));
        return loanOfferDtoList;
    }

    private LoanOfferDto createLoanOfferDto(
            LoanStatementRequestDto loanStatementRequestDto,
            Boolean isInsuranceEnabled,
            Boolean isSalaryClient
    ) {
        LoanOfferDto loanOfferDto = new LoanOfferDto(isInsuranceEnabled, isSalaryClient);
        loanOfferDto.setRequestedAmount(loanStatementRequestDto.getAmount());
        loanOfferDto.setTerm(loanStatementRequestDto.getTerm());
        loanOfferDto.setRate(calculateLoanTermsService.calculateTotalRate(
                isInsuranceEnabled, isSalaryClient, BigDecimal.valueOf(rate)));
        loanOfferDto.setMonthlyPayment(calculateLoanTermsService.calculateFullMonthlyPayment(
                loanOfferDto.getRate(), loanOfferDto.getRequestedAmount(), loanOfferDto.getTerm()
        ));
        loanOfferDto.setTotalAmount(calculateLoanTermsService.calculateTotalAmount(
                loanOfferDto.getMonthlyPayment(), loanOfferDto.getTerm()
        ));
        loanOfferDto.setStatementId(UUID.randomUUID());
        return loanOfferDto;
    }

}
