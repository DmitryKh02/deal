package ru.neoflex.deal.service;

import ru.neoflex.deal.dto.request.FinishRegistrationRequestDTO;
import ru.neoflex.deal.dto.request.LoanApplicationRequestDTO;
import ru.neoflex.deal.entity.Application;
import ru.neoflex.deal.entity.Client;

public interface ClientService {
    Client createAndSaveClient(LoanApplicationRequestDTO loanApplicationRequestDTO);
    void updateClient(Application application, FinishRegistrationRequestDTO finishRegistrationRequestDTO);
}
