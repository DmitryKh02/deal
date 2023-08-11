package ru.neoflex.deal.entity.jsonb;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Passport{
        Long passportId;

        String series;

        String number;

        String issueBranch;

        LocalDate issueDate;
}
