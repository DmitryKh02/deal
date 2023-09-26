package ru.neoflex.deal.dto.response;


import ru.neoflex.deal.dto.LoanOfferDTO;
import ru.neoflex.deal.entity.Client;
import ru.neoflex.deal.entity.Credit;
import ru.neoflex.deal.entity.jsonb.ApplicationStatusHistoryDTO;
import ru.neoflex.deal.enums.ApplicationStatus;


import java.time.LocalDateTime;
import java.util.List;

public record ApplicationDTO(

        Long applicationId,

        Client client,

        Credit credit,

        ApplicationStatus status,

        LocalDateTime creationDate,


        LoanOfferDTO appliedOffer,

        LocalDateTime signDate,

        String sesCode,

        List<ApplicationStatusHistoryDTO> statusHistoryList,

        String statusHistoryString
) {
}
