package ru.neoflex.deal.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Список платежей")
public class PaymentSchedule {
        @Schema(description = "Номер платежа")
        Integer number;

        @Schema(description = "Дата платежа")
        LocalDate date;

        @Schema(description = "Общая сумма платежа")
        BigDecimal totalPayment;

        @Schema(description = "Погашение долга")
        BigDecimal interestPayment;

        @Schema(description = "Погашение процентов")
        BigDecimal debtPayment;

        @Schema(description = "Оставшийся долг")
        BigDecimal remainingDebt;
}