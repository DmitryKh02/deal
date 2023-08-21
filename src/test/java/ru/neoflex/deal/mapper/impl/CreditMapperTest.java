package ru.neoflex.deal.mapper.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.neoflex.deal.dto.response.CreditDTO;
import ru.neoflex.deal.dto.response.PaymentSchedule;
import ru.neoflex.deal.entity.Credit;
import ru.neoflex.deal.mapper.CreditMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class CreditMapperTest {
    private static CreditMapper creditMapper;

    @BeforeAll
    public static void setup() {
        creditMapper = new CreditMapperImpl();
    }

    @Test
    void CreditMapper_toCreditFromCreditDTO_ReturnCredit() {
        PaymentSchedule paymentSchedule = new PaymentSchedule(1,
                LocalDate.parse("2023-08-18"),
                BigDecimal.valueOf(25444.27),
                BigDecimal.valueOf(24735.94),
                BigDecimal.valueOf(708.33),
                BigDecimal.valueOf(75264.06));

        PaymentSchedule paymentSchedule1 = new PaymentSchedule(2,
                LocalDate.parse("2023-09-18"),
                BigDecimal.valueOf(24911.15),
                BigDecimal.valueOf(24735.94),
                BigDecimal.valueOf(533.12),
                BigDecimal.valueOf(50352.91));

        PaymentSchedule paymentSchedule2 = new PaymentSchedule(3,
                LocalDate.parse("2023-10-18"),
                BigDecimal.valueOf(25444.27),
                BigDecimal.valueOf(25087.60),
                BigDecimal.valueOf(356.67),
                BigDecimal.valueOf(25265.31));

        PaymentSchedule paymentSchedule3 = new PaymentSchedule(4,
                LocalDate.parse("2023-11-18"),
                BigDecimal.valueOf(25444.27),
                BigDecimal.valueOf(25265.31),
                BigDecimal.valueOf(178.96),
                BigDecimal.valueOf(0.00));

        List<PaymentSchedule> paymentScheduleList = new ArrayList<>(Arrays.asList(paymentSchedule, paymentSchedule1, paymentSchedule2, paymentSchedule3));

        Credit expectedCredit = new Credit(
                4,
                BigDecimal.valueOf(25444.27),
                BigDecimal.valueOf(8.5),
                BigDecimal.valueOf(8.42),
                paymentScheduleList,
                true,
                true);

        CreditDTO creditDTO = new CreditDTO(
                BigDecimal.valueOf(100000),
                4,
                BigDecimal.valueOf(25444.27),
                BigDecimal.valueOf(8.5),
                BigDecimal.valueOf(8.42),
                true,
                true,
                paymentScheduleList);

        Credit actualCredit = creditMapper.toCreditFromCreditDTO(creditDTO);

        Assertions.assertEquals(expectedCredit, actualCredit);
    }
}
