package ru.neoflex.deal.service.impl;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.neoflex.deal.dto.LoanOfferDTO;
import ru.neoflex.deal.dto.request.FinishRegistrationRequestDTO;
import ru.neoflex.deal.dto.request.LoanApplicationRequestDTO;
import ru.neoflex.deal.dto.response.CreditDTO;
import ru.neoflex.deal.dto.response.ScoringDataDTO;
import ru.neoflex.deal.entity.Application;
import ru.neoflex.deal.entity.Client;
import ru.neoflex.deal.exception.BadRequestToServer;
import ru.neoflex.deal.external.ConveyorServiceDeal;
import ru.neoflex.deal.mapper.ScoringMapper;
import ru.neoflex.deal.service.ApplicationService;
import ru.neoflex.deal.service.ClientService;
import ru.neoflex.deal.service.CreditService;
import ru.neoflex.deal.service.DealService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {
    private final ConveyorServiceDeal conveyorServiceDeal;

    private final ScoringMapper scoringMapper;

    private final ClientService clientService;
    private final ApplicationService applicationService;
    private final CreditService creditService;

    @Override
    public List<LoanOfferDTO> calculationPossibleCreditConditions(LoanApplicationRequestDTO loanApplicationRequestDTO) throws RuntimeException {
        log.trace("DealServiceImpl.calculationPossibleCreditConditions - internal data: loanApplicationRequestDTO {}", loanApplicationRequestDTO);

        List<LoanOfferDTO> responseList;
        try {
            responseList = conveyorServiceDeal.calculationPossibleCreditConditions(loanApplicationRequestDTO).getBody();
        } catch (FeignException ex) {
            log.warn("DealServiceImpl.calculationPossibleCreditConditions - bad request to conveyor server");
            throw new BadRequestToServer(ex.responseBody());
        }

        Client client = clientService.createAndSaveClient(loanApplicationRequestDTO);
        Long applicationId = applicationService.createAndSaveApplication(client).getApplicationId();

        if (responseList != null) {
            applicationService.updateListLoanOfferDTOByApplicationId(responseList, applicationId);
        }

        log.trace("DealServiceImpl.calculationPossibleCreditConditions - list of loanOfferDTO: {}", responseList);
        return responseList;
    }

    @Override
    public void savingCreditApplication(LoanOfferDTO loanOfferDTO) {
        // Метод savingCreditApplication предназначен для обновления заявки пользователя
        log.trace("DealServiceImpl.savingCreditApplication - internal data: LoanOfferDTO {}", loanOfferDTO);

        applicationService.updateApplicationByLoanOfferDTO(loanOfferDTO);

        log.trace("DealServiceImpl.savingCreditApplication - End of work and no such exception");
    }

    @Override
    public void finishRegistration(FinishRegistrationRequestDTO finishRegistrationRequestDTO, Long applicationId) throws RuntimeException {
        // Метод finishRegistration завершает регистрацию
        log.trace("DealServiceImpl.finishRegistration - internal data: FinishRegistrationRequestDTO {}", finishRegistrationRequestDTO);

        Application application = applicationService.getApplication(applicationId);
        clientService.updateClient(application, finishRegistrationRequestDTO);
        ScoringDataDTO scoringDataDTO = scoringMapper.toScoringDataDTO(application);

        log.debug("DealServiceImpl.finishRegistration - ScoringDataDTO {}", scoringDataDTO);

        ResponseEntity<CreditDTO> response;
        try {
            response = conveyorServiceDeal.calculationCreditParameters(scoringDataDTO);
        } catch (FeignException ex) {
            throw new BadRequestToServer(ex.responseBody());
        }

        applicationService.setCreditAndSaveApplication(application, creditService.createAndSaveCredit(response.getBody()));
    }


}
