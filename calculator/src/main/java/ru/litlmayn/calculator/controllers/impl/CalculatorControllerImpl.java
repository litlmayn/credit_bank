package ru.litlmayn.calculator.controllers.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.litlmayn.api.controllers.CalculatorControllerApi;
import ru.litlmayn.api.dto.CreditDto;
import ru.litlmayn.api.dto.LoanOfferDto;
import ru.litlmayn.api.dto.LoanStatementRequestDto;
import ru.litlmayn.api.dto.ScoringDataDto;
import ru.litlmayn.calculator.exceptions.RefusalCreditException;
import ru.litlmayn.calculator.services.CreateCreditService;
import ru.litlmayn.calculator.services.LoanOffersService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/calculator")
@AllArgsConstructor
@Tag(name = "Calculator controller", description = "Расчитывает все данные по кредиту")
public class CalculatorControllerImpl implements CalculatorControllerApi {

    private final LoanOffersService loanOffersService;
    private final CreateCreditService createCreditService;

    @PostMapping("/offers")
    @Operation(summary = "Создание четырех кредитных предложений")
    public List<LoanOfferDto> offers(@Valid @RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        log.info("offers() - start: LoanStatementRequestDto = " + loanStatementRequestDto);
        List<LoanOfferDto> returnData = loanOffersService.createListOffers(loanStatementRequestDto);
        log.info("offers() - end: List<LoanOfferDto> = " + returnData.toString());
        return returnData;
    }

    @PostMapping("/calc")
    @Operation(summary = "Полный расчет всех данных выбранного кредита")
    public CreditDto calc(@Valid @RequestBody ScoringDataDto scoringDataDto) {
        log.info("calc() - start: ScoringDataDto = " + scoringDataDto);
        try {

            CreditDto creditDto = createCreditService.createCreditDto(scoringDataDto);
            log.info("calc() - end: CreditDto = " + creditDto);
            return creditDto;
        } catch (RefusalCreditException e) {
            log.info("calc() - end:RefusalCreditException = " + e.fillInStackTrace());
            throw new RuntimeException(e);
        }
    }

}
