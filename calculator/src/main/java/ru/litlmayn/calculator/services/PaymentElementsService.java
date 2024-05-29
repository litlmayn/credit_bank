package ru.litlmayn.calculator.services;

import ru.litlmayn.calculator.dto.CreditDto;
import ru.litlmayn.calculator.dto.PaymentScheduleElementDto;

import java.util.List;

public interface PaymentElementsService {
    List<PaymentScheduleElementDto> calculatePaymentScheduleElements(CreditDto creditDto);
}