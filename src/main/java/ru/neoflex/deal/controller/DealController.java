package ru.neoflex.deal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.deal.dto.LoanOfferDTO;
import ru.neoflex.deal.dto.request.FinishRegistrationRequestDTO;
import ru.neoflex.deal.dto.request.LoanApplicationRequestDTO;
import ru.neoflex.deal.service.DealService;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/deal")
@RequiredArgsConstructor
@Tag(name = "Сделка")
public class DealController {
    private final DealService dealService;

    @PostMapping(value = "/application")
    @Operation(summary = "Расчёт возможных условий кредита", description = "?????")
    public ResponseEntity<List<LoanOfferDTO>> calculationPossibleCreditConditions(@RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO) {
        log.trace("/deal/application Request: LoanApplicationRequestDTO  {} ", loanApplicationRequestDTO);

        List<LoanOfferDTO> loanOfferDTOS = dealService.calculationPossibleCreditConditions(loanApplicationRequestDTO);

        log.trace("/deal/application Response: LoanOfferDTO {} ", loanOfferDTOS);

        return ResponseEntity.status(HttpStatus.OK).body(loanOfferDTOS);
    }

    @PutMapping(value = "/offer")
    @Operation(summary = "Выбор одного из предложений", description = "?????")
    public void calculationCreditParameters(@RequestBody LoanOfferDTO loanOfferDTO) {
        log.trace("/deal/offer Request: LoanOfferDTO {} ", loanOfferDTO);

        dealService.savingCreditApplication(loanOfferDTO);

        log.trace("/deal/offer End of work and no such exception");
    }

    @PutMapping(value = "/calculate/{applicationId}")
    @Operation(summary = "Завершение регистрации + полный подсчёт кредита", description = "?????")
    public void calculationCreditParameters(@RequestBody FinishRegistrationRequestDTO finishRegistrationRequestDTO, @PathVariable Long applicationId) {
        log.trace("/deal/offer Request: FinishRegistrationRequestDTO {}, applicationId {}", finishRegistrationRequestDTO, applicationId);

        dealService.finishRegistration(finishRegistrationRequestDTO,applicationId);

        log.trace("/deal/calculate/{} End of work and no such exception", applicationId);
    }
}
