package ru.litlmayn.statement.controllers.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.litlmayn.api.exceptions.RefusalCreditException;
import ru.litlmayn.api.exceptions.ValidationErrorResponse;
import ru.litlmayn.api.exceptions.Violation;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(RefusalCreditException.class)
    public ResponseEntity<String> handleRefusalCreditException(RefusalCreditException exception) {
        // 500 ответ сервера, когда клиенту отказано в кредите. Для читаемого варианта создал кастомный exception.
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        // 400 ответ сервера, когда клиенту отправляет не валидные данные.
        // Для ответа с обоснованием ошибки для пользователя.
        final List<Violation> violations = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }
}
