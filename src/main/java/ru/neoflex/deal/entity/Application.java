package ru.neoflex.deal.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.neoflex.deal.dto.LoanOfferDTO;
import ru.neoflex.deal.entity.jsonb.ApplicationStatusHistoryDTO;
import ru.neoflex.deal.enums.ApplicationStatus;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "application")
@NoArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id", nullable = false)
    private Long applicationId;

    @OneToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToOne
    @JoinColumn(name = "credit_id")
    private Credit credit;

    @Column(name = "status", nullable = false)
    private ApplicationStatus status;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Type(type = "jsonb")
    @Column(name = "applied_offer")
    private LoanOfferDTO appliedOffer;

    @Column(name = "sign_date")
    private LocalDateTime signDate;

    @Column(name = "ses_code", nullable = false)
    private String sesCode;

    @Type(type = "jsonb")
    @Column(name = "status_history")
    private List<ApplicationStatusHistoryDTO> statusHistoryList;

    public Application(Client client, ApplicationStatus status, LocalDateTime creationDate, String sesCode, ApplicationStatusHistoryDTO statusHistoryDTO) {
        this.client = client;
        this.status = status;
        this.creationDate = creationDate;
        this.sesCode = sesCode;
        this.statusHistoryList = new ArrayList<>();
        this.statusHistoryList.add(statusHistoryDTO);
    }

    public void addStatus(ApplicationStatusHistoryDTO status){
        this.statusHistoryList.add(status);
    }
}
