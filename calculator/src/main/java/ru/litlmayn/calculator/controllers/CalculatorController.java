package ru.litlmayn.calculator.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.litlmayn.calculator.dto.CreditDto;
import ru.litlmayn.calculator.dto.LoanOfferDto;
import ru.litlmayn.calculator.dto.LoanStatementRequestDto;
import ru.litlmayn.calculator.dto.ScoringDataDto;
import ru.litlmayn.calculator.exceptions.RefusalCreditException;
import ru.litlmayn.calculator.services.CreateCreditService;
import ru.litlmayn.calculator.services.LoanOffersService;
import ru.litlmayn.calculator.services.impl.CreateCreditServiceImpl;
import ru.litlmayn.calculator.services.impl.LoanOffersServiceImpl;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/calculator")
@AllArgsConstructor
@Tag(name = "Calculator controller", description = "Расчитывает все данные по кредиту")
public class CalculatorController {

    private final LoanOffersServiceImpl loanOffersService;
    private final CreateCreditServiceImpl createCreditService;

    @PostMapping("/offers")
    @Operation(summary = "Создание четырех кредитных предложений")
    public List<LoanOfferDto> offers(@Valid @RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        log.info("Старт метода 'offers' констроллера 'CalculatorController'");
        log.info("Контроллер стартовал с параметрами: " + loanStatementRequestDto.toString());
        List<LoanOfferDto> returnData = loanOffersService.createListOffers(loanStatementRequestDto);
        log.info("Метод контроллера вернул: " + returnData.toString());
        return returnData;
    }

    @PostMapping("/calc")
    @Operation(summary = "Полный расчет всех данных выбранного кредита")
    public CreditDto calc(@Valid @RequestBody ScoringDataDto scoringDataDto) {
        log.info("Старт метода 'calc' констроллера 'CalculatorController'");
        log.info("Контроллер стартовал с параметрами: " + scoringDataDto.toString());
        try {

            CreditDto creditDto = createCreditService.createCreditDto(scoringDataDto);
            log.info("Метод контроллера вернул: " + creditDto);
            return creditDto;
        } catch (RefusalCreditException e) {
            log.info("Метод контроллера вернул: " + e.fillInStackTrace());
            throw new RuntimeException(e);
        }
    }

    @ExceptionHandler(RefusalCreditException.class)
    public ResponseEntity<String> handleException(RefusalCreditException exception) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(exception.getMessage());
    }

}
