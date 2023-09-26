package ru.neoflex.deal.mapper.impl;

import org.springframework.stereotype.Component;
import ru.neoflex.deal.dto.response.CreditDTO;
import ru.neoflex.deal.entity.Credit;
import ru.neoflex.deal.mapper.CreditMapper;

@Component
public class CreditMapperImpl implements CreditMapper {
    @Override
    public Credit toCreditFromCreditDTO(CreditDTO creditDTO) {
        if (creditDTO == null){
            return null;
        }
        return new Credit(
                creditDTO.term(),
                creditDTO.monthlyPayment(),
                creditDTO.rate(),
                creditDTO.psk(),
                creditDTO.paymentScheduleList(),
                creditDTO.isInsuranceEnabled(),
                creditDTO.isSalaryClient()
        );
    }
}
