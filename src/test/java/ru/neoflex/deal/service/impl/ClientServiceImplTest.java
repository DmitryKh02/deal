package ru.neoflex.deal.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.neoflex.deal.dto.request.LoanApplicationRequestDTO;
import ru.neoflex.deal.entity.Client;
import ru.neoflex.deal.entity.jsonb.Passport;
import ru.neoflex.deal.mapper.ClientMapper;
import ru.neoflex.deal.repository.ClientRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.when;

@ExtendWith(value = MockitoExtension.class)
class ClientServiceImplTest {
    @Mock
    ClientMapper clientMapper;
    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    ClientServiceImpl clientService;

    @Test
    void ClientService_CreateAndSaveClient_ReturnCredit() {
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
        Client inernalClient = new Client();
        Passport passport = new Passport();
        inernalClient.setFirstName("Ivan");
        inernalClient.setLastName("Petrov");
        inernalClient.setEmail("ivan@example.com");
        inernalClient.setBirthdate(LocalDate.of(1990, 8, 10));
        passport.setSeries("5678");
        passport.setNumber("123456");
        inernalClient.setPassport(passport);


        Client expectedClient = new Client();
        passport = new Passport();
        expectedClient.setFirstName("Ivan");
        expectedClient.setLastName("Petrov");
        expectedClient.setEmail("ivan@example.com");
        expectedClient.setBirthdate(LocalDate.of(1990, 8, 10));
        passport.setSeries("5678");
        passport.setNumber("123456");
        expectedClient.setPassport(passport);

        when(clientMapper.toClient(loanApplicationRequestDTO)).thenReturn(inernalClient);
        when(clientRepository.save(inernalClient)).thenReturn(inernalClient);

        Client actualClient = clientService.createAndSaveClient(loanApplicationRequestDTO);

        Assertions.assertEquals(expectedClient,actualClient);

    }
}
