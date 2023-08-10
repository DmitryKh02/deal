package ru.neoflex.deal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import ru.neoflex.deal.dto.LoanOfferDTO;
import ru.neoflex.deal.entity.Application;

@Mapper
public interface ApplicationMapper {

    ApplicationMapper INSTANCE = Mappers.getMapper(ApplicationMapper.class);

    @Mapping(target = "applicationId", ignore = true)
    Application updateApplicationFromDTO(@MappingTarget Application application, LoanOfferDTO loanOfferDTO);
}
