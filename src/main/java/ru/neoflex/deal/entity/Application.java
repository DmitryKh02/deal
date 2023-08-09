package ru.neoflex.deal.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.neoflex.deal.entity.jsonb.StatusHistory;
import ru.neoflex.deal.enums.ApplicationStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "application")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id", nullable = false)
    Long applicationId;

    @OneToOne
    @JoinColumn(name = "client_id")
    Client client;

    @OneToOne
    @JoinColumn(name = "credit_id")
    Credit credit;

    @Column(name = "status", nullable = false)
    ApplicationStatus status;

    @Column(name = "creation_date", nullable = false)
    LocalDateTime creationDate;

    @Type(type = "jsonb")
    @Column(name = "applied_offer", nullable = false)
    AppliedOffer appliedOffer;

    @Column(name = "sign_date", nullable = false)
    LocalDateTime signDate;

    @Column(name = "ses_code", nullable = false)
    String sesCode;

    @Type(type = "jsonb")
    @Column(name = "status_history", nullable = false)
    StatusHistory statusHistory;
}
