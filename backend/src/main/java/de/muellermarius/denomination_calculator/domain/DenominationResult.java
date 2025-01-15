package de.muellermarius.denomination_calculator.domain;

import lombok.Builder;

import java.util.List;
import java.util.Optional;

@Builder
public record DenominationResult(
        long amount,
        Currency currency,
        List<DenominationPart> denomination,
        Optional<List<DenominationPart>> difference
) {}
