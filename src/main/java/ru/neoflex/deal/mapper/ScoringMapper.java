package ru.neoflex.deal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.neoflex.deal.dto.Response.ScoringDataDTO;
import ru.neoflex.deal.dto.request.FinishRegistrationRequestDTO;
import ru.neoflex.deal.entity.Client;

@Mapper
public interface ScoringMapper {
    ScoringMapper INSTANCE = Mappers.getMapper(ScoringMapper.class);

    ScoringDataDTO toScoringDataDTO(FinishRegistrationRequestDTO finishRegistrationRequestDTO, Client client);
}
