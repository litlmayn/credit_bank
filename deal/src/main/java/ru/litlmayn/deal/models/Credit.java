package ru.litlmayn.deal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import ru.litlmayn.api.dto.PaymentScheduleElementDto;
import ru.litlmayn.api.dto.enums.CreditStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "credit")
public class Credit {

    @Id
    @GeneratedValue
    @Column(name = "credit_id")
    private UUID creditId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "term")
    private Integer term;

    @Column(name = "monthly_payment")
    private BigDecimal monthlyPayment;

    @Column(name = "rate")
    private BigDecimal rate;

    @Column(name = "psk")
    private BigDecimal psk;

    @Type(type = "jsonb")
    @Column(name = "payment_schedule", columnDefinition = "jsonb")
    private List<PaymentScheduleElementDto> paymentSchedule;

    @Column(name = "insurance_enable")
    private Boolean insuranceEnabled;

    @Column(name = "salary_client")
    private Boolean salaryClient;

    @Column(name = "credit_status")
    @Enumerated(EnumType.STRING)
    private CreditStatus creditStatus = CreditStatus.CALCULATED;
}
