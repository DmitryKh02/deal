package ru.neoflex.deal.entity;


import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.neoflex.deal.entity.jsonb.Employment;
import ru.neoflex.deal.entity.jsonb.Passport;
import ru.neoflex.deal.enums.Gender;
import ru.neoflex.deal.enums.MaterialStatus;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "client")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id", nullable = false)
    Long clientId;

    @Column(name = "first_name", length = 32, nullable = false)
    String firstName;

    @Column(name = "middle_name", length = 32)
    String middleName;

    @Column(name = "last_name", length = 32, nullable = false)
    String lastName;

    @Column(name = "birth_data", nullable = false)
    LocalDate birthday;

    @Column(name = "email", nullable = false)
    String email;

    @Column(name = "gender", nullable = false)
    Gender gender;

    @Column(name = "material_status", nullable = false)
    MaterialStatus materialStatus;

    @Column(name = "dependent_amount", nullable = false)
    Integer dependentAmount;

    @Type(type = "jsonb")
    @Column(name = "passport", nullable = false)
    Passport passport;

    @Type(type = "jsonb")
    @Column(name = "employment", nullable = false)
    Employment employment;

    @Column(name = "account", nullable = false)
    String account;
}
