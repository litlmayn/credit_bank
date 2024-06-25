package ru.litlmayn.deal.controllers.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.litlmayn.api.controllers.DealControllerApi;
import ru.litlmayn.api.dto.FinishRegistrationRequestDto;
import ru.litlmayn.api.dto.LoanOfferDto;
import ru.litlmayn.api.dto.LoanStatementRequestDto;
import ru.litlmayn.deal.services.CalculateStatementId;
import ru.litlmayn.deal.services.OfferSelectService;
import ru.litlmayn.deal.services.StatementService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/deal")
@AllArgsConstructor
@Tag(name = "Deal controller", description = "Рассчет всех данных для сделки и сохранекние ее в БД")
public class DealControllerImpl implements DealControllerApi {

    private CalculateStatementId calculateStatementId;
    private StatementService statementService;
    private OfferSelectService offerSelectService;

    @Override
    @PostMapping("/statement")
    @Operation(summary = "Создание клиента и заявки и сохранения их в бд")
    public List<LoanOfferDto> statement(@RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        log.info("statement() - start: LoanStatementRequestDto={}", loanStatementRequestDto);
        List<LoanOfferDto> list = statementService.createAnswerStatement(loanStatementRequestDto);
        log.info("statement() - end: List<loanOfferDto>={}", list);
        return list;
    }

    @Override
    @PostMapping("/offer/select")
    @Operation(summary = "Добавление кредитного предложения в заявку и изменнеие статуса")
    public void offerSelect(@RequestBody LoanOfferDto loanOfferDto) {
        log.info("offerSelect - start: LoanOfferDto={}", loanOfferDto);
        offerSelectService.updateLoanOfferInStatement(loanOfferDto);
        log.info("offerSelect - end: void");
    }

    @Override
    @PostMapping("/calculate/{statementId}")
    @Operation(summary = "Создание модели кредит и добавление в бд")
    public void createCredit(
            @RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto,
            @RequestParam("statementId") String statementIdString
    ) {
        log.info("createCredit() - start: FinishRegistrationRequestDto={}", finishRegistrationRequestDto
                + ", statementIdString = " + statementIdString);
        calculateStatementId.calculateCredit(finishRegistrationRequestDto, statementIdString);
        log.info("createCredit() - end: void");
    }
}
