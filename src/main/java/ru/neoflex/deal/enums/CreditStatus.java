package ru.neoflex.deal.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CreditStatus {
    CALCULATED("Calculated"),
    ISSUED("Issued");

    private final String status;
}
