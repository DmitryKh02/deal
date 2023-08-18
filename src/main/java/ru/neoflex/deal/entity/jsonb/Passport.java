package ru.neoflex.deal.entity.jsonb;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class Passport{
        private Long passportId;

        private String series;

        private String number;

        private String issueBranch;

        private LocalDate issueDate;
}
