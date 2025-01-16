package de.muellermarius.denomination_calculator.repository;

import de.muellermarius.denomination_calculator.modell.JpaDenominationResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DenominationRepository extends JpaRepository<JpaDenominationResult, Long> {
    Optional<JpaDenominationResult> findFirstByUserIdOrderByCreatedAtDesc(String userId);

    @Query("SELECT d FROM JpaDenominationResult d WHERE d.userId = :userId ORDER BY d.createdAt DESC limit :limit")
    List<JpaDenominationResult> findFirstTwoByUserIdOrderByCreatedAtDesc(String userId, int limit);
}
