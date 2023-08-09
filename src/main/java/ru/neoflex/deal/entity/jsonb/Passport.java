package ru.neoflex.deal.entity.jsonb;

import javax.persistence.Id;
import java.time.LocalDate;

public record Passport(
        @Id
        Long passportId,
        String series,
        Integer number,
        String issueBranch,
        LocalDate issueDate
) {
}
