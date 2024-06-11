package ru.litlmayn.models;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;
import ru.litlmayn.api.dto.enums.ApplicationStatus;
import ru.litlmayn.api.dto.enums.ChangeType;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "status_history")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class StatusHistory {

    @Column(name = "status")
    private ApplicationStatus status;

    @Column(name = "time")
    private Date time;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "change_type")
    private ChangeType changeType;
}
