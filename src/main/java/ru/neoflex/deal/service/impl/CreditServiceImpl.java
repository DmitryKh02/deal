package ru.neoflex.deal.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.neoflex.deal.dto.response.CreditDTO;
import ru.neoflex.deal.entity.Credit;
import ru.neoflex.deal.enums.CreditStatus;
import ru.neoflex.deal.mapper.CreditMapper;
import ru.neoflex.deal.repository.CreditRepository;
import ru.neoflex.deal.service.CreditService;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {
    private final CreditRepository creditRepository;
    private final CreditMapper creditMapper;

    /**
     * Создание и сохранение кредита из CreditDTO
     *
     * @param creditDTO данные по кредиту
     * @return кредит
     */
    @Override
    public Credit createAndSaveCredit(CreditDTO creditDTO) {
        log.debug("CreditServiceImpl.createAndSaveCredit - internal data: CreditDTO {}", creditDTO);

        Credit credit = creditMapper.toCreditFromCreditDTO(creditDTO);
        credit.setCreditStatus(CreditStatus.CALCULATED);
        creditRepository.save(credit);

        log.debug("CreditServiceImpl.createAndSaveCredit - Credit {}", credit);
        return credit;
    }
}
