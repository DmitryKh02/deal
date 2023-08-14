package ru.neoflex.deal.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.neoflex.deal.dto.response.PaymentSchedule;
import ru.neoflex.deal.enums.CreditStatus;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "credit")
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

    @Type(type = "jsonb")
    @Column(name = "payment_schedule", nullable = false)
    private PaymentSchedule paymentSchedule;

    @Column(name = "insurance_enable", nullable = false)
    private Boolean insuranceEnable;

    @Column(name = "salary_client", nullable = false)
    private Boolean salaryClient;

    @Column(name = "credit_status", nullable = false)
    private CreditStatus creditStatus;
}
