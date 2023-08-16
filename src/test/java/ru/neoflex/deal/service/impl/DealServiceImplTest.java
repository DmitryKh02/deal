package ru.neoflex.deal.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import ru.neoflex.deal.dto.LoanOfferDTO;
import ru.neoflex.deal.dto.request.EmploymentDTO;
import ru.neoflex.deal.dto.request.FinishRegistrationRequestDTO;
import ru.neoflex.deal.dto.request.LoanApplicationRequestDTO;
import ru.neoflex.deal.entity.Application;
import ru.neoflex.deal.entity.Client;
import ru.neoflex.deal.enums.EmploymentStatus;
import ru.neoflex.deal.enums.Gender;
import ru.neoflex.deal.enums.MaterialStatus;
import ru.neoflex.deal.enums.WorkPosition;
import ru.neoflex.deal.external.ConveyorServiceDeal;
import ru.neoflex.deal.mapper.ClientMapper;
import ru.neoflex.deal.mapper.ScoringMapper;
import ru.neoflex.deal.repository.ApplicationRepository;
import ru.neoflex.deal.repository.ClientRepository;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(value = MockitoExtension.class)
class DealServiceImplTest {
    @Mock
    private Logger log;
    @Mock
    ApplicationRepository applicationRepository;
    @Mock
    ClientRepository clientRepository;
    @Mock
    ClientMapper clientMapper;
    @Mock
    ScoringMapper scoringMapper;
    @Mock
    ConveyorServiceDeal conveyorServiceDeal;

    @InjectMocks
    DealServiceImpl dealService;

    @Test
    void DealService_CalculationPossibleCreditConditions_ReturnListLoanOffersDTO() {
        LoanApplicationRequestDTO loanApplicationRequestDTO = new LoanApplicationRequestDTO(
                BigDecimal.valueOf(150000),
                24,
                "Ivan",
                "Petrov",
                null,
                "ivan@example.com",
                LocalDate.of(1990, 8, 10),
                "5678",
                "123456"
        );

        Long applicationId = 5L;
        List<LoanOfferDTO> offerDTOList = new ArrayList<>();

        LoanOfferDTO loanOffer1 = new LoanOfferDTO(0L, BigDecimal.valueOf(150000), BigDecimal.valueOf(151500.00),
                24, BigDecimal.valueOf(6547.17), BigDecimal.valueOf(4.5), true, true);

        LoanOfferDTO loanOffer2 = new LoanOfferDTO(1L, BigDecimal.valueOf(150000), BigDecimal.valueOf(151500.00),
                24, BigDecimal.valueOf(6614.35), BigDecimal.valueOf(5.5), true, false);

        LoanOfferDTO loanOffer3 = new LoanOfferDTO(2L, BigDecimal.valueOf(150000), BigDecimal.valueOf(150000),
                24, BigDecimal.valueOf(6749.94), BigDecimal.valueOf(7.5), false, true);

        LoanOfferDTO loanOffer4 = new LoanOfferDTO(3L, BigDecimal.valueOf(150000), BigDecimal.valueOf(150000),
                24, BigDecimal.valueOf(6818.35), BigDecimal.valueOf(8.5), false, false);

        offerDTOList.add(0, loanOffer1);
        offerDTOList.add(1, loanOffer2);
        offerDTOList.add(2, loanOffer3);
        offerDTOList.add(3, loanOffer4);

        LoanOfferDTO loanOfferResult1 = new LoanOfferDTO(applicationId, BigDecimal.valueOf(150000), BigDecimal.valueOf(151500.00),
                24, BigDecimal.valueOf(6547.17), BigDecimal.valueOf(4.5), true, true);

        LoanOfferDTO loanOfferResult2 = new LoanOfferDTO(applicationId, BigDecimal.valueOf(150000), BigDecimal.valueOf(151500.00),
                24, BigDecimal.valueOf(6614.35), BigDecimal.valueOf(5.5), true, false);

        LoanOfferDTO loanOfferResult3 = new LoanOfferDTO(applicationId, BigDecimal.valueOf(150000), BigDecimal.valueOf(150000),
                24, BigDecimal.valueOf(6749.94), BigDecimal.valueOf(7.5), false, true);

        LoanOfferDTO loanOfferResult4 = new LoanOfferDTO(applicationId, BigDecimal.valueOf(150000), BigDecimal.valueOf(150000),
                24, BigDecimal.valueOf(6818.35), BigDecimal.valueOf(8.5), false, false);

        List<LoanOfferDTO> expectedLoanOffers = new ArrayList<>();

        expectedLoanOffers.add(0, loanOfferResult1);
        expectedLoanOffers.add(1, loanOfferResult2);
        expectedLoanOffers.add(2, loanOfferResult3);
        expectedLoanOffers.add(3, loanOfferResult4);

        when(conveyorServiceDeal.calculationPossibleCreditConditions(loanApplicationRequestDTO)).thenReturn(ResponseEntity.ok().body(offerDTOList));

        when(applicationRepository.save(Mockito.any(Application.class))).thenAnswer((Answer<Application>) invocation -> {
            Application savedApplication = invocation.getArgument(0);
            Field idField = Application.class.getDeclaredField("applicationId");
            idField.setAccessible(true);
            idField.set(savedApplication, applicationId);
            return savedApplication;
        });

        Assertions.assertEquals(expectedLoanOffers, dealService.calculationPossibleCreditConditions(loanApplicationRequestDTO));
    }

    @Test
    void DealService_SavingCreditApplication_CheckSaveAndSetAppliedOffer() {

        // Создание LoanOfferDTO для теста
        Long applicationId = 5L;
        LoanOfferDTO loanOfferDTO = new LoanOfferDTO(applicationId, BigDecimal.valueOf(150000), BigDecimal.valueOf(150000),
                24, BigDecimal.valueOf(6749.94), BigDecimal.valueOf(7.5), false, true);

        Application application = new Application();
        when(applicationRepository.getReferenceById(applicationId)).thenReturn(application);

        dealService.savingCreditApplication(loanOfferDTO);

        verify(applicationRepository).getReferenceById(applicationId);
        verify(applicationRepository).save(application);

        verify(applicationRepository).save(argThat(argument -> argument.getAppliedOffer().equals(application.getAppliedOffer())));
    }

    @Test
    void DealService_FinishRegistration_CheckSaveAndSetAppliedOffer() {
        // Создание FinishRegistrationRequestDTO и Application для теста
        FinishRegistrationRequestDTO finishRegistrationRequestDTO = new FinishRegistrationRequestDTO(
                Gender.FEMALE,
                MaterialStatus.MARRIED,
                2,
                LocalDate.of(2005, 10, 15),
                "Passport Office XYZ",
                new EmploymentDTO(
                        EmploymentStatus.SELF_EMPLOYED,
                        "123456789012",
                        BigDecimal.valueOf(30000),
                        WorkPosition.MIDDLE_MANAGER,
                        28,
                        12
                ),
                "9876543210"
        );

        Long applicationId = 5L;
        Application application = new Application();
        application.setClient(new Client());

        // Создание моков для возвращаемых значений
        when(applicationRepository.getReferenceById(applicationId)).thenReturn(application);
        when(clientMapper.finishClient(Mockito.any(), Mockito.any())).thenReturn(new Client());

        // Вызов тестируемого метода
        dealService.finishRegistration(finishRegistrationRequestDTO, applicationId);

        // Проверка вызовов методов
        verify(applicationRepository).getReferenceById(applicationId);
        verify(clientRepository).save(Mockito.any());
        verify(scoringMapper).toScoringDataDTO(application);
    }
}