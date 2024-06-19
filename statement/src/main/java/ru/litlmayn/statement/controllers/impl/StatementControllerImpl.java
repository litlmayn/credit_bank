package ru.litlmayn.statement.controllers.impl;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.litlmayn.api.controllers.StatementController;
import ru.litlmayn.api.dto.LoanOfferDto;
import ru.litlmayn.api.dto.LoanStatementRequestDto;
import ru.litlmayn.statement.services.RequestOnDealCreateOffers;
import ru.litlmayn.statement.services.RequestOnDealOfferSelect;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/statement")
@AllArgsConstructor
public class StatementControllerImpl implements StatementController {

    private RequestOnDealOfferSelect requestOnDealOfferSelect;

    private RequestOnDealCreateOffers requestOnDealCreateOffers;


    @Override
    @PostMapping("/statement")
    @Operation(summary = "Прескоринг + запрос на расчет возможных условий кредита")
    public List<LoanOfferDto> createOffers(@Valid @RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        log.info("createOffers() - start: LoanStatementRequestDto = " + loanStatementRequestDto);
        List<LoanOfferDto> offerDtoList = requestOnDealCreateOffers.requestOnDealCreateOffers(loanStatementRequestDto);
        log.info("createOffers() - end: List<LoanOfferDto> = " + offerDtoList);
        return offerDtoList;
    }

    @Override
    @PostMapping("/statement/offer")
    @Operation(summary = "Выбор одного из предложений")
    public void choiceOffer(@Valid @RequestBody LoanOfferDto loanOfferDto) {
        log.info("choiceOffer() - start: LoanOfferDto = " + loanOfferDto);
        requestOnDealOfferSelect.requestOnDealOfferSelect(loanOfferDto);
        log.info("choiceOffer() - end: void");
    }
}
