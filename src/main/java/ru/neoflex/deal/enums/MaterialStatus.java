package ru.neoflex.deal.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MaterialStatus {
    SINGLE("Single"),
    DIVORCED("Divorced"),
    MARRIED("Married");

    private final String value;
}
