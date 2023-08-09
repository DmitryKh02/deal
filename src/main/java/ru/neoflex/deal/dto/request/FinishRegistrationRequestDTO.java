package ru.neoflex.deal.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.neoflex.deal.enums.Gender;
import ru.neoflex.deal.enums.MaterialStatus;

import java.time.LocalDate;

@Schema(description = "Информация для регистрации")
public record FinishRegistrationRequestDTO(
        @Schema(description = "Пол")
        Gender gender,

        @Schema(description = "Материальное положение")
        MaterialStatus materialStatus,

        @Schema(description = "Число иждивенцев")
        Integer dependentAmount,

        @Schema(description = "Дата выдачи паспорта")
        LocalDate passportIssueDate,

        @Schema(description = "Кем выдан паспорт")
        String passportIssueBranch,

        @Schema(description = "Данные о работнике")
        EmploymentDTO employmentDTO,

        @Schema(description = "Аккаунт")
        String account
) {
}
