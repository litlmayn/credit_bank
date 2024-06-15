package ru.litlmayn.models;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;
import ru.litlmayn.api.dto.LoanOfferDto;
import ru.litlmayn.api.dto.enums.ApplicationStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "statement")
public class Statement {

    @Id
    @Column(name = "statement_id")
    private UUID statementId;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client clientId;

    @OneToOne
    @JoinColumn(name = "credit_id")
    private Credit credit;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(name = "creation_date")
    private Date creationDate;

    @Type(type = "jsonb")
    @Column(name = "applied_offer", columnDefinition = "jsonb")
    private LoanOfferDto appliedOffer;

    @Column(name = "sign_date")
    private LocalDateTime signDate;

    @Column(name = "ses_code")
    private String ses_code;

    @Type(type = "jsonb")
    @Column(name = "status_history", columnDefinition = "jsonb")
    private StatusHistory statusHistory;
}
