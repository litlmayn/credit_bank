package ru.litlmayn.calculator.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FinishRegistrationRequestDto {
    private Enum gender;
    private Enum maritalStatus;
    private Integer dependentAmount;
    private LocalDate passportIssueDate;
    private String passportIssueBrach;
    private EmploymentDto employment;
    private String accountNumber;
}
