package ru.litlmayn.api.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class StatementStatusHistoryDto {
    private Enum status;
    private LocalDateTime time;
    private Enum changeType;
}
