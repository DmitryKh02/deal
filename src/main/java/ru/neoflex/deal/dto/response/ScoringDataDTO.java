package ru.neoflex.deal.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.neoflex.deal.dto.request.EmploymentDTO;
import ru.neoflex.deal.enums.Gender;
import ru.neoflex.deal.enums.MaterialStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Schema(description = "Данные для оформления кредита")
public class ScoringDataDTO {
        @Schema(description = "Сумма кредита")
        private BigDecimal amount;

        @Schema(description = "Срок выдачи кредита в месяцах")
        private Integer term;

        @Schema(description = "Имя")
        private String firstName;

        @Schema(description = "Фамилия")
        private String lastName;

        @Schema(description = "Отчество")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String middleName;

        @Schema(description = "Пол")
        private Gender gender;

        @Schema(description = "День рождения")
        private LocalDate birthdate;

        @Schema(description = "Серия паспорта")
        private String passportSeries;

        @Schema(description = "Номер паспорта")
        private String passportNumber;

        @Schema(description = "Дата выдачи паспорта")
        private LocalDate passportIssueDate;

        @Schema(description = "Кем выдан паспорт")
        private String passportIssueBranch;

        @Schema(description = "Материальный статус")
        private MaterialStatus maritalStatus;

        @Schema(description = "Число иждивенцев")
        private Integer dependentAmount;

        @Schema(description = "Данные о работнике")
        private EmploymentDTO employment;

        @Schema(description = "Аккаунт")
        private String account;

        @Schema(description = "Включена ли страховка")
        private Boolean isInsuranceEnabled;

        @Schema(description = "Зарплатный клиент (зарплата в этом банке)")
        private Boolean isSalaryClient;
}
