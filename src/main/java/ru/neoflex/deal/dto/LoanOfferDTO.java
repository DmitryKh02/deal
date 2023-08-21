package ru.neoflex.deal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Schema(description = "Вариант кредитного предложения")
public class LoanOfferDTO {
    @Schema(description = "ID офера")
    private Long applicationId;

    @Schema(description = "Запрошенная сумма кредита")
    private BigDecimal requestedAmount;

    @Schema(description = "Итоговая сумма выдаваемого кредита")
    private BigDecimal totalAmount;

    @Schema(description = "Срок выдачи кредита в месяцах")
    private Integer term;

    @Schema(description = "Месячный платеж")
    private BigDecimal monthlyPayment;

    @Schema(description = "Ставка по кредиту")
    private BigDecimal rate;

    @Schema(description = "Включена ли страховка")
    private Boolean isInsuranceEnabled;

    @Schema(description = "Зарплатный клиент (зарплата в этом банке)")
    private Boolean isSalaryClient;
}
