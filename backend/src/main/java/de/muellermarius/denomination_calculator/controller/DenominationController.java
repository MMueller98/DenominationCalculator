package de.muellermarius.denomination_calculator.controller;

import java.util.List;
import java.util.Optional;

import de.muellermarius.denomination_calculator.domain.DenominationPart;
import de.muellermarius.denomination_calculator.domain.DenominationResult;
import de.muellermarius.denomination_calculator.domain.Currency;
import de.muellermarius.denomination_calculator.dto.DenominationRequest;
import de.muellermarius.denomination_calculator.dto.DenominationResponse;
import de.muellermarius.denomination_calculator.service.DenominationCalculatorService;
import de.muellermarius.denomination_calculator.service.DenominationPersistenceService;

import de.muellermarius.denomination_calculator.translation.DenominationDomainToDtoTranslation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/denomination")
public class DenominationController {

    private final DenominationCalculatorService denominationCalculatorService;
    private final DenominationPersistenceService denominationPersistenceService;

    private final DenominationDomainToDtoTranslation denominationDomainToDtoTranslation;

    public DenominationController(
            final DenominationCalculatorService denominationCalculatorService,
            final DenominationPersistenceService denominationPersistenceService,
            final DenominationDomainToDtoTranslation denominationDomainToDtoTranslation
    ) {
        this.denominationCalculatorService = denominationCalculatorService;
        this.denominationPersistenceService = denominationPersistenceService;
        this.denominationDomainToDtoTranslation = denominationDomainToDtoTranslation;
    }

    @PostMapping("/calculate")
    public DenominationResponse calculateDenomination(
            @RequestHeader("X-User-Token") final String userToken,
            @RequestBody final DenominationRequest denominationRequest
    ) {
        final long euroCentValue = convertToEuroCent(denominationRequest.value(), denominationRequest.currency());

        final DenominationResult denominationResult = denominationCalculatorService.calculateDenominationAndDifference(
                euroCentValue,
                Currency.EURO_CENT,
                userToken
        );

        return denominationDomainToDtoTranslation.toDto(denominationResult);
    }

    @GetMapping("/last-calculation")
    public DenominationResponse getLastCalculations(
        @RequestHeader("X-User-Token") final String userToken
    ) {
        return denominationPersistenceService
                .getPreviousDenominationResult(userToken)
                .map(denominationDomainToDtoTranslation::toDto)
                .orElse(null);
    }

    private long convertToEuroCent(final double value, final Currency currency) {
        return currency == Currency.EURO_CENT ? (long) value : Math.round(value * 100);
    }
}
