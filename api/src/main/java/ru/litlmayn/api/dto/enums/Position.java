package ru.litlmayn.api.dto.enums;

import lombok.Getter;

@Getter
public enum Position {
    WORKER(0), MID_MANAGER(2), TOP_MANAGER(3), OWNER(0);

    private final int changeRate;

    Position(int changeRate) {
        this.changeRate = changeRate;
    }

}
