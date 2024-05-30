package ru.litlmayn.api.controllers;

import org.springframework.http.ResponseEntity;
import ru.litlmayn.api.exceptions.RefusalCreditException;

public interface ControllerExceptionHandler {

    ResponseEntity<String> handleRefusalCreditException(RefusalCreditException exception);
}
