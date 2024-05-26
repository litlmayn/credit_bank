package ru.litlmayn.calculator.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailMessage {
    private String address;
    private Enum theme;
    private Long statementId;
}
