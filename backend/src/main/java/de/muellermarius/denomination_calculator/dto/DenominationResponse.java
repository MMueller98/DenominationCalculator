package de.muellermarius.denomination_calculator.dto;

import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record DenominationResponse(
        DtoDenomination denomination,
        DtoDenomination previousDenomination,
        List<DtoDenominationPart> difference
) implements Serializable {}
