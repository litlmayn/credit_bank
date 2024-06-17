package ru.litlmayn.utils;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PostMapping;
import ru.litlmayn.api.dto.CreditDto;
import ru.litlmayn.api.dto.LoanOfferDto;
import ru.litlmayn.api.dto.LoanStatementRequestDto;
import ru.litlmayn.api.dto.ScoringDataDto;

import java.util.List;

@PropertySource("classpath:application.properties")
@FeignClient(value = "${feignClient.value}", url = "${feignClient.url}")
public interface FeignServiceUtil {

    @PostMapping("/offers")
    List<LoanOfferDto> createLoanOffersDto(LoanStatementRequestDto loanStatementRequestDto);

    @PostMapping("/calc")
    CreditDto createCreditDto(ScoringDataDto scoringDataDto);
}
