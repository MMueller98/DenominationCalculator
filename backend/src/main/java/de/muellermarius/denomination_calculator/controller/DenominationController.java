package de.muellermarius.denomination_calculator.controller;

import de.muellermarius.denomination_calculator.domain.DenominationResult;
import de.muellermarius.denomination_calculator.domain.Currency;
import de.muellermarius.denomination_calculator.dto.DenominationRequest;
import de.muellermarius.denomination_calculator.service.DenominationCalculatorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/denomination")
public class DenominationController {

    private final DenominationCalculatorService denominationCalculatorService;

    public DenominationController(final DenominationCalculatorService denominationCalculatorService) {
        this.denominationCalculatorService = denominationCalculatorService;
    }

    @PostMapping("/calculate")
    public DenominationResult calculateDenomination(
            @RequestHeader("X-User-Token") final String userToken,
            @RequestBody final DenominationRequest denominationRequest
    ) {
        final long euroCentValue = convertToEuroCent(denominationRequest.value(), denominationRequest.currency());

        // calculate denomination
        return denominationCalculatorService.calculateDenominationAndDifference(
                euroCentValue,
                Currency.EURO_CENT,
                userToken
        );
    }

    @GetMapping("/last-calculation")
    public String getLastCalculation() {
        return null;
    }

    private long convertToEuroCent(final double value, final Currency currency) {
        return currency == Currency.EURO_CENT ? (long) value : Math.round(value * 100);
    }
}
