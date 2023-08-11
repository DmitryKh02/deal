package ru.neoflex.deal.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.neoflex.deal.dto.response.PaymentSchedule;
import ru.neoflex.deal.enums.CreditStatus;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "credit")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_id", nullable = false)
    Long creditId;

    @Column(name = "term", nullable = false)
    Integer term;

    @Column(name = "monthly_payment", nullable = false)
    BigDecimal monthlyPayment;

    @Column(name = "rate", nullable = false)
    BigDecimal rate;

    @Column(name = "psk", nullable = false)
    BigDecimal psk;

    @Type(type = "jsonb")
    @Column(name = "payment_schedule", nullable = false)
    PaymentSchedule paymentSchedule;

    @Column(name = "insurance_enable", nullable = false)
    Boolean insuranceEnable;

    @Column(name = "salary_client", nullable = false)
    Boolean salaryClient;

    @Column(name = "credit_status", nullable = false)
    CreditStatus creditStatus;
}
