package de.muellermarius.denomination_calculator.controller;

import de.muellermarius.denomination_calculator.domain.Denomination;
import de.muellermarius.denomination_calculator.domain.Currency;
import de.muellermarius.denomination_calculator.domain.DenominationPart;
import de.muellermarius.denomination_calculator.dto.DenominationCreateResponse;
import de.muellermarius.denomination_calculator.dto.DenominationRequest;
import de.muellermarius.denomination_calculator.dto.DenominationResponse;
import de.muellermarius.denomination_calculator.dto.DtoDenomination;
import de.muellermarius.denomination_calculator.service.DenominationService;

import de.muellermarius.denomination_calculator.translation.DenominationDomainToDtoTranslation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/denomination")
public class DenominationController {

    private final DenominationService denominationService;
    private final DenominationDomainToDtoTranslation denominationDomainToDtoTranslation;

    public DenominationController(
            final DenominationService denominationService,
            final DenominationDomainToDtoTranslation denominationDomainToDtoTranslation
    ) {
        this.denominationService = denominationService;
        this.denominationDomainToDtoTranslation = denominationDomainToDtoTranslation;
    }

    @PostMapping("/calculate")
    public ResponseEntity<DenominationResponse> calculateDenomination(
            @RequestHeader("X-User-Token") final String userToken,
            @RequestBody final DenominationRequest denominationRequest
    ) {
        final long euroCentValue = convertToEuroCent(denominationRequest.value(), denominationRequest.currency());

        final Optional<Denomination> previousDenomination = denominationService
            .getPreviousDenomination(userToken);

        final Denomination denomination = denominationService
            .calculateDenomination(euroCentValue, Currency.EURO_CENT, userToken);

        final Optional<List<DenominationPart>> difference = denominationService
            .calculateDifference(denomination, previousDenomination);

        return ResponseEntity.ok(denominationDomainToDtoTranslation.toDto(denomination, previousDenomination, difference));
    }

    @GetMapping("/last-calculation")
    public ResponseEntity<DenominationResponse> getLastCalculations(
        @RequestHeader("X-User-Token") final String userToken
    ) {
        final List<Denomination> userDenominations = denominationService.getPreviousTwoDenominations(userToken);

        DenominationResponse denominationResponse;

        if (userDenominations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        if (userDenominations.size() == 1) {
            denominationResponse = denominationDomainToDtoTranslation.toDto(userDenominations.getFirst(), Optional.empty(), Optional.empty());
        } else {
            final Denomination currentDenomination = userDenominations.getFirst();
            final Denomination previousDenomination = userDenominations.get(1);
            final List<DenominationPart> difference = denominationService.calculateDifference(currentDenomination, previousDenomination);
            denominationResponse = denominationDomainToDtoTranslation.toDto(currentDenomination, Optional.of(previousDenomination), Optional.of(difference));
        }

        return ResponseEntity.ok(denominationResponse);
    }


    @GetMapping("/last-denomination")
    public ResponseEntity<DtoDenomination> getLastDenomination(
        @RequestHeader("X-User-Token") final String userToken
    ) {
        return denominationService
            .getPreviousDenomination(userToken)
            .map(denominationDomainToDtoTranslation::denominationToDto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping("/persist")
    public ResponseEntity<DenominationCreateResponse> persistDenomination(
        @RequestHeader("X-User-Token") final String userToken,
        @RequestBody final DtoDenomination dtoDenomination
    ) {
        final Denomination denomination = denominationDomainToDtoTranslation.denominationToDomain(dtoDenomination);
        final LocalDateTime createdAt = denominationService.persistDenomination(denomination, userToken);

        return ResponseEntity.ok(denominationDomainToDtoTranslation.toDto(createdAt));
    }


    private long convertToEuroCent(final double value, final Currency currency) {
        return currency == Currency.EURO_CENT ? (long) value : Math.round(value * 100);
    }
}
