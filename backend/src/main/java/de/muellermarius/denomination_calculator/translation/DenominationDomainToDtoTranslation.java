package de.muellermarius.denomination_calculator.translation;

import de.muellermarius.denomination_calculator.domain.Currency;
import de.muellermarius.denomination_calculator.domain.Denomination;
import de.muellermarius.denomination_calculator.domain.DenominationPart;
import de.muellermarius.denomination_calculator.dto.DtoDenomination;
import de.muellermarius.denomination_calculator.dto.DtoDenominationPart;
import de.muellermarius.denomination_calculator.dto.DenominationResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class DenominationDomainToDtoTranslation {

    public DenominationResponse toDto(
            final Denomination denomination,
            final Optional<Denomination> previousDenomination,
            final Optional<List<DenominationPart>> difference
    ) {
        return DenominationResponse.builder()
                .denomination(denominationToDto(denomination))
                .previousDenomination(previousDenomination.map(this::denominationToDto).orElse(null))
                .difference(difference.map(list -> list.stream().map(this::denominationPartToDto).toList()).orElse(null))
                .build();
    }

    public DtoDenomination denominationToDto(final Denomination denomination) {
        return DtoDenomination.builder()
                .value(converToEuroString(denomination.value()))
                .currency(Currency.EURO)
                .denominationParts(denomination.denominationParts().stream().map(this::denominationPartToDto).toList())
                .build();
    }

    public DtoDenominationPart denominationPartToDto(final DenominationPart denominationPart) {
        return DtoDenominationPart.builder()
                .amount(denominationPart.amount())
                .cashType(denominationPart.cashType())
                .build();
    }

    public Denomination denominationToDomain(final DtoDenomination dtoDenomination) {
        final long euroCentValue = new BigDecimal(dtoDenomination.value())
            .movePointRight(2)
            .longValue();

        System.out.println("EuroCent Value of " + dtoDenomination.value() + ": " + euroCentValue);

        return Denomination.builder()
            .value(euroCentValue)
            .currency(dtoDenomination.currency())
            .denominationParts(dtoDenomination.denominationParts()
                .stream()
                .map(this::denominationPartToDomain)
                .toList())
            .build();
    }

    public DenominationPart denominationPartToDomain(final DtoDenominationPart denominationPart) {
        return DenominationPart.builder()
            .amount(denominationPart.amount())
            .cashType(denominationPart.cashType())
            .build();
    }

    private String converToEuroString(final long value) {
        return new BigDecimal(value).movePointLeft(2).toString();
    }
}
