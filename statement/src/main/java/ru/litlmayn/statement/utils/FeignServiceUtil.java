package ru.litlmayn.statement.utils;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PostMapping;
import ru.litlmayn.api.dto.LoanOfferDto;
import ru.litlmayn.api.dto.LoanStatementRequestDto;

import java.util.List;

@PropertySource("classpath:application.properties")
@FeignClient(value = "${feignClient.value}", url = "${feignClient.url}")
public interface FeignServiceUtil {

    @PostMapping("/statement")
    List<LoanOfferDto> requestOnDealCreateOffers(LoanStatementRequestDto loanStatementRequestDto);

    @PostMapping("/offer/select")
    void requestOnDealOfferSelect(LoanOfferDto loanOfferDto);

}
