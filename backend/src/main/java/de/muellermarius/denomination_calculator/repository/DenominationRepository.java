package de.muellermarius.denomination_calculator.repository;

import de.muellermarius.denomination_calculator.modell.JpaDenomination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DenominationRepository extends JpaRepository<JpaDenomination, Long> {
    Optional<JpaDenomination> findFirstByUserIdOrderByCreatedAtDesc(String userId);

    @Query("SELECT d FROM JpaDenomination d WHERE d.userId = :userId ORDER BY d.createdAt DESC limit :limit")
    List<JpaDenomination> findByUserIdOrderByCreatedAtDesc(String userId, int limit);
}
