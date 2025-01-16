package de.muellermarius.denomination_calculator.translation;

import de.muellermarius.denomination_calculator.domain.DenominationPart;
import de.muellermarius.denomination_calculator.domain.Denomination;
import de.muellermarius.denomination_calculator.modell.JpaDenominationPart;
import de.muellermarius.denomination_calculator.modell.JpaDenomination;
import org.springframework.stereotype.Service;


@Service
public class DenominationJpaToDomainTranslation {

    public JpaDenomination translateToPersistenceEntity(final Denomination denomination, final String userId) {
        return JpaDenomination.builder()
                .userId(userId)
                .amount(denomination.value())
                .currency(denomination.currency())
                .build();
    }

    public JpaDenominationPart translateToPersistenceEntity(
            final DenominationPart denominationPart,
            final JpaDenomination denominationResult
    ) {
        return JpaDenominationPart.builder()
                .amount(denominationPart.amount())
                .cashType(denominationPart.cashType())
                .denominationResult(denominationResult)
                .build();
    }

    public Denomination translateToDomainEntity(final JpaDenomination jpaDenomination) {
        return Denomination.builder()
                .value(jpaDenomination.getAmount())
                .currency(jpaDenomination.getCurrency())
                .denominationParts(jpaDenomination.getDenominationParts()
                                      .stream()
                                      .map(this::translateToDomainEntity)
                                      .toList()
                )
                .build();
    }

    public DenominationPart translateToDomainEntity(final JpaDenominationPart jpaDenominationPart) {
        return DenominationPart.builder()
                .amount(jpaDenominationPart.getAmount())
                .cashType(jpaDenominationPart.getCashType())
                .build();
    }
}
