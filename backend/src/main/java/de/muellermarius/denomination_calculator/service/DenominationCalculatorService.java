package de.muellermarius.denomination_calculator.service;

import de.muellermarius.denomination_calculator.domain.DenominationResult;
import de.muellermarius.denomination_calculator.domain.CashType;
import de.muellermarius.denomination_calculator.domain.Currency;
import de.muellermarius.denomination_calculator.domain.DenominationPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DenominationCalculatorService {

    private static final List<CashType> CASH_TYPES;

    static {
        CASH_TYPES = Arrays.asList(CashType.values());
        CASH_TYPES.sort(Comparator.comparingLong(CashType::getValue).reversed());
    }

    private final DenominationPersistenceService denominationPersistenceService;

    @Autowired
    public DenominationCalculatorService(final DenominationPersistenceService denominationPersistenceService) {
        this.denominationPersistenceService = denominationPersistenceService;
    }

    public DenominationResult calculateDenominationAndDifference(
            final long amount,
            final Currency currency,
            final String userId
    ) {
        final List<DenominationPart> currentCalculation = calculateMinimumDenominationParts(amount);
        final Optional<List<DenominationPart>> difference = calculateDifferenceToPreviousCalculation(
                userId,
                currentCalculation
        );

        final DenominationResult denominationResult = DenominationResult.builder()
                .amount(amount)
                .currency(currency)
                .denomination(currentCalculation)
                .difference(difference)
                .build();

        denominationPersistenceService.saveDenominationResult(denominationResult, userId);

        return denominationResult;
    }

    // Greedy Algorithm to find minimum number of cash (banknotes and coins)
    private List<DenominationPart> calculateMinimumDenominationParts(long amount) {
        List<DenominationPart> denomination = new ArrayList<>();

        // Loop over all types off cash
        for (CashType cashType : CASH_TYPES) {
            final long value = cashType.getValue();
            int count = 0;

            while (amount >= value) {
                count++;
                amount -= value;
            }

            denomination.add(new DenominationPart(count, cashType));
        }

        return denomination;
    }

    private Optional<List<DenominationPart>> calculateDifferenceToPreviousCalculation(
            final String userId,
            final List<DenominationPart> currentCalculation
    ) {

        final Optional<List<DenominationPart>> previousDenominationCalculation = denominationPersistenceService
                .getPreviousDenominationResult(userId)
                .map(DenominationResult::denomination);

        return previousDenominationCalculation
                .map(previousCalculation -> calculateDifference(currentCalculation, previousCalculation));
    }

    private List<DenominationPart> calculateDifference(
            final List<DenominationPart> currentCalculation,
            final List<DenominationPart> previousCalculation
    ) {
        final Map<CashType, Long> currentCalculationMap = currentCalculation.stream()
                .collect(Collectors.groupingBy(DenominationPart::cashType, Collectors.summingLong(DenominationPart::amount)));

        final Map<CashType, Long> previousCalculationMap = previousCalculation.stream()
                .collect(Collectors.groupingBy(DenominationPart::cashType, Collectors.summingLong(DenominationPart::amount)));

        final List<DenominationPart> difference = new ArrayList<>();

        for (CashType cashType : CashType.values()) {
            final Long current = currentCalculationMap.getOrDefault(cashType, 0L);
            final Long previous = previousCalculationMap.getOrDefault(cashType, 0L);

            final DenominationPart denominationPart = DenominationPart.builder()
                    .amount(current - previous)
                    .cashType(cashType)
                    .build();

            difference.add(denominationPart);
        }

        return difference;
    }

}
