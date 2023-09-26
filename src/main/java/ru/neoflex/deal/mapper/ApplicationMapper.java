package ru.neoflex.deal.mapper;

import ru.neoflex.deal.dto.response.ApplicationDTO;
import ru.neoflex.deal.entity.Application;

public interface ApplicationMapper {
    ApplicationDTO toApplicationDTO(Application application);
}
