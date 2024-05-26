package ru.litlmayn.calculator.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.litlmayn.calculator.dto.CreditDto;
import ru.litlmayn.calculator.dto.PaymentScheduleElementDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CreatePaymentScheduleElements {
    private final CalculateCreditDataService calculateCreditDataService;

    List<PaymentScheduleElementDto> list = new ArrayList<>();
    LocalDate datePay = LocalDate.now();

    @Autowired
    public CreatePaymentScheduleElements(CalculateCreditDataService calculateCreditDataService) {
        this.calculateCreditDataService = calculateCreditDataService;
    }

    public List<PaymentScheduleElementDto> calculatePaymentScheduleElements(CreditDto creditDto) {
        BigDecimal totalAmount = creditDto.getAmount();
        BigDecimal interestPayment = calculateCreditDataService.calculateMonthlyInsurancePayment(creditDto.getAmount());
        BigDecimal debtPayment = calculateCreditDataService.calculateMonthlyAmountPayment(
                creditDto.getRate(), creditDto.getAmount(), creditDto.getTerm()
        );
        BigDecimal totalPayment = interestPayment.add(debtPayment);
        for (int i = 1; i < creditDto.getTerm(); i++) {
            totalAmount = totalAmount.subtract(totalPayment);
            list.add(new PaymentScheduleElementDto(
                    i,
                    calculateDatePay(),
                    totalPayment,
                    interestPayment,
                    debtPayment,
                    totalAmount
                    )
            );
        }
        return list;
    }

    private LocalDate calculateDatePay() {
        datePay = datePay.plusMonths(1);
        return datePay;
    }
}
