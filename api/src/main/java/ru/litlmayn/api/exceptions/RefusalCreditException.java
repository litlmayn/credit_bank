package ru.litlmayn.api.exceptions;

public class RefusalCreditException extends Exception {
    public RefusalCreditException(String description) {
        super(description);
    }
}
