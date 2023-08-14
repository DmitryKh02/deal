package ru.neoflex.deal.mapper.impl;

import org.springframework.stereotype.Component;
import ru.neoflex.deal.dto.request.EmploymentDTO;
import ru.neoflex.deal.dto.request.FinishRegistrationRequestDTO;
import ru.neoflex.deal.dto.request.LoanApplicationRequestDTO;
import ru.neoflex.deal.entity.Client;
import ru.neoflex.deal.entity.jsonb.Employment;
import ru.neoflex.deal.entity.jsonb.Passport;
import ru.neoflex.deal.mapper.ClientMapper;

@Component
public class ClientMapperImpl implements ClientMapper {
    @Override
    public Client toClient(LoanApplicationRequestDTO dto) {
        Client client = new Client();

        client.setFirstName(dto.getFirstName());
        client.setMiddleName(dto.getMiddleName());
        client.setLastName(dto.getLastName());
        client.setEmail(dto.getEmail());
        client.setBirthdate(dto.getBirthdate());

        Passport passport = new Passport();
        passport.setNumber(dto.getPassportNumber());
        passport.setSeries(dto.getPassportSeries());

        client.setPassport(passport);

        return client;
    }

    @Override
    public Client finishClient(Client client, FinishRegistrationRequestDTO finishRegistrationRequestDTO) {
        client.setDependentAmount(finishRegistrationRequestDTO.dependentAmount());
        client.setGender(finishRegistrationRequestDTO.gender());
        client.setMaterialStatus(finishRegistrationRequestDTO.materialStatus());
        client.setAccount(finishRegistrationRequestDTO.account());

        client.getPassport().setPassportId(client.getClientId());
        client.getPassport().setIssueDate(finishRegistrationRequestDTO.passportIssueDate());
        client.getPassport().setIssueBranch(finishRegistrationRequestDTO.passportIssueBranch());

        client.setEmployment(getEmployment(client.getClientId(), finishRegistrationRequestDTO.employmentDTO()));

        return client;
    }

    private Employment getEmployment(Long clientId, EmploymentDTO employmentDTO){
        return new Employment(clientId,
                employmentDTO.employmentStatus(),
                employmentDTO.employerINN(),
                employmentDTO.salary(),
                employmentDTO.position(),
                employmentDTO.workExperienceTotal(),
                employmentDTO.workExperienceCurrent());
    }
}
