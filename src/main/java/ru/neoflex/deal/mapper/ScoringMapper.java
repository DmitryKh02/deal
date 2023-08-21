package ru.neoflex.deal.mapper;

import ru.neoflex.deal.dto.response.ScoringDataDTO;
import ru.neoflex.deal.entity.Application;


public interface ScoringMapper {
    ScoringDataDTO toScoringDataDTO(Application application);
}
