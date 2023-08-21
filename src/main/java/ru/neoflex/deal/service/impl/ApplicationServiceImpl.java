package ru.neoflex.deal.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.neoflex.deal.dto.LoanOfferDTO;
import ru.neoflex.deal.entity.Application;
import ru.neoflex.deal.entity.Client;
import ru.neoflex.deal.entity.Credit;
import ru.neoflex.deal.entity.jsonb.ApplicationStatusHistoryDTO;
import ru.neoflex.deal.enums.ApplicationStatus;
import ru.neoflex.deal.enums.ChangeType;
import ru.neoflex.deal.repository.ApplicationRepository;
import ru.neoflex.deal.service.ApplicationService;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;

    /**
     * Создание и сохранение заявки
     * <p>
     * @param client сущность клиента
     * @return получение порядкового номера (id) заявки
     */
    @Override
    public Application createAndSaveApplication(Client client) {
        log.debug("ApplicationServiceImpl.createAndSaveApplication - client {}", client);


        Application application = new Application(client,
                LocalDateTime.now().withNano(0),
                "SES_CODE");
        //createAddAndSetStatusHistory(application, ApplicationStatus.PREAPPROVAL);

        log.debug("ApplicationServiceImpl.createAndSaveApplication - application {}", application);

        applicationRepository.save(application);

        log.debug("ApplicationServiceImpl.createAndSaveApplication - application {}", application);
        return application;
    }

    /**
     * Получение заявки по её id
     * <p>
     * @param applicationId id заявки
     * @return заявка
     */
    @Override
    public Application getApplication(Long applicationId) throws EntityNotFoundException {
        log.debug("ApplicationServiceImpl.getApplication - ApplicationId {}", applicationId);

        Application application = applicationRepository.getReferenceById(applicationId);

        log.debug("ApplicationServiceImpl.getApplication - Application {}", application);
        return application;
    }


    /**
     * Обновление порядкового номера (id) заявки в предложениях кредита
     * <p>
     * @param offers        предложения кредита
     * @param applicationId порядковый номер (id) заявки
     */
    @Override
    public void updateListLoanOfferDTOByApplicationId(List<LoanOfferDTO> offers, Long applicationId) {
        log.debug("ApplicationServiceImpl.updateListLoanOfferDTOByApplicationId - List<LoanOfferDTO> {}, ApplicationId {}", offers, applicationId);

        for (LoanOfferDTO offer : offers) {
            offer.setApplicationId(applicationId);
        }

        log.debug("ApplicationServiceImpl.updateListLoanOfferDTOByApplicationId - List<LoanOfferDTO> {}", offers);
    }

    /**
     * Обновление заявки из LoanOfferDTO
     * <p>
     * @param loanOfferDTO выбранное кредитное предложение
     */
    @Override
    public void updateApplicationByLoanOfferDTO(LoanOfferDTO loanOfferDTO) throws EntityNotFoundException{
        log.debug("ApplicationServiceImpl.updateApplicationByLoanOfferDTO - loanOfferDTO {}", loanOfferDTO);

        Application application = applicationRepository.getReferenceById(loanOfferDTO.getApplicationId());

        //createAddAndSetStatusHistory(application, ApplicationStatus.APPROVED);
        application.setAppliedOffer(loanOfferDTO);
        application.setSignDate(LocalDateTime.now());
        applicationRepository.save(application);

        log.debug("ApplicationServiceImpl.updateApplicationByLoanOfferDTO - application {}", application);
    }

    @Override
    public void setCreditAndSaveApplication(Application application, Credit credit) {
        log.debug("ApplicationServiceImpl.setCreditAndSave - application {}, credit {}", application, credit);

        //createAddAndSetStatusHistory(application, ApplicationStatus.CREDIT_ISSUED);
        application.setCredit(credit);
        applicationRepository.save(application);

        log.debug("ApplicationServiceImpl.setCreditAndSave - application {}", application);
    }


    /**
     * Создание и добавление статуса истории заявки
     * <p>
     *
     * @param application заявка
     * @param status      статус заявки
     */
    private void createAddAndSetStatusHistory(Application application, ApplicationStatus status) {
        log.debug("ApplicationServiceImpl.createAddAndSetStatusHistory - Application {}, ApplicationStatus {}",application, status);

        ApplicationStatusHistoryDTO applicationStatusHistoryDTO = new ApplicationStatusHistoryDTO(
                status,
                LocalDateTime.now().withNano(0),
                ChangeType.AUTOMATIC);
        application.addStatus(applicationStatusHistoryDTO);
        application.setStatus(status);

        log.debug("ApplicationServiceImpl.createAddAndSetStatusHistory - ApplicationStatusHistoryDTO {}", applicationStatusHistoryDTO);
    }

}
