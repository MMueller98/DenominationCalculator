package de.muellermarius.denomination_calculator.dto;

import de.muellermarius.denomination_calculator.domain.Currency;

public record DenominationRequest(
        double value,
        Currency currency
) {}
