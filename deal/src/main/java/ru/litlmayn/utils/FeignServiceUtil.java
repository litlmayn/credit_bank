package ru.litlmayn.utils;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import ru.litlmayn.api.dto.CreditDto;
import ru.litlmayn.api.dto.LoanOfferDto;
import ru.litlmayn.api.dto.LoanStatementRequestDto;
import ru.litlmayn.api.dto.ScoringDataDto;

import java.util.List;

@FeignClient(value = "feignService", url = "http://localhost:8080/api/calculator")
public interface FeignServiceUtil {

    @PostMapping("/offers")
    List<LoanOfferDto> createLoanOffersDto(LoanStatementRequestDto loanStatementRequestDto);

    @PostMapping("/calc")
    CreditDto createCreditDto(ScoringDataDto scoringDataDto);
}
