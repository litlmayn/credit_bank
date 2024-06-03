package ru.litlmayn.api.dto.enums;

import lombok.Getter;

@Getter
public enum MaritalStatus {
    SINGLE(0), MARRIED(3), DIVORCED(1);

    private final int changeRate;

    MaritalStatus(int changeRate){
        this.changeRate = changeRate;
    }
}
