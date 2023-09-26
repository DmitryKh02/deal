package ru.neoflex.deal.entity.jsonb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.neoflex.deal.enums.ApplicationStatus;
import ru.neoflex.deal.enums.ChangeType;

import java.sql.Timestamp;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ApplicationStatusHistoryDTO{
        private ApplicationStatus status;
        private Timestamp timestamp;
        private ChangeType changeType;
}
