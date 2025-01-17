package de.muellermarius.denomination_calculator.service;

import de.muellermarius.denomination_calculator.domain.Denomination;
import de.muellermarius.denomination_calculator.domain.CashType;
import de.muellermarius.denomination_calculator.domain.Currency;
import de.muellermarius.denomination_calculator.domain.DenominationPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class DenominationService {

    private static final List<CashType> CASH_TYPES;

    static {
        CASH_TYPES = Arrays.asList(CashType.values());
        CASH_TYPES.sort(Comparator.comparingLong(CashType::getValue).reversed());
    }

    private final CalculationService calculationService;
    private final DenominationPersistenceService denominationPersistenceService;

    @Autowired
    public DenominationService(
        final CalculationService calculationService,
        final DenominationPersistenceService denominationPersistenceService
    ) {
        this.calculationService = calculationService;
        this.denominationPersistenceService = denominationPersistenceService;
    }

    public Denomination calculateDenomination(
            final long amount,
            final Currency currency,
            final String userId
    ) {
        final List<DenominationPart> currentCalculation = calculationService
            .calculateMinimumDenominationParts(amount);

        final Denomination denomination = Denomination.builder()
                .value(amount)
                .currency(currency)
                .denominationParts(currentCalculation)
                .build();

        denominationPersistenceService.saveDenominationResult(denomination, userId);

        return denomination;
    }

    public Optional<List<DenominationPart>> calculateDifference(
        final Denomination currentDenomination,
        final Optional<Denomination> previousDenomination
    ) {
        return previousDenomination.map(previous -> calculateDifference(currentDenomination, previous));
    }

    public List<DenominationPart> calculateDifference(
            final Denomination currentDenomination,
            final Denomination previousDenomination
    ) {
        return calculationService.calculateDifference(currentDenomination, previousDenomination);
    }

    public Optional<Denomination> getPreviousDenomination(final String userToken) {
        return denominationPersistenceService.getPreviousDenomination(userToken);
    }

    public List<Denomination> getPreviousTwoDenominations(final String userToken) {
        return denominationPersistenceService.getPreviousDenominations(userToken, 2);
    }

    public LocalDateTime persistDenomination(final Denomination denomination, final String userToken) {
        return denominationPersistenceService.saveDenominationResult(denomination, userToken);
    }
}