package ru.neoflex.deal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.neoflex.deal.dto.response.PaymentSchedule;
import ru.neoflex.deal.enums.CreditStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "credit")
@NoArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_id", nullable = false)
    private Long creditId;

    @Column(name = "term", nullable = false)
    private Integer term;

    @Column(name = "monthly_payment", nullable = false)
    private BigDecimal monthlyPayment;

    @Column(name = "rate", nullable = false)
    private BigDecimal rate;

    @Column(name = "psk", nullable = false)
    private BigDecimal psk;

    @JsonIgnore
    @Type(type = "jsonb")
    @Column(name = "payment_schedule")
    private String paymentScheduleString;

    @Transient
    private List<PaymentSchedule> paymentScheduleList;

    @Column(name = "insurance_enable", nullable = false)
    private Boolean insuranceEnable;

    @Column(name = "salary_client", nullable = false)
    private Boolean salaryClient;

    @Enumerated(EnumType.STRING)
    @Column(name = "credit_status", nullable = false)
    private CreditStatus creditStatus;

    public Credit(Integer term, BigDecimal monthlyPayment, BigDecimal rate, BigDecimal psk, List<PaymentSchedule> paymentScheduleList, Boolean insuranceEnable, Boolean salaryClient) {
        this.term = term;
        this.monthlyPayment = monthlyPayment;
        this.rate = rate;
        this.psk = psk;
        this.paymentScheduleList = paymentScheduleList;
        this.insuranceEnable = insuranceEnable;
        this.salaryClient = salaryClient;
    }
}
