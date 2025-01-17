package de.muellermarius.denomination_calculator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.muellermarius.denomination_calculator.domain.CashType;
import de.muellermarius.denomination_calculator.domain.Currency;
import de.muellermarius.denomination_calculator.domain.Denomination;
import de.muellermarius.denomination_calculator.domain.DenominationPart;

public class CalculationServiceTest {

    final CalculationService underTest = new CalculationService();

    @ParameterizedTest
    @MethodSource("calculateMinimumDenominationPartsTestData")
    public void calculateMinimumDenominationPartsTest(
        final long input,
        final List<DenominationPart> expected
    ) {
        assertEquals(expected, underTest.calculateMinimumDenominationParts(input));
    }

    @Test
    public void testCalculateDifference() {
        final Denomination previousDenomination = Denomination.builder()
            .value(4532)
            .currency(Currency.EURO_CENT)
            .denominationParts(getFirst())
            .build();

        final Denomination currentDenomination = Denomination.builder()
            .value(23423)
            .currency(Currency.EURO_CENT)
            .denominationParts(getSecond())
            .build();

        final List<DenominationPart> expected = List.of(
            DenominationPart.builder().amount(1).cashType(CashType.TWO_HUNDRED_EURO).build(),
            DenominationPart.builder().amount(-1).cashType(CashType.TWENTY_EURO).build(),
            DenominationPart.builder().amount(1).cashType(CashType.TEN_EURO).build(),
            DenominationPart.builder().amount(-1).cashType(CashType.FIVE_EURO).build(),
            DenominationPart.builder().amount(2).cashType(CashType.TWO_EURO).build(),
            DenominationPart.builder().amount(0).cashType(CashType.TWENTY_CENT).build(),
            DenominationPart.builder().amount(-1).cashType(CashType.TEN_CENT).build(),
            DenominationPart.builder().amount(0).cashType(CashType.TWO_CENT).build(),
            DenominationPart.builder().amount(1).cashType(CashType.ONE_CENT).build()
        );

        assertEquals(expected, underTest.calculateDifference(currentDenomination, previousDenomination));

    }

    private static List<Arguments> calculateMinimumDenominationPartsTestData() {
        return List.of(
            Arguments.of(4532, getFirst()),
            Arguments.of(23423, getSecond())
        );
    }

    private static List<DenominationPart> getFirst() {
        return List.of(
            DenominationPart.builder().cashType(CashType.TWO_HUNDRED_EURO).amount(0).build(),
            DenominationPart.builder().cashType(CashType.ONE_HUNDRED_EURO).amount(0).build(),
            DenominationPart.builder().cashType(CashType.FIFTY_EURO).amount(0).build(),
            DenominationPart.builder().cashType(CashType.TWENTY_EURO).amount(2).build(),
            DenominationPart.builder().cashType(CashType.TEN_EURO).amount(0).build(),
            DenominationPart.builder().cashType(CashType.FIVE_EURO).amount(1).build(),
            DenominationPart.builder().cashType(CashType.TWO_EURO).amount(0).build(),
            DenominationPart.builder().cashType(CashType.ONE_EURO).amount(0).build(),
            DenominationPart.builder().cashType(CashType.FIFTY_CENT).amount(0).build(),
            DenominationPart.builder().cashType(CashType.TWENTY_CENT).amount(1).build(),
            DenominationPart.builder().cashType(CashType.TEN_CENT).amount(1).build(),
            DenominationPart.builder().cashType(CashType.FIVE_CENT).amount(0).build(),
            DenominationPart.builder().cashType(CashType.TWO_CENT).amount(1).build(),
            DenominationPart.builder().cashType(CashType.ONE_CENT).amount(0).build()
        );
    }

    private static List<DenominationPart> getSecond() {
        return List.of(
            DenominationPart.builder().cashType(CashType.TWO_HUNDRED_EURO).amount(1).build(),
            DenominationPart.builder().cashType(CashType.ONE_HUNDRED_EURO).amount(0).build(),
            DenominationPart.builder().cashType(CashType.FIFTY_EURO).amount(0).build(),
            DenominationPart.builder().cashType(CashType.TWENTY_EURO).amount(1).build(),
            DenominationPart.builder().cashType(CashType.TEN_EURO).amount(1).build(),
            DenominationPart.builder().cashType(CashType.FIVE_EURO).amount(0).build(),
            DenominationPart.builder().cashType(CashType.TWO_EURO).amount(2).build(),
            DenominationPart.builder().cashType(CashType.ONE_EURO).amount(0).build(),
            DenominationPart.builder().cashType(CashType.FIFTY_CENT).amount(0).build(),
            DenominationPart.builder().cashType(CashType.TWENTY_CENT).amount(1).build(),
            DenominationPart.builder().cashType(CashType.TEN_CENT).amount(0).build(),
            DenominationPart.builder().cashType(CashType.FIVE_CENT).amount(0).build(),
            DenominationPart.builder().cashType(CashType.TWO_CENT).amount(1).build(),
            DenominationPart.builder().cashType(CashType.ONE_CENT).amount(1).build()
        );
    }
}
