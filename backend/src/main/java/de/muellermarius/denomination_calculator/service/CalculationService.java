package de.muellermarius.denomination_calculator.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import de.muellermarius.denomination_calculator.domain.CashType;
import de.muellermarius.denomination_calculator.domain.Denomination;
import de.muellermarius.denomination_calculator.domain.DenominationPart;

@Service
public class CalculationService {

    private static final List<CashType> CASH_TYPES;

    static {
        CASH_TYPES = Arrays.asList(CashType.values());
        CASH_TYPES.sort(Comparator.comparingLong(CashType::getValue).reversed());
    }

    // Greedy Algorithm to find minimum number of cash (banknotes and coins)
    public List<DenominationPart> calculateMinimumDenominationParts(long amount) {
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

    // Algorithm to calculate difference between two denomination calculations
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

            if (current.equals(0L) && previous.equals(0L)) {
                continue;
            }

            final DenominationPart denominationPart = DenominationPart.builder()
                .amount(current - previous)
                .cashType(cashType)
                .build();

            difference.add(denominationPart);
        }

        return difference;
    }
}
