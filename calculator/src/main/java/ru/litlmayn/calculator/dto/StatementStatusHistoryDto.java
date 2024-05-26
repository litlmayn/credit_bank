package ru.litlmayn.calculator.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StatementStatusHistoryDto {
    private Enum status;
    private LocalDateTime time;
    private Enum changeType;
}
