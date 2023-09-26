package ru.neoflex.deal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.neoflex.deal.entity.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query("SELECT c.email FROM Application a JOIN a.client c WHERE a.applicationId = :applicationId")
    String getClientEmailByApplicationId(Long applicationId);
}
