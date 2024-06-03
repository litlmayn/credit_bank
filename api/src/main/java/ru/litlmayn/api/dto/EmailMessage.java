package ru.litlmayn.api.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class EmailMessage {
    private String address;
    private Enum theme;
    private Long statementId;
}
