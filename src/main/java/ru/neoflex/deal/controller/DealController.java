package ru.neoflex.deal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.deal.configuration.kafka.KafkaTopic;
import ru.neoflex.deal.dto.LoanOfferDTO;
import ru.neoflex.deal.dto.request.FinishRegistrationRequestDTO;
import ru.neoflex.deal.dto.request.LoanApplicationRequestDTO;
import ru.neoflex.deal.entity.Application;
import ru.neoflex.deal.service.DealService;
import ru.neoflex.deal.service.KafkaMessageService;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/deal")
@RequiredArgsConstructor
@Tag(name = "Сделка")
public class DealController {
    private final DealService dealService;
    private final KafkaTopic kafkaTopic;
    private final KafkaMessageService kafkaMessageService;

    @PostMapping(value = "/application")
    @Operation(summary = "Расчёт возможных условий кредита", description = "Получение списка из 4 возможных кредитных предложений на основе входных данных")
    public ResponseEntity<List<LoanOfferDTO>> calculationPossibleCreditConditions(@RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO) {
        log.trace("/deal/application Request: LoanApplicationRequestDTO  {} ", loanApplicationRequestDTO);

        List<LoanOfferDTO> loanOfferDTOS = dealService.calculationPossibleCreditConditions(loanApplicationRequestDTO);

        log.trace("/deal/application Response: LoanOfferDTO {} ", loanOfferDTOS);

        return ResponseEntity.status(HttpStatus.OK).body(loanOfferDTOS);
    }

    @PutMapping(value = "/offer")
    @Operation(summary = "Выбор одного из предложений", description = "Получение итогового предложения и занесение его в заявку")
    public void calculationCreditParameters(@RequestBody LoanOfferDTO loanOfferDTO) {
        log.trace("/deal/offer Request: LoanOfferDTO {} ", loanOfferDTO);

        dealService.savingCreditApplication(loanOfferDTO);

        kafkaMessageService.send(kafkaTopic.finishRegistration(), loanOfferDTO.getApplicationId());
    }

    @PutMapping(value = "/calculate/{applicationId}")
    @Operation(summary = "Завершение регистрации + полный подсчёт кредита", description = "Создание сущности кредита и занесение в базу данных")
    public void finishRegistration(@RequestBody FinishRegistrationRequestDTO finishRegistrationRequestDTO, @PathVariable Long applicationId) {
        log.trace("/deal/offer Request: FinishRegistrationRequestDTO {}, applicationId {}", finishRegistrationRequestDTO, applicationId);

        dealService.finishRegistration(finishRegistrationRequestDTO,applicationId);

        kafkaMessageService.send(kafkaTopic.createDocument(), applicationId);
    }

    @PostMapping(value = "/document/{applicationId}/send")
    @Operation(summary = "Запрос на отправку документов", description = "")
    public void sendDocumentByApplicationId(@PathVariable Long applicationId) {
        log.trace("/document/{applicationId}/send Request: ApplicationId  {} ", applicationId);

        kafkaMessageService.send(kafkaTopic.sendDocuments(), applicationId);
    }

    @PostMapping(value = "/document/{applicationId}/sign")
    @Operation(summary = "Запрос на подписание документов", description = "")
    public void signDocumentByApplicationId(@PathVariable Long applicationId) {
        log.trace("/document/{applicationId}/sign Request: ApplicationId  {} ", applicationId);

        kafkaMessageService.send(kafkaTopic.sendSes() ,applicationId);
    }
    @PostMapping(value = "/document/{applicationId}/code")
    @Operation(summary = "Подписание документов", description = "")
    public void codeDocumentByApplicationId(@PathVariable Long applicationId) {
        log.trace("/document/{applicationId}/code Request: ApplicationId  {} ", applicationId);

        kafkaMessageService.send(kafkaTopic.creditIssued(), applicationId);
    }

    @GetMapping(value = "/admin/application/{applicationId}")
    @Operation()
    public ResponseEntity<Application> getApplicationById(@PathVariable Long applicationId){
        log.trace("/admin/application/{applicationId} Request: ApplicationId  {} ", applicationId);

        Application application = dealService.getApplicationById(applicationId);

        log.trace("/admin/application/{applicationId} Response: Application {}", application);

        return ResponseEntity.status(HttpStatus.OK).body(application);
    }

    @GetMapping(value = "/admin/application/")
    @Operation()
    public ResponseEntity<List<Application>> getAllApplications(){
        log.trace("/admin/application/{applicationId}");

        List<Application> application = dealService.getAllApplications();

        log.trace("/admin/application/ Response: List<Application> {}", application);

        return ResponseEntity.status(HttpStatus.OK).body(application);
    }
}
