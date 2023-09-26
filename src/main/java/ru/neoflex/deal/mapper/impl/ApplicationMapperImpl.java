package ru.neoflex.deal.mapper.impl;

import org.springframework.stereotype.Component;
import ru.neoflex.deal.dto.response.ApplicationDTO;
import ru.neoflex.deal.entity.Application;
import ru.neoflex.deal.mapper.ApplicationMapper;

@Component
public class ApplicationMapperImpl implements ApplicationMapper {
    @Override
    public ApplicationDTO toApplicationDTO(Application application) {
        if (application == null)
            return null;

        return new ApplicationDTO(
                application.getApplicationId(),
                application.getClient(),
                application.getCredit(),
                application.getStatus(),
                application.getCreationDate(),
                application.getAppliedOffer(),
                application.getSignDate(),
                application.getSesCode(),
                application.getStatusHistoryList(),
                application.getStatusHistoryString());
    }
}
