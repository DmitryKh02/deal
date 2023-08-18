package ru.neoflex.deal.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.neoflex.deal.dto.request.FinishRegistrationRequestDTO;
import ru.neoflex.deal.dto.request.LoanApplicationRequestDTO;
import ru.neoflex.deal.entity.Application;
import ru.neoflex.deal.entity.Client;
import ru.neoflex.deal.mapper.ClientMapper;
import ru.neoflex.deal.repository.ClientRepository;
import ru.neoflex.deal.service.ClientService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;


    /**
     * Создание и сохранение клиента
     * <p>
     * @param loanApplicationRequestDTO основная информация
     * @return сущность клиента
     */
    @Override
    public Client createAndSaveClient(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        log.debug("ClientServiceImpl.createAndSaveClient - internal data: loanApplicationRequestDTO {}", loanApplicationRequestDTO);

        Client client = clientMapper.toClient(loanApplicationRequestDTO);

        log.debug("Mapped client: {}", client);

        clientRepository.save(client);

        log.debug("ClientServiceImpl.createAndSaveClient - client {}", client);
        return client;
    }

    /**
     * Обновление данных о клиенте через FinishRegistrationRequestDTO
     * <p>
     * @param application заявка
     * @param finishRegistrationRequestDTO окончательные данные о клиенте
     */
    @Override
    public void updateClient(Application application, FinishRegistrationRequestDTO finishRegistrationRequestDTO){
        log.debug("ClientServiceImpl.updateClient - internal data: Application {}, FinishRegistrationRequestDTO {}", application, finishRegistrationRequestDTO);

        Client client = application.getClient();
        client = clientMapper.finishClient(client, finishRegistrationRequestDTO);
        clientRepository.save(client);

        log.debug("ClientServiceImpl.updateClient - client {}", client);
    }
}
