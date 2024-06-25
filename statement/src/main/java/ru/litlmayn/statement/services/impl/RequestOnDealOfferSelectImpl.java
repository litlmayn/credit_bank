package ru.litlmayn.statement.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.litlmayn.api.dto.LoanOfferDto;
import ru.litlmayn.statement.services.RequestOnDealOfferSelect;
import ru.litlmayn.statement.utils.FeignServiceUtil;

@Slf4j
@Service
@AllArgsConstructor
public class RequestOnDealOfferSelectImpl implements RequestOnDealOfferSelect {

    private FeignServiceUtil feignServiceUtil;

    @Override
    public void requestOnDealOfferSelect(LoanOfferDto loanOfferDto) {
        log.info("requestOnDealOfferSelect() - start: LoanOfferDto={}", loanOfferDto);
        feignServiceUtil.requestOnDealOfferSelect(loanOfferDto);
        log.info("requestOnDealOfferSelect() - end: void");
    }

}
