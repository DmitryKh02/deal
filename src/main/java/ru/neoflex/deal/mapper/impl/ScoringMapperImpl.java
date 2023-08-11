package ru.neoflex.deal.mapper.impl;

import org.springframework.stereotype.Component;
import ru.neoflex.deal.dto.request.EmploymentDTO;
import ru.neoflex.deal.dto.response.ScoringDataDTO;
import ru.neoflex.deal.entity.Application;
import ru.neoflex.deal.entity.jsonb.Employment;
import ru.neoflex.deal.mapper.ScoringMapper;

@Component
public class ScoringMapperImpl implements ScoringMapper {
    @Override
    public ScoringDataDTO toScoringDataDTO(Application application) {
        return new ScoringDataDTO(
                application.getAppliedOffer().getTotalAmount(),
                application.getAppliedOffer().getTerm(),
                application.getClient().getFirstName(),
                application.getClient().getLastName(),
                application.getClient().getMiddleName(),
                application.getClient().getGender(),
                application.getClient().getBirthdate(),
                application.getClient().getPassport().getSeries(),
                application.getClient().getPassport().getNumber(),
                application.getClient().getPassport().getIssueDate(),
                application.getClient().getPassport().getIssueBranch(),
                application.getClient().getMaterialStatus(),
                application.getClient().getDependentAmount(),
                getEmploymentDTO(application.getClient().getEmployment()),
                application.getClient().getAccount(),
                application.getAppliedOffer().getIsInsuranceEnabled(),
                application.getAppliedOffer().getIsSalaryClient()
                );
    }

    private EmploymentDTO getEmploymentDTO(Employment employment){
        return  new EmploymentDTO(
                employment.status(),
                employment.employmentINN(),
                employment.salary(),
                employment.position(),
                employment.workExperienceTotal(),
                employment.workExperienceCurrent());
    }
}
