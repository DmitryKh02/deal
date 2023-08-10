package ru.neoflex.deal.dto.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import ru.neoflex.deal.dto.request.EmploymentDTO;
import ru.neoflex.deal.enums.Gender;
import ru.neoflex.deal.enums.MaterialStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Данные для оформления кредита")
public record ScoringDataDTO(
        @Schema(description = "Сумма кредита")
        BigDecimal amount,

        @Schema(description = "Срок выдачи кредита в месяцах")
        Integer term,

        @Schema(description = "Имя")
        String firstName,

        @Schema(description = "Фамилия")
        String lastName,

        @Schema(description = "Отчество")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String middleName,

        @Schema(description = "Пол")
        Gender gender,

        @Schema(description = "День рождения")
        LocalDate birthdate,

        @Schema(description = "Серия паспорта")
        String passportSeries,

        @Schema(description = "Номер паспорта")
        String passportNumber,

        @Schema(description = "Дата выдачи паспорта")
        LocalDate passportIssueDate,

        @Schema(description = "Кем выдан паспорт")
        String passportIssueBranch,

        @Schema(description = "Материальный статус")
        MaterialStatus maritalStatus,

        @Schema(description = "Число иждивенцев")
        Integer dependentAmount,

        @Schema(description = "Данные о работнике")
        EmploymentDTO employment,

        @Schema(description = "Аккаунт")
        String account,

        @Schema(description = "Включена ли страховка")
        Boolean isInsuranceEnabled,

        @Schema(description = "Зарплатный клиент (зарплата в этом банке)")
        Boolean isSalaryClient
) {}
