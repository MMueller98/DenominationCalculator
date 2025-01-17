package de.muellermarius.denomination_calculator.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record DenominationCreateResponse(
    LocalDateTime createdAt
) implements Serializable {}
