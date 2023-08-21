package ru.neoflex.deal.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Schema(description = "Запрос на создание кредитного предложения")
public class LoanApplicationRequestDTO {
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

        @Schema(description = "E-mail")
        private String email;

        @Schema(description = "День рождения")
        private LocalDate birthdate;

        @Schema(description = "Серия паспорта")
        private String passportSeries;

        @Schema(description = "Номер паспорта")
        private String passportNumber;
}