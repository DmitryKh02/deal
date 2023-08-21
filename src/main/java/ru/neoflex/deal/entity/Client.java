package ru.neoflex.deal.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.neoflex.deal.entity.jsonb.Employment;
import ru.neoflex.deal.entity.jsonb.Passport;
import ru.neoflex.deal.enums.Gender;
import ru.neoflex.deal.enums.MaterialStatus;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "client")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "first_name", length = 32, nullable = false)
    private String firstName;

    @Column(name = "middle_name", length = 32)
    private String middleName;

    @Column(name = "last_name", length = 32, nullable = false)
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthdate;

    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "material_status")
    private MaterialStatus materialStatus;

    @Column(name = "dependent_amount")
    private Integer dependentAmount;

    @Type(type = "jsonb")
    @Column(name = "passport")
    private Passport passport;

    @Type(type = "jsonb")
    @Column(name = "employment")
    private Employment employment;

    @Column(name = "account")
    private String account;
}
