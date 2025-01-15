package de.muellermarius.denomination_calculator.repository;

import de.muellermarius.denomination_calculator.modell.JpaDenominationResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DenominationRepository extends JpaRepository<JpaDenominationResult, Long> {
    Optional<JpaDenominationResult> findByUserId(String userId);
}
