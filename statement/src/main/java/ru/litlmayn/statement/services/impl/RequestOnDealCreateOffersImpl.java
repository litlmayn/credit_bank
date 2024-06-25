package ru.litlmayn.statement.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.litlmayn.api.dto.LoanOfferDto;
import ru.litlmayn.api.dto.LoanStatementRequestDto;
import ru.litlmayn.statement.services.RequestOnDealCreateOffers;
import ru.litlmayn.statement.utils.FeignServiceUtil;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RequestOnDealCreateOffersImpl implements RequestOnDealCreateOffers {

    private FeignServiceUtil feignServiceUtil;

    @Override
    public List<LoanOfferDto> requestOnDealCreateOffers(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("requestOnDealCreateOffers() - start: LoanStatementRequestDto={}", loanStatementRequestDto);
        List<LoanOfferDto> offerDtoList = feignServiceUtil.requestOnDealCreateOffers(loanStatementRequestDto);
        log.info("requestOnDealCreateOffers() - end: List<LoanOfferDto>={}", offerDtoList);
        return offerDtoList;
    }
}
