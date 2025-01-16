package de.muellermarius.denomination_calculator.service;

import de.muellermarius.denomination_calculator.domain.CalculationType;
import de.muellermarius.denomination_calculator.domain.DenominationPart;
import de.muellermarius.denomination_calculator.domain.DenominationResult;
import de.muellermarius.denomination_calculator.modell.JpaDenominationPart;
import de.muellermarius.denomination_calculator.modell.JpaDenominationResult;
import de.muellermarius.denomination_calculator.repository.DenominationPartRepository;
import de.muellermarius.denomination_calculator.repository.DenominationRepository;
import de.muellermarius.denomination_calculator.translation.DenominationJpaToDomainTranslation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DenominationPersistenceService {

    private final DenominationRepository denominationRepository;
    private final DenominationPartRepository denominationPartRepository;

    private final DenominationJpaToDomainTranslation translation;

    public DenominationPersistenceService(
            final DenominationRepository denominationRepository,
            final DenominationPartRepository denominationPartRepository, DenominationJpaToDomainTranslation translation
    ) {
        this.denominationRepository = denominationRepository;
        this.denominationPartRepository = denominationPartRepository;
        this.translation = translation;
    }

    public void saveDenominationResult(final DenominationResult denominationResult, final String userId) {
        final JpaDenominationResult jpaDenominationResult = translation.translateToPersistenceEntity(denominationResult, userId);
        final JpaDenominationResult saved = denominationRepository.save(jpaDenominationResult);

        final List<JpaDenominationPart> denominationParts = denominationResult.denomination()
                .stream()
                .map(domainEntity -> translation.translateToPersistenceEntity(domainEntity, saved, CalculationType.DENOMINATION))
                .toList();
        final List<JpaDenominationPart> differenceParts = denominationResult.difference()
                .map(list -> list.stream()
                    .map(domainEntity -> translation.translateToPersistenceEntity(domainEntity, saved, CalculationType.DIFFERENCE))
                        .toList()
                ).orElse(List.of());

        denominationPartRepository.saveAll(denominationParts);
        denominationPartRepository.saveAll(differenceParts);
    }

    public Optional<DenominationResult> getPreviousDenominationResult(final String userId) {
        return denominationRepository
            .findFirstByUserIdOrderByCreatedAtDesc(userId)
            .map(translation::translateToDomainEntity);
    }

    public List<DenominationResult> getPreviousDenominationResults(final String userId, final int limit) {
        return denominationRepository
                .findFirstTwoByUserIdOrderByCreatedAtDesc(userId, limit)
                .stream()
                .map(translation::translateToDomainEntity)
                .toList();
    }
}
