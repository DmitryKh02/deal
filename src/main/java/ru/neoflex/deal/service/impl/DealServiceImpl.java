package ru.neoflex.deal.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.neoflex.deal.dto.LoanOfferDTO;
import ru.neoflex.deal.dto.request.FinishRegistrationRequestDTO;
import ru.neoflex.deal.dto.request.LoanApplicationRequestDTO;
import ru.neoflex.deal.dto.response.ScoringDataDTO;
import ru.neoflex.deal.entity.Application;
import ru.neoflex.deal.entity.Client;
import ru.neoflex.deal.entity.jsonb.ApplicationStatusHistoryDTO;
import ru.neoflex.deal.enums.ApplicationStatus;
import ru.neoflex.deal.enums.ChangeType;
import ru.neoflex.deal.external.ConveyorServiceDeal;
import ru.neoflex.deal.mapper.ClientMapper;
import ru.neoflex.deal.mapper.ScoringMapper;
import ru.neoflex.deal.repository.ApplicationRepository;
import ru.neoflex.deal.repository.ClientRepository;
import ru.neoflex.deal.service.DealService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {
    private final ClientRepository clientRepository;
    private final ApplicationRepository applicationRepository;

    private final ConveyorServiceDeal conveyorServiceDeal;

    private final ClientMapper clientMapper;
    private final ScoringMapper scoringMapper;


    @Override
    public List<LoanOfferDTO> calculationPossibleCreditConditions(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        log.trace("DealServiceImpl.calculationPossibleCreditConditions - internal data: loanApplicationRequestDTO {}", loanApplicationRequestDTO);

        Client client = createAndSaveClient(loanApplicationRequestDTO);

        Long applicationId = createAndSaveApplication(client);

        List<LoanOfferDTO> responseList = null;
        List<LoanOfferDTO> requestList = conveyorServiceDeal.calculationPossibleCreditConditions(loanApplicationRequestDTO).getBody();

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


        Application application = applicationRepository.getReferenceById(loanOfferDTO.getApplicationId());
        //application.addStatus(createStatus(ApplicationStatus.APPROVED));

        application.setAppliedOffer(loanOfferDTO);
        application.setSignDate(LocalDateTime.now());

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
        client = clientMapper.finishClient(client,finishRegistrationRequestDTO);
        clientRepository.save(client);

        ScoringDataDTO scoringDataDTO = scoringMapper.toScoringDataDTO(application);
        log.trace("DealServiceImpl.finishRegistration - ScoringDataDTO {}", scoringDataDTO);

        conveyorServiceDeal.calculationCreditParameters(scoringDataDTO);

        log.trace("DealServiceImpl.finishRegistration - End of work and no such exception");
    }




    private Client createAndSaveClient(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        log.debug("DealServiceImpl.mapAndSaveClient - internal data: loanApplicationRequestDTO {}", loanApplicationRequestDTO);

        Client client = clientMapper.toClient(loanApplicationRequestDTO);

        log.debug("Mapped client: {}", client);

        clientRepository.save(client);

        log.debug("DealServiceImpl.mapAndSaveClient - client {}", client);

        return client;
    }

    private Long createAndSaveApplication(Client client) {
        log.debug("DealServiceImpl.createAndSaveApplication - client {}", client);

        //TODO проблема с сериализацией списка
        Application application = new Application(client,
                ApplicationStatus.PREAPPROVAL,
                LocalDateTime.now(),
                "SES_CODE",
                null);

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

    private List<LoanOfferDTO> updateApplicationId(List<LoanOfferDTO> offers, Long applicationId) {
        log.debug("DealServiceImpl.updateApplicationId - List<LoanOfferDTO> {}, ApplicationId {}", offers, applicationId);

        for (LoanOfferDTO offer : offers) {
            offer.setApplicationId(applicationId);
        }

        log.debug("DealServiceImpl.updateApplicationId - List<LoanOfferDTO> {}", offers);

        return offers;
    }
}
