package ru.neoflex.deal.service;

import ru.neoflex.deal.dto.response.CreditDTO;
import ru.neoflex.deal.entity.Credit;

public interface CreditService {
    Credit createAndSaveCredit(CreditDTO creditDTO);
}
