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

        Client client = new Client();
        Passport passport = new Passport();
        client.setFirstName("Ivan");
        client.setLastName("Petrov");
        client.setEmail("ivan@example.com");
        client.setBirthdate(LocalDate.of(1990, 8, 10));
        passport.setSeries("5678");
        passport.setNumber("123456");
        client.setPassport(passport);

        Assertions.assertEquals(client, clientMapper.toClient(loanApplicationRequestDTO));
    }

    @Test
    void ClientMapper_FinishClient_ReturnClient(){
        Client client = new Client();
        Passport passport = new Passport();
        client.setClientId(5L);
        client.setFirstName("Ivan");
        client.setLastName("Petrov");
        client.setEmail("ivan@example.com");
        client.setBirthdate(LocalDate.of(1990, 8, 10));
        passport.setSeries("5678");
        passport.setNumber("123456");
        client.setPassport(passport);

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


        Client client2 = new Client();
        Passport passport2 = new Passport();
        client2.setEmployment(new Employment(
                5L,
                EmploymentStatus.BUSINESS_OWNER,
                "2324234",
                BigDecimal.valueOf(50000),
                WorkPosition.TOP_MANAGER,
                24,
                3));

        client2.setClientId(5L);
        client2.setFirstName("Ivan");
        client2.setLastName("Petrov");
        client2.setEmail("ivan@example.com");
        client2.setGender(Gender.MALE);
        client2.setMaterialStatus(MaterialStatus.SINGLE);
        client2.setDependentAmount(0);
        client2.setBirthdate(LocalDate.of(1990, 8, 10));
        passport2.setSeries("5678");
        passport2.setNumber("123456");
        passport2.setIssueBranch("V");
        passport2.setPassportId(5L);
        passport2.setIssueDate(LocalDate.parse("2002-04-23"));
        client2.setPassport(passport2);
        client2.setAccount("12334324");

        Assertions.assertEquals(client2,clientMapper.finishClient(client,finishRegistrationRequestDTO));
    }
}
