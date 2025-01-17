package de.muellermarius.denomination_calculator.repository;

import de.muellermarius.denomination_calculator.modell.JpaDenominationPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DenominationPartRepository extends JpaRepository<JpaDenominationPart, Long> {
}
