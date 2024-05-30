package ru.litlmayn.calculator.services;

import ru.litlmayn.api.dto.CreditDto;
import ru.litlmayn.api.dto.PaymentScheduleElementDto;

import java.util.List;

public interface PaymentElementsService {
    List<PaymentScheduleElementDto> calculatePaymentScheduleElements(CreditDto creditDto);
}