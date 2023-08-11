package ru.neoflex.deal.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Список платежей")
public record PaymentSchedule(
        @Schema(description = "Номер платежа")
        Integer number,

        @Schema(description = "Дата платежа")
        LocalDate date,

        @Schema(description = "Общая сумма платежа")
        BigDecimal totalPayment,

        @Schema(description = "Погашение долга")
        BigDecimal interestPayment,

        @Schema(description = "Погашение процентов")
        BigDecimal debtPayment,

        @Schema(description = "Оставшийся долг")
        BigDecimal remainingDebt
) {}