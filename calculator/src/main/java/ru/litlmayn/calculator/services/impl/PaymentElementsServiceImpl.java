package ru.litlmayn.calculator.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.litlmayn.api.dto.CreditDto;
import ru.litlmayn.api.dto.PaymentScheduleElementDto;
import ru.litlmayn.calculator.services.PaymentElementsService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class PaymentElementsServiceImpl implements PaymentElementsService {

    private final CreditDataServiceImpl creditDataServiceImpl;

    public List<PaymentScheduleElementDto> calculatePaymentScheduleElements(CreditDto creditDto) {
        // создание графика платежей
        log.info("Вызывается метод 'calculatePaymentScheduleElements' класса 'PaymentElementsService'");
        log.info("Входные параметры метода 'calculatePaymentScheduleElements': " + creditDto);
        LocalDate datePay = LocalDate.now();
        List<PaymentScheduleElementDto> list = new ArrayList<>();
        BigDecimal remainderCredit = creditDto.getPsk();
        BigDecimal interestPayment = creditDataServiceImpl.
                calculateMonthlyInsurancePayment(creditDto.getAmount(), creditDto.getIsInsuranceEnabled())
                ;
        BigDecimal debtPayment = creditDataServiceImpl.calculateMonthlyAmountPayment(
                creditDto.getRate(), creditDto.getAmount(), creditDto.getTerm()
        );
        BigDecimal totalPayment = interestPayment.add(debtPayment);
        for (int i = 1; i < creditDto.getTerm() + 1; i++) {
            remainderCredit = remainderCredit.subtract(totalPayment);
            list.add(new PaymentScheduleElementDto(
                    i,
                    counterDatePay(datePay),
                    totalPayment.setScale(2, RoundingMode.HALF_UP),
                    interestPayment.setScale(2, RoundingMode.HALF_UP),
                    debtPayment.setScale(2, RoundingMode.HALF_UP),
                    remainderCredit.setScale(2, RoundingMode.HALF_UP)
                    )
            );
        }
        log.info("Метод возвращает список 'PaymentScheduleElementDto' " + list.toString());
        return list;
    }

    private LocalDate counterDatePay(LocalDate datePay) {
        // счетчик дня платежа
        log.info("Вызывается метод 'counterDatePay' класса 'PaymentElementsService'");
        log.info("Входные параметры отсутвуют");
        datePay = datePay.plusMonths(1);
        log.info("Метод возвращает дату следуюшего платежа: " + datePay);
        return datePay;
    }
}
