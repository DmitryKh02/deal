package ru.neoflex.deal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Вариант кредитного предложения")
public class LoanOfferDTO {
    @Schema(description = "ID офера")
    Long applicationId;

    @Schema(description = "Запрошенная сумма кредита")
    BigDecimal requestedAmount;

    @Schema(description = "Итоговая сумма выдаваемого кредита")
    BigDecimal totalAmount;

    @Schema(description = "Срок выдачи кредита в месяцах")
    Integer term;

    @Schema(description = "Месячный платеж")
    BigDecimal monthlyPayment;

    @Schema(description = "Ставка по кредиту")
    BigDecimal rate;

    @Schema(description = "Включена ли страховка")
    Boolean isInsuranceEnabled;

    @Schema(description = "Зарплатный клиент (зарплата в этом банке)")
    Boolean isSalaryClient;
}
