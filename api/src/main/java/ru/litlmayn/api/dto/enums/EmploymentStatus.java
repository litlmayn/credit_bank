package ru.litlmayn.api.dto.enums;

import lombok.Getter;

@Getter
public enum EmploymentStatus {
    UNEMPLOYED(0), WORKER(0), SELF_EMPLOYED(1), BUSINESS_OWNER(2);

    private final int changeRate;

    EmploymentStatus(int changeRate) {
        this.changeRate = changeRate;
    }
}
