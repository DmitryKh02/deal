package ru.neoflex.deal.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.neoflex.deal.dto.LoanOfferDTO;
import ru.neoflex.deal.dto.Response.ScoringDataDTO;
import ru.neoflex.deal.dto.request.FinishRegistrationRequestDTO;
import ru.neoflex.deal.dto.request.LoanApplicationRequestDTO;
import ru.neoflex.deal.entity.Application;
import ru.neoflex.deal.entity.Client;
import ru.neoflex.deal.entity.jsonb.ApplicationStatusHistoryDTO;
import ru.neoflex.deal.enums.ApplicationStatus;
import ru.neoflex.deal.enums.ChangeType;
import ru.neoflex.deal.external.ConveyorServiceDeal;
import ru.neoflex.deal.mapper.ApplicationMapper;
import ru.neoflex.deal.mapper.ClientMapper;
import ru.neoflex.deal.mapper.ScoringMapper;
import ru.neoflex.deal.repository.ApplicationRepository;
import ru.neoflex.deal.repository.ClientRepository;
import ru.neoflex.deal.service.DealService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {
    ClientMapper clientMapper;
    ApplicationMapper applicationMapper;
    ScoringMapper scoringMapper;

    ClientRepository clientRepository;
    ApplicationRepository applicationRepository;

    ConveyorServiceDeal conveyorServiceDeal;


    @Override
    public List<LoanOfferDTO> calculationPossibleCreditConditions(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        log.trace("DealServiceImpl.calculationPossibleCreditConditions - internal data: loanApplicationRequestDTO {}", loanApplicationRequestDTO);

        //TODO посмотреть все что связанно с маппером
        Client client = mapAndSaveClient(loanApplicationRequestDTO);

        Long applicationId = createAndSaveApplication(client);

        List<LoanOfferDTO> requestList = conveyorServiceDeal.calculationPossibleCreditConditions(loanApplicationRequestDTO).getBody();

        List<LoanOfferDTO> responseList = null;

        if (requestList != null) {
             responseList= updateApplicationId(requestList, applicationId);
        }

        log.trace("DealServiceImpl.calculationPossibleCreditConditions - list of loanOfferDTO: {}", responseList);
        return responseList;
    }

    @Override
    public void savingCreditApplication(LoanOfferDTO loanOfferDTO) {
        // TODO document why this method is empty
        log.trace("DealServiceImpl.savingCreditApplication - internal data: LoanOfferDTO {}", loanOfferDTO);

        Application application = applicationRepository.getReferenceById(loanOfferDTO.applicationId());
        application.addStatus(createStatus(ApplicationStatus.APPROVED));
        application = applicationMapper.INSTANCE.updateApplicationFromDTO(application, loanOfferDTO);

        log.trace("DealServiceImpl.savingCreditApplication - application {}", application);
        applicationRepository.save(application);

        log.trace("DealServiceImpl.savingCreditApplication - End of work and no such exception");
    }

    @Override
    public void finishRegistration(FinishRegistrationRequestDTO finishRegistrationRequestDTO, Long applicationId) {
        // TODO document why this method is empty
        log.trace("DealServiceImpl.finishRegistration - internal data: FinishRegistrationRequestDTO {}", finishRegistrationRequestDTO);

        Application application = applicationRepository.getReferenceById(applicationId);
        Client client = application.getClient();

        ScoringDataDTO scoringDataDTO = scoringMapper.INSTANCE.toScoringDataDTO(finishRegistrationRequestDTO, client);
        log.trace("DealServiceImpl.finishRegistration - ScoringDataDTO {}", scoringDataDTO);

        conveyorServiceDeal.calculationCreditParameters(scoringDataDTO);

        log.trace("DealServiceImpl.finishRegistration - End of work and no such exception");
    }

    private Client mapAndSaveClient(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        log.debug("DealServiceImpl.mapAndSaveClient - internal data: loanApplicationRequestDTO {}", loanApplicationRequestDTO);

        Client client = clientMapper.INSTANCE.toClient(loanApplicationRequestDTO);

        log.debug("Mapped client: {}", client);

        clientRepository.save(client);

        log.debug("DealServiceImpl.mapAndSaveClient - client {}", client);

        return client;
    }

    private Long createAndSaveApplication(Client client) {
        log.debug("DealServiceImpl.createAndSaveApplication - client {}", client);

        Application application = new Application(client,
                ApplicationStatus.PREAPPROVAL,
                LocalDateTime.now(),
                "SES_CODE",
                createStatus(ApplicationStatus.PREAPPROVAL));

        log.debug("DealServiceImpl.createAndSaveApplication - application {}", application);
        applicationRepository.save(application);
        Long applicationId = application.getApplicationId();

        log.debug("DealServiceImpl.createAndSaveApplication - applicationId {}", applicationId);

        return applicationId;
    }

    private ApplicationStatusHistoryDTO createStatus(ApplicationStatus status) {
        log.debug("DealServiceImpl.createAndSaveApplication - ApplicationStatus {}", status);

        ApplicationStatusHistoryDTO applicationStatusHistoryDTO = new ApplicationStatusHistoryDTO(status, LocalDateTime.now(), ChangeType.AUTOMATIC);

        log.debug("DealServiceImpl.createAndSaveApplication - ApplicationStatusHistoryDTO {}", applicationStatusHistoryDTO);

        return applicationStatusHistoryDTO;
    }

    private static List<LoanOfferDTO> updateApplicationId(List<LoanOfferDTO> offers, Long newApplicationId) {
        List<LoanOfferDTO> updatedOffers = new ArrayList<>();

        for (LoanOfferDTO offer : offers) {
            LoanOfferDTO updatedOffer = new LoanOfferDTO(
                    newApplicationId,
                    offer.requestedAmount(),
                    offer.totalAmount(),
                    offer.term(),
                    offer.monthlyPayment(),
                    offer.rate(),
                    offer.isInsuranceEnabled(),
                    offer.isSalaryClient()
            );

            updatedOffers.add(updatedOffer);
        }

        return updatedOffers;
    }
}
