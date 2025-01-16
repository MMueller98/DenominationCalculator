package de.muellermarius.denomination_calculator.translation;

import de.muellermarius.denomination_calculator.domain.CalculationType;
import de.muellermarius.denomination_calculator.domain.DenominationPart;
import de.muellermarius.denomination_calculator.domain.DenominationResult;
import de.muellermarius.denomination_calculator.modell.JpaDenominationPart;
import de.muellermarius.denomination_calculator.modell.JpaDenominationResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class DenominationJpaToDomainTranslation {

    public JpaDenominationResult translateToPersistenceEntity(final DenominationResult denominationResult, final String userId) {
        return JpaDenominationResult.builder()
                .userId(userId)
                .amount(denominationResult.amount())
                .currency(denominationResult.currency())
                .build();
    }

    public JpaDenominationPart translateToPersistenceEntity(
            final DenominationPart denominationPart,
            final JpaDenominationResult denominationResult,
            final CalculationType calculationType
            ) {
        return JpaDenominationPart.builder()
                .amount(denominationPart.amount())
                .cashType(denominationPart.cashType())
                .calculationType(calculationType)
                .denominationResult(denominationResult)
                .build();
    }

    public DenominationResult translateToDomainEntity(final JpaDenominationResult jpaDenominationResult) {
        return DenominationResult.builder()
                .amount(jpaDenominationResult.getAmount())
                .currency(jpaDenominationResult.getCurrency())
                .denomination(jpaDenominationResult
                                      .getDenomination()
                                      .stream()
                                      .map(this::translateToDomainEntity)
                                      .toList()
                )
                .difference(Optional.of(jpaDenominationResult
                                    .getDifference()
                                    .stream()
                                    .map(this::translateToDomainEntity)
                                    .toList()).filter(Predicate.not(List::isEmpty))
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
