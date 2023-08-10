package ru.neoflex.deal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import ru.neoflex.deal.dto.request.FinishRegistrationRequestDTO;
import ru.neoflex.deal.dto.request.LoanApplicationRequestDTO;
import ru.neoflex.deal.entity.Client;

@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    @Mapping(target = "clientId", ignore = true)
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "materialStatus", ignore = true)
    @Mapping(target = "dependentAmount", ignore = true)
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "passport", ignore = true)
    @Mapping(target = "employment", ignore = true)
    Client toClient(LoanApplicationRequestDTO dto);

    @Mapping(target = "clientId", ignore = true)
    Client updateClientFromDTO(@MappingTarget Client client, FinishRegistrationRequestDTO finishRegistrationRequestDTO);
}