package ru.litlmayn.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.litlmayn.api.dto.enums.Gender;
import ru.litlmayn.api.dto.enums.MaritalStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Client {
    @Id
    @Column(name = "client_id")
    @GeneratedValue
    private  UUID clientId;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "birth_day")
    private LocalDate birthdate;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column(name = "marital_status")
    @Enumerated(value = EnumType.STRING)
    private MaritalStatus maritalStatus;

    @Column(name = "dependent_amount")
    private Integer dependentAmount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passport_id")
    private Passport passportId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employment_id")
    private Employment employmentId;

    @Column(name = "account_number")
    private String accountNumber;

}
