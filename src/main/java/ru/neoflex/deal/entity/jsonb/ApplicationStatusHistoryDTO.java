package ru.neoflex.deal.entity.jsonb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.neoflex.deal.enums.ApplicationStatus;
import ru.neoflex.deal.enums.ChangeType;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Getter
@Setter
public class ApplicationStatusHistoryDTO{
        private ApplicationStatus status;
        private LocalDateTime timestamp;
        private ChangeType changeType;
}
