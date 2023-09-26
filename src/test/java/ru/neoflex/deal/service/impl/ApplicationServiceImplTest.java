package ru.neoflex.deal.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.neoflex.deal.entity.Application;
import ru.neoflex.deal.entity.Client;
import ru.neoflex.deal.entity.jsonb.ApplicationStatusHistoryDTO;
import ru.neoflex.deal.entity.jsonb.Passport;
import ru.neoflex.deal.enums.ApplicationStatus;
import ru.neoflex.deal.enums.ChangeType;
import ru.neoflex.deal.repository.ApplicationRepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

@ExtendWith(value = MockitoExtension.class)
class ApplicationServiceImplTest {
    @Mock
    ApplicationRepository applicationRepository;

    @InjectMocks
    ApplicationServiceImpl applicationService;

    @Test
    void ApplicationService_CreateAndSaveApplication_ReturnApplicationId() {
        Client client = new Client();
        Passport passport = new Passport();
        client.setFirstName("Ivan");
        client.setLastName("Petrov");
        client.setEmail("ivan@example.com");
        client.setBirthdate(LocalDate.of(1990, 8, 10));
        passport.setSeries("5678");
        passport.setNumber("123456");
        client.setPassport(passport);

        Application expectedApplication = new Application(client,
                LocalDateTime.now().withSecond(0).withNano(0),
                "SES_CODE");
        expectedApplication.setStatus(ApplicationStatus.PREAPPROVAL);
        expectedApplication.addStatus(
                new ApplicationStatusHistoryDTO(
                        ApplicationStatus.PREAPPROVAL,
                        Timestamp.valueOf(LocalDateTime.now().withSecond(0).withNano(0)),
                        ChangeType.AUTOMATIC));


        expectedApplication.setStatusHistoryString("{\"status\":\"PREAPPROVAL\",\"timestamp\":" + Timestamp.valueOf(LocalDateTime.now().withSecond(0).withNano(0)).getTime() + ",\"changeType\":\"AUTOMATIC\"};");
        when(applicationRepository.save(Mockito.any(Application.class))).thenReturn(expectedApplication);

        Application actualApplication = applicationService.createAndSaveApplication(client);
        Assertions.assertEquals(expectedApplication,actualApplication);
    }

}
