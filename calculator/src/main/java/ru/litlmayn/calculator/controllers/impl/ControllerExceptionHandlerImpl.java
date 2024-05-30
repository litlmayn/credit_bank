package ru.litlmayn.calculator.controllers.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.litlmayn.api.exceptions.RefusalCreditException;

@ControllerAdvice
public class ControllerExceptionHandlerImpl {
    @ExceptionHandler(RefusalCreditException.class)
    public ResponseEntity<String> handleRefusalCreditException(RefusalCreditException exception) {
        // 500 ответ сервера, когда клиенту отказано в кредите. Для читаемого варианта создал кастомный exception.
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(exception.getMessage());
    }


}
