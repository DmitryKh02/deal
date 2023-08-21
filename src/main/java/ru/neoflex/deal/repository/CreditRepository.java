package ru.neoflex.deal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.neoflex.deal.entity.Credit;

public interface CreditRepository extends JpaRepository<Credit, Long> {
}
