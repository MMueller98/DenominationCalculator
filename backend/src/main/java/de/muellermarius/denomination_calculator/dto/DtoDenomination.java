package de.muellermarius.denomination_calculator.dto;

import de.muellermarius.denomination_calculator.domain.Currency;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record DtoDenomination(
        String value,
        Currency currency,
        List<DtoDenominationPart> denominationParts
) implements Serializable {}
