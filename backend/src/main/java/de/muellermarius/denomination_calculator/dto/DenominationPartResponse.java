package de.muellermarius.denomination_calculator.dto;

import de.muellermarius.denomination_calculator.domain.CashType;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record DenominationPartResponse(
        long amount,
        CashType cashType
) implements Serializable {}
