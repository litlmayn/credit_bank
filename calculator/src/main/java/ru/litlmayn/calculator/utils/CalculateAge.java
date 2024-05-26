package ru.litlmayn.calculator.utils;

import java.time.LocalDate;

public class CalculateAge {
    public static int calculateAge(LocalDate dateBirthday) {
        return LocalDate.now().minusYears(dateBirthday.getYear()).getYear();
    }
}
