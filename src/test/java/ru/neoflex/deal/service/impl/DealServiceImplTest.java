package ru.neoflex.deal.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.neoflex.deal.external.ConveyorServiceDeal;
import ru.neoflex.deal.mapper.ClientMapper;
import ru.neoflex.deal.mapper.ScoringMapper;
import ru.neoflex.deal.repository.ApplicationRepository;
import ru.neoflex.deal.repository.ClientRepository;

class DealServiceImplTest {
    @MockBean
    ApplicationRepository applicationRepository;
    @MockBean
    ClientRepository clientRepository;
    @MockBean
    ClientMapper clientMapper;
    @MockBean
    ScoringMapper scoringMapper;
    @MockBean
    ConveyorServiceDeal conveyorServiceDeal;

    @InjectMocks
    DealServiceImpl dealService;

    @Test
    void DealService_CreateAndSaveClient_ReturnClient() {

    }
}