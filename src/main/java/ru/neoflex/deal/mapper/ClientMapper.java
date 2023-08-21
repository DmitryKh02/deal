package ru.neoflex.deal.mapper;

import ru.neoflex.deal.dto.request.FinishRegistrationRequestDTO;
import ru.neoflex.deal.dto.request.LoanApplicationRequestDTO;
import ru.neoflex.deal.entity.Client;


public interface ClientMapper {
    Client toClient(LoanApplicationRequestDTO dto);

    Client finishClient(Client client, FinishRegistrationRequestDTO finishRegistrationRequestDTO);
}