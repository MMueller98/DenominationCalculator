package de.muellermarius.denomination_calculator.domain;

import lombok.Builder;

import java.util.List;

@Builder
public record Denomination(
        long value,
        Currency currency,
        List<DenominationPart> denominationParts
) {}
