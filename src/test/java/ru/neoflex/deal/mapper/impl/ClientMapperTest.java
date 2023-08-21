package ru.neoflex.deal.mapper.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.neoflex.deal.dto.request.EmploymentDTO;
import ru.neoflex.deal.dto.request.FinishRegistrationRequestDTO;
import ru.neoflex.deal.dto.request.LoanApplicationRequestDTO;
import ru.neoflex.deal.entity.Client;
import ru.neoflex.deal.entity.jsonb.Employment;
import ru.neoflex.deal.entity.jsonb.Passport;
import ru.neoflex.deal.enums.EmploymentStatus;
import ru.neoflex.deal.enums.Gender;
import ru.neoflex.deal.enums.MaterialStatus;
import ru.neoflex.deal.enums.WorkPosition;
import ru.neoflex.deal.mapper.ClientMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

class ClientMapperTest {
    private static ClientMapper clientMapper;

    @BeforeAll
    public static void setup(){
        clientMapper= new ClientMapperImpl();
    }
    @Test
    void ClientMapper_ToClientFromLoanApplicationRequestDTO_ReturnClient() {
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

        Client expectedClient = new Client();
        Passport passport = new Passport();
        expectedClient.setFirstName("Ivan");
        expectedClient.setLastName("Petrov");
        expectedClient.setEmail("ivan@example.com");
        expectedClient.setBirthdate(LocalDate.of(1990, 8, 10));
        passport.setSeries("5678");
        passport.setNumber("123456");
        expectedClient.setPassport(passport);

        Client actualClient = clientMapper.toClient(loanApplicationRequestDTO);

        Assertions.assertEquals(expectedClient, actualClient);
    }

    @Test
    void ClientMapper_FinishClient_ReturnClient(){
        Client internalDateClient = getClient();

        FinishRegistrationRequestDTO finishRegistrationRequestDTO = new FinishRegistrationRequestDTO(
                Gender.MALE,
                MaterialStatus.SINGLE,
                0,
                LocalDate.parse("2002-04-23"),
                "V",
                new EmploymentDTO(
                        EmploymentStatus.BUSINESS_OWNER,
                        "2324234",
                        BigDecimal.valueOf(50000),
                        WorkPosition.TOP_MANAGER,
                        24,
                        3),
                "12334324");


        Client expectedClient = getClient();
        expectedClient.setEmployment(new Employment(
                5L,
                EmploymentStatus.BUSINESS_OWNER,
                "2324234",
                BigDecimal.valueOf(50000),
                WorkPosition.TOP_MANAGER,
                24,
                3));


        expectedClient.setGender(Gender.MALE);
        expectedClient.setMaterialStatus(MaterialStatus.SINGLE);
        expectedClient.setDependentAmount(0);
        expectedClient.getPassport().setIssueBranch("V");
        expectedClient.getPassport().setPassportId(5L);
        expectedClient.getPassport().setIssueDate(LocalDate.parse("2002-04-23"));
        expectedClient.setAccount("12334324");

        Client actualClient = clientMapper.finishClient(internalDateClient,finishRegistrationRequestDTO);
        Assertions.assertEquals(expectedClient,actualClient);
    }

    private static Client getClient() {
        Client internalDateClient = new Client();
        Passport internalDateClientPassport = new Passport();
        internalDateClient.setClientId(5L);
        internalDateClient.setFirstName("Ivan");
        internalDateClient.setLastName("Petrov");
        internalDateClient.setEmail("ivan@example.com");
        internalDateClient.setBirthdate(LocalDate.of(1990, 8, 10));
        internalDateClientPassport.setSeries("5678");
        internalDateClientPassport.setNumber("123456");
        internalDateClient.setPassport(internalDateClientPassport);
        return internalDateClient;
    }
}
