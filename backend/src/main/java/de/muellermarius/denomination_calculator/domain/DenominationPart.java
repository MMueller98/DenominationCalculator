package de.muellermarius.denomination_calculator.domain;

import lombok.Builder;

@Builder
public record DenominationPart(
    long amount,
    CashType cashType
){}
