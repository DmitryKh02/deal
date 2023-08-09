package ru.neoflex.deal.entity.jsonb;

import ru.neoflex.deal.enums.ChangeType;

import java.time.LocalDateTime;

public record StatusHistory(
        String status,
        LocalDateTime timestamp,
        ChangeType changeType
) {
}
