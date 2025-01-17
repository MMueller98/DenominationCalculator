package de.muellermarius.denomination_calculator.dto;

import java.io.Serializable;

import lombok.Builder;

import de.muellermarius.denomination_calculator.domain.Currency;

@Builder
public record DenominationRequest(
        double value,
        Currency currency
) implements Serializable {}
