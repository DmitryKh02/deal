package ru.neoflex.deal.service;

import ru.neoflex.deal.dto.LoanOfferDTO;
import ru.neoflex.deal.dto.request.FinishRegistrationRequestDTO;
import ru.neoflex.deal.dto.request.LoanApplicationRequestDTO;

import java.util.List;

public interface DealService {

    /**
     * Инициализация клиента и его заявки
     *
     * @param loanApplicationRequestDTO основная информация о клиенте
     * @return список предложений кредита
     */
    List<LoanOfferDTO> calculationPossibleCreditConditions(LoanApplicationRequestDTO loanApplicationRequestDTO);

    void savingCreditApplication(LoanOfferDTO loanOfferDTO);

    void finishRegistration(FinishRegistrationRequestDTO finishRegistrationRequestDTO, Long applicationId);
}
