package ru.neoflex.deal.entity.jsonb;

import ru.neoflex.deal.enums.ApplicationStatus;
import ru.neoflex.deal.enums.ChangeType;

import java.time.LocalDateTime;

public record ApplicationStatusHistoryDTO(
        ApplicationStatus status,
        LocalDateTime timestamp,
        ChangeType changeType
) {
}
