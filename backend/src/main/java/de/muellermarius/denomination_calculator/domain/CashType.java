package de.muellermarius.denomination_calculator.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum CashType {
    TWO_HUNDRED_EURO(20000, Currency.EURO_CENT, "200€"),
    ONE_HUNDRED_EURO(10000, Currency.EURO_CENT, "100€"),
    FIFTY_EURO(5000, Currency.EURO_CENT, "50€"),
    TWENTY_EURO(2000, Currency.EURO_CENT, "20€"),
    TEN_EURO(1000, Currency.EURO_CENT, "10€"),
    FIVE_EURO(500, Currency.EURO_CENT, "5€"),
    TWO_EURO(200, Currency.EURO_CENT, "2€"),
    ONE_EURO(100, Currency.EURO_CENT, "1€"),
    FIFTY_CENT(50, Currency.EURO_CENT, "0,50"),
    TWENTY_CENT(20, Currency.EURO_CENT, "0,20€"),
    TEN_CENT(10, Currency.EURO_CENT, "0,10€"),
    FIVE_CENT(5, Currency.EURO_CENT, "0,05€"),
    TWO_CENT(2, Currency.EURO_CENT, "0,02€"),
    ONE_CENT(1, Currency.EURO_CENT, "0,01€");

    @Getter
    private final long value;
    private final Currency currency;
    private final String displayName;


    CashType(final long value, final Currency currency, final String displayName) {
        this.value = value;
        this.currency = currency;
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }
}
