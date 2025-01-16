package de.muellermarius.denomination_calculator.translation;

import de.muellermarius.denomination_calculator.domain.Currency;
import de.muellermarius.denomination_calculator.domain.DenominationPart;
import de.muellermarius.denomination_calculator.domain.DenominationResult;
import de.muellermarius.denomination_calculator.dto.DenominationPartResponse;
import de.muellermarius.denomination_calculator.dto.DenominationResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Service
public class DenominationDomainToDtoTranslation {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.00");

    public DenominationResponse toDto(final DenominationResult denominationResult) {
        return DenominationResponse.builder()
                .value(converToEuroString(denominationResult.amount()))
                .currency(Currency.EURO)
                .denomination(denominationResult.denomination().stream().map(this::denominationPartToDto).toList())
                .difference(denominationResult.difference()
                                    .map(list -> list.stream().map(this::denominationPartToDto).toList())
                                    .orElse(null))
                .build();
    }

    public DenominationPartResponse denominationPartToDto(final DenominationPart denominationPartResponse) {
        return DenominationPartResponse.builder()
                .amount(denominationPartResponse.amount())
                .cashType(denominationPartResponse.cashType())
                .build();
    }

    private String converToEuroString(final long value) {
        return new BigDecimal(value).movePointLeft(2).toString();
    }
}
