package ru.neoflex.deal.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ChangeType {
    AUTOMATIC("Automatic"),
    MANUAL("Manual");

    private final String type;
}
