package ru.neoflex.deal.service;

import ru.neoflex.deal.dto.LoanOfferDTO;
import ru.neoflex.deal.entity.Application;
import ru.neoflex.deal.entity.Client;
import ru.neoflex.deal.entity.Credit;

import java.util.List;

public interface ApplicationService {
    Application createAndSaveApplication(Client client);

    void updateListLoanOfferDTOByApplicationId(List<LoanOfferDTO> responseList, Long applicationId);

    void updateApplicationByLoanOfferDTO(LoanOfferDTO loanOfferDTO);

    void setCreditAndSaveApplication(Application application, Credit credit);

    String getClientEmailByApplicationId(Long applicationId);

    Application getApplication(Long applicationId);

    List<Application> getAllApplications();
}
