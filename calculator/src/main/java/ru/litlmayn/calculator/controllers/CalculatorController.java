package ru.litlmayn.calculator.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.litlmayn.calculator.dto.CreditDto;
import ru.litlmayn.calculator.dto.LoanOfferDto;
import ru.litlmayn.calculator.dto.LoanStatementRequestDto;
import ru.litlmayn.calculator.dto.ScoringDataDto;
import ru.litlmayn.calculator.exceptions.RefusalCreditException;
import ru.litlmayn.calculator.services.CreateCreditService;
import ru.litlmayn.calculator.services.CreateLoanOffersService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/calculator")
@Tag(name = "Calculator controller", description = "Расчитывает все данные по кредиту")
public class CalculatorController {

    private final CreateLoanOffersService createLoanOffersService;
    private final CreateCreditService createCreditService;

    @Autowired
    public CalculatorController(CreateLoanOffersService createLoanOffersService, CreateCreditService createCreditService) {
        this.createLoanOffersService = createLoanOffersService;
        this.createCreditService = createCreditService;
    }

    @PostMapping("/offers")
    @Operation(summary = "Создание четырех кредитных предложений")
    public List<LoanOfferDto> offers(@Valid @RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        return createLoanOffersService.createListLoanOffersDto(loanStatementRequestDto);
    }

    @PostMapping("/calc")
    @Operation(summary = "Полный расчет всех данных выбранного кредита")
    public CreditDto calc(@Valid @RequestBody ScoringDataDto scoringDataDto) {
        try {
            return createCreditService.createCreditDto(scoringDataDto);
        } catch (RefusalCreditException e) {
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
