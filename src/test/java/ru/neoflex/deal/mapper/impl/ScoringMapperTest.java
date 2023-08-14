package ru.neoflex.deal.mapper.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.neoflex.deal.dto.LoanOfferDTO;
import ru.neoflex.deal.dto.request.EmploymentDTO;
import ru.neoflex.deal.dto.response.ScoringDataDTO;
import ru.neoflex.deal.entity.Application;
import ru.neoflex.deal.entity.Client;
import ru.neoflex.deal.entity.jsonb.Employment;
import ru.neoflex.deal.entity.jsonb.Passport;
import ru.neoflex.deal.enums.*;
import ru.neoflex.deal.mapper.ScoringMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

class ScoringMapperTest {
    private static ScoringMapper scoringMapper;

    @BeforeAll
    public static void setup(){
        scoringMapper = new ScoringMapperImpl();
    }

    @Test
    void ScoringMapper_ToScoringDataDTO_ReturnScoringDataDTO(){
        ScoringDataDTO validDataDTO = new ScoringDataDTO(
                BigDecimal.valueOf(100000),
                4,
                "Ivan",
                "Petrov",
                null,
                Gender.MALE,
                LocalDate.of(1990, 8, 10),
                "5678",
                "123456",
                LocalDate.parse("2005-03-12"),
                "Local Passport Office",
                MaterialStatus.MARRIED,
                2,
                new EmploymentDTO(
                        EmploymentStatus.SELF_EMPLOYED,
                        "123456789012",
                        BigDecimal.valueOf(30000),
                        WorkPosition.TOP_MANAGER,
                        28,
                        12
                ),
                "9876543210",
                true,
                true
        );

        Client client = new Client();
        Passport passport = new Passport();
        client.setEmployment(new Employment(
                5L,
                EmploymentStatus.SELF_EMPLOYED,
                "123456789012",
                BigDecimal.valueOf(30000),
                WorkPosition.TOP_MANAGER,
                28,
                12));

        client.setClientId(5L);
        client.setFirstName("Ivan");
        client.setLastName("Petrov");
        client.setEmail("ivan@example.com");
        client.setGender(Gender.MALE);
        client.setMaterialStatus(MaterialStatus.MARRIED);
        client.setDependentAmount(2);
        client.setBirthdate(LocalDate.of(1990, 8, 10));
        passport.setSeries("5678");
        passport.setNumber("123456");
        passport.setIssueBranch("Local Passport Office");
        passport.setPassportId(5L);
        passport.setIssueDate(LocalDate.parse("2005-03-12"));
        client.setPassport(passport);
        client.setAccount("9876543210");

        LoanOfferDTO loanOffer = new LoanOfferDTO(12L, BigDecimal.valueOf(100000), BigDecimal.valueOf(100000),
                4, BigDecimal.valueOf(6749.94), BigDecimal.valueOf(7.5), true, true);

        Application application = new Application();
        application.setApplicationId(5L);
        application.setClient(client);
        application.setCredit(null);
        application.setStatus(ApplicationStatus.APPROVED);
        application.setCreationDate(LocalDateTime.now());
        application.setAppliedOffer(loanOffer);
        application.setSignDate(LocalDateTime.now());
        application.setSesCode("SES_CODE");
        application.setStatusHistoryList(null);
        Assertions.assertEquals(validDataDTO, scoringMapper.toScoringDataDTO(application));
    }

}
