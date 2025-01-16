package de.muellermarius.denomination_calculator.service;

import de.muellermarius.denomination_calculator.domain.Denomination;
import de.muellermarius.denomination_calculator.domain.CashType;
import de.muellermarius.denomination_calculator.domain.Currency;
import de.muellermarius.denomination_calculator.domain.DenominationPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DenominationService {

    private static final List<CashType> CASH_TYPES;

    static {
        CASH_TYPES = Arrays.asList(CashType.values());
        CASH_TYPES.sort(Comparator.comparingLong(CashType::getValue).reversed());
    }

    private final DenominationPersistenceService denominationPersistenceService;

    @Autowired
    public DenominationService(final DenominationPersistenceService denominationPersistenceService) {
        this.denominationPersistenceService = denominationPersistenceService;
    }

    public Denomination calculateDenomination(
            final long amount,
            final Currency currency,
            final String userId
    ) {
        final List<DenominationPart> currentCalculation = calculateMinimumDenominationParts(amount);

        final Denomination denomination = Denomination.builder()
                .value(amount)
                .currency(currency)
                .denominationParts(currentCalculation)
                .build();

        denominationPersistenceService.saveDenominationResult(denomination, userId);

        return denomination;
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

    public List<DenominationPart> calculateDifference(
            final Denomination currentDenomination,
            final Denomination previousDenomination
    ) {
        final Map<CashType, Long> currentCalculationMap = currentDenomination.denominationParts().stream()
                .collect(Collectors.groupingBy(DenominationPart::cashType, Collectors.summingLong(DenominationPart::amount)));

        final Map<CashType, Long> previousCalculationMap = previousDenomination.denominationParts().stream()
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