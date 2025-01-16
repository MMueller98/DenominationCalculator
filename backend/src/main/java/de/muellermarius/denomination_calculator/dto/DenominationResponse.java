package de.muellermarius.denomination_calculator.dto;

import de.muellermarius.denomination_calculator.domain.Currency;
import de.muellermarius.denomination_calculator.domain.DenominationPart;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record DenominationResponse(
        String value,
        Currency currency,
        List<DenominationPartResponse> denomination,
        String previousValue,
        Currency previousCurrency,
        List<DenominationPartResponse> difference
) implements Serializable {}
