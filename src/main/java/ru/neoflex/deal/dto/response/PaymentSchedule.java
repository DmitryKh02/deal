package ru.neoflex.deal.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "Список платежей")
public class PaymentSchedule {
        @Schema(description = "Номер платежа")
        private Integer number;

        @Schema(description = "Дата платежа")
        private LocalDate date;

        @Schema(description = "Общая сумма платежа")
        private BigDecimal totalPayment;

        @Schema(description = "Погашение долга")
        private BigDecimal interestPayment;

        @Schema(description = "Погашение процентов")
        private BigDecimal debtPayment;

        @Schema(description = "Оставшийся долг")
        private BigDecimal remainingDebt;
}