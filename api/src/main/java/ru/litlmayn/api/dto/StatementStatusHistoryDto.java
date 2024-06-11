package ru.litlmayn.api.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.litlmayn.api.dto.enums.ApplicationStatus;
import ru.litlmayn.api.dto.enums.ChangeType;

import java.time.LocalDateTime;

@Data
public class StatementStatusHistoryDto {

    private ApplicationStatus status;

    private LocalDateTime time;

    private ChangeType changeType;
}
