package de.muellermarius.denomination_calculator.service;

import de.muellermarius.denomination_calculator.domain.DenominationPart;
import de.muellermarius.denomination_calculator.domain.DenominationResult;
import de.muellermarius.denomination_calculator.modell.JpaDenominationPart;
import de.muellermarius.denomination_calculator.modell.JpaDenominationResult;
import de.muellermarius.denomination_calculator.repository.DenominationPartRepository;
import de.muellermarius.denomination_calculator.repository.DenominationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DenominationPersistenceService {

    private final DenominationRepository denominationRepository;
    private final DenominationPartRepository denominationPartRepository;

    public DenominationPersistenceService(
            final DenominationRepository denominationRepository,
            final DenominationPartRepository denominationPartRepository
    ) {
        this.denominationRepository = denominationRepository;
        this.denominationPartRepository = denominationPartRepository;
    }

    public void saveDenominationResult(final DenominationResult denominationResult, final String userId) {
        final JpaDenominationResult jpaDenominationResult = translateToPersistenceEntity(denominationResult, userId);
        final JpaDenominationResult saved = denominationRepository.save(jpaDenominationResult);

        final List<JpaDenominationPart> jpaDenominationParts = denominationResult.denomination()
                .stream()
                .map(domainEntity -> translateToPersistenceEntity(domainEntity, saved))
                .toList();
        denominationPartRepository.saveAll(jpaDenominationParts);
    }

    public Optional<List<DenominationPart>> getPreviousDenominationCalculation(final String userId) {
        return denominationRepository
                .findByUserId(userId)
                .map(JpaDenominationResult::getDenomination)
                .map(denomination -> denomination.stream().map(this::translateToDomainEntity).toList());
    }

    private JpaDenominationResult translateToPersistenceEntity(final DenominationResult denominationResult, final String userId) {
        return JpaDenominationResult.builder()
                .userId(userId)
                .amount(denominationResult.amount())
                .currency(denominationResult.currency())
                .build();
    }

    private JpaDenominationPart translateToPersistenceEntity(final DenominationPart denominationPart, final JpaDenominationResult denominationResult) {
        return JpaDenominationPart.builder()
                .amount(denominationPart.amount())
                .cashType(denominationPart.cashType())
                .denominationResult(denominationResult)
                .build();
    }

    private DenominationPart translateToDomainEntity(final JpaDenominationPart jpaDenominationPart) {
        return DenominationPart.builder()
                .amount(jpaDenominationPart.getAmount())
                .cashType(jpaDenominationPart.getCashType())
                .build();
    }
}
