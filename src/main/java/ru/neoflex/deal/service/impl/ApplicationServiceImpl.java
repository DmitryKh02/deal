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
import ru.neoflex.deal.service.CreditService;
import ru.neoflex.deal.utils.StringListConverter;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final CreditService creditService;

    /**
     * Создание и сохранение заявки
     * <p>
     *
     * @param client сущность клиента
     * @return получение порядкового номера (id) заявки
     */
    @Override
    public Application createAndSaveApplication(Client client) {
        log.debug("ApplicationServiceImpl.createAndSaveApplication - client {}", client);


        Application application = new Application(client,
                LocalDateTime.now().withSecond(0).withNano(0),
                "SES_CODE");
        createAddAndSetStatusHistory(application, ApplicationStatus.PREAPPROVAL);

        log.debug("ApplicationServiceImpl.createAndSaveApplication - application {}", application);

        applicationRepository.save(application);
        log.debug("ApplicationServiceImpl.createAndSaveApplication - application {}", application);
        return application;
    }


    /**
     * Обновление порядкового номера (id) заявки в предложениях кредита
     * <p>
     *
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
     *
     * @param loanOfferDTO выбранное кредитное предложение
     */
    @Override
    public void updateApplicationByLoanOfferDTO(LoanOfferDTO loanOfferDTO) throws EntityNotFoundException {
        log.debug("ApplicationServiceImpl.updateApplicationByLoanOfferDTO - loanOfferDTO {}", loanOfferDTO);

        Application application = applicationRepository.getReferenceById(loanOfferDTO.getApplicationId());

        createAddAndSetStatusHistory(application, ApplicationStatus.APPROVED);
        application.setAppliedOffer(loanOfferDTO);
        application.setSignDate(LocalDateTime.now());
        applicationRepository.save(application);

        log.debug("ApplicationServiceImpl.updateApplicationByLoanOfferDTO - application {}", application);
    }

    @Override
    public void setCreditAndSaveApplication(Application application, Credit credit) {
        log.debug("ApplicationServiceImpl.setCreditAndSave - application {}, credit {}", application, credit);

        createAddAndSetStatusHistory(application, ApplicationStatus.CC_APPROVED);
        application.setCredit(credit);
        applicationRepository.save(application);

        log.debug("ApplicationServiceImpl.setCreditAndSave - application {}", application);
    }

    @Override
    public String getClientEmailByApplicationId(Long applicationId) throws EntityNotFoundException {
        log.debug("ApplicationServiceImpl.getClientEmailByApplicationId - ApplicationID {}", applicationId);

        String email = applicationRepository.getClientEmailByApplicationId(applicationId);

        if (email == null) {
            throw new EntityNotFoundException("Email not found by application id: " + applicationId);
        }

        log.debug("ApplicationServiceImpl.getClientEmailByApplicationId - Email {}", email);
        return email;
    }

    /**
     * Получение заявки по её id
     * <p>
     *
     * @param applicationId id заявки
     * @return заявка
     */
    @Override
    public Application getApplication(Long applicationId) throws EntityNotFoundException {
        log.debug("ApplicationServiceImpl.getApplication - ApplicationId {}", applicationId);

        Application application = applicationRepository.getReferenceById(applicationId);

        convertStringForList(application);

        if(application.getCredit() != null){
            creditService.convertStringForList(application.getCredit());
        }

        log.debug("ApplicationServiceImpl.getApplication - Application {}", application);
        return application;
    }

    @Override
    public List<Application> getAllApplications() {
        log.debug("ApplicationServiceImpl.getAllApplications");
        List<Application> applicationList = applicationRepository.findAll();

        for (Application application : applicationList) {
            convertStringForList(application);

            if(application.getCredit() != null){
                creditService.convertStringForList(application.getCredit());
            }
        }

        log.debug("ApplicationServiceImpl.getAllApplications - Application List {}",applicationList);
        return applicationList;
    }


    /**
     * Создание и добавление статуса истории заявки
     * <p>
     *
     * @param application заявка
     * @param status      статус заявки
     */
    private void createAddAndSetStatusHistory(Application application, ApplicationStatus status) {
        log.debug("ApplicationServiceImpl.createAddAndSetStatusHistory - Application {}, ApplicationStatus {}", application, status);

        ApplicationStatusHistoryDTO applicationStatusHistoryDTO = new ApplicationStatusHistoryDTO(
                status,
                Timestamp.valueOf(LocalDateTime.now().withSecond(0).withNano(0)),
                ChangeType.AUTOMATIC);

        if (application.getStatusHistoryList() == null) {
            convertStringForList(application);
        }

        application.addStatus(applicationStatusHistoryDTO);
        application.setStatus(status);

        List<Object> objectList = new ArrayList<>(application.getStatusHistoryList());
        application.setStatusHistoryString(StringListConverter.listToString(objectList));

        log.debug("ApplicationServiceImpl.createAddAndSetStatusHistory - ApplicationStatusHistoryDTO {}", applicationStatusHistoryDTO);
    }

    /**
     * Конвертация строки в лист
     * <p>
     *
     * @param application заявка
     */
    private void convertStringForList(Application application) {
        List<Object> originalList = StringListConverter.stringToList(application.getStatusHistoryString(), new ApplicationStatusHistoryDTO());

        List<ApplicationStatusHistoryDTO> convertedList = new ArrayList<>();

        for (Object obj : originalList) {
            convertedList.add((ApplicationStatusHistoryDTO) obj);
        }

        application.setStatusHistoryList(convertedList);
    }

}
