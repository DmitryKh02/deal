package ru.neoflex.deal.service;

import ru.neoflex.deal.dto.LoanOfferDTO;
import ru.neoflex.deal.dto.request.FinishRegistrationRequestDTO;
import ru.neoflex.deal.dto.request.LoanApplicationRequestDTO;
import ru.neoflex.deal.entity.Application;

import java.util.List;

public interface DealService {

    List<LoanOfferDTO> calculationPossibleCreditConditions(LoanApplicationRequestDTO loanApplicationRequestDTO);

    void savingCreditApplication(LoanOfferDTO loanOfferDTO);

    void finishRegistration(FinishRegistrationRequestDTO finishRegistrationRequestDTO, Long applicationId);

    Application getApplicationById(Long applicationId);

    List<Application> getAllApplications();
}
