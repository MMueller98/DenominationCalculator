package de.muellermarius.denomination_calculator.service;

import de.muellermarius.denomination_calculator.domain.Denomination;
import de.muellermarius.denomination_calculator.modell.JpaDenominationPart;
import de.muellermarius.denomination_calculator.modell.JpaDenomination;
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

    public void saveDenominationResult(final Denomination denomination, final String userId) {
        final JpaDenomination jpaDenomination = denominationRepository.save(
                translation.translateToPersistenceEntity(denomination, userId)
        );

        final List<JpaDenominationPart> denominationParts = denomination.denominationParts()
                .stream()
                .map(domainEntity -> translation.translateToPersistenceEntity(domainEntity, jpaDenomination))
                .toList();

        denominationPartRepository.saveAll(denominationParts);
    }

    public Optional<Denomination> getPreviousDenomination(final String userId) {
        return denominationRepository
            .findFirstByUserIdOrderByCreatedAtDesc(userId)
            .map(translation::translateToDomainEntity);
    }

    public List<Denomination> getPreviousDenominations(final String userId, final int limit) {
        return denominationRepository
                .findByUserIdOrderByCreatedAtDesc(userId, limit)
                .stream()
                .map(translation::translateToDomainEntity)
                .toList();
    }
}
