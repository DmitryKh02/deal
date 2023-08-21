package ru.neoflex.deal.mapper;

import ru.neoflex.deal.dto.response.CreditDTO;
import ru.neoflex.deal.entity.Credit;

public interface CreditMapper {
    Credit toCreditFromCreditDTO(CreditDTO creditDTO);
}
