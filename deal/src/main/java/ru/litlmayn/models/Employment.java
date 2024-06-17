package ru.litlmayn.models;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;
import ru.litlmayn.api.dto.enums.EmploymentStatus;
import ru.litlmayn.api.dto.enums.Position;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Table(name = "employment")
public class Employment {
    @Id
    @Column(name = "employment_uuid")
    @GeneratedValue
    private UUID employmentId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EmploymentStatus employerStatus;

    @Column(name = "employer_inn")
    private String employerINN;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "position")
    @Enumerated(EnumType.STRING)
    private Position position;

    @Column(name = "work_experience_total")
    private Integer workExperienceTotal;

    @Column(name = "work_experience_current")
    private Integer workExperienceCurrent;
}
