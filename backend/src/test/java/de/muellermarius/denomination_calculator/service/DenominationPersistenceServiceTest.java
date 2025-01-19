package de.muellermarius.denomination_calculator.service;

import de.muellermarius.denomination_calculator.domain.CashType;
import de.muellermarius.denomination_calculator.domain.Currency;
import de.muellermarius.denomination_calculator.domain.Denomination;
import de.muellermarius.denomination_calculator.domain.DenominationPart;
import de.muellermarius.denomination_calculator.modell.JpaDenomination;
import de.muellermarius.denomination_calculator.modell.JpaDenominationPart;
import de.muellermarius.denomination_calculator.repository.DenominationPartRepository;
import de.muellermarius.denomination_calculator.repository.DenominationRepository;
import de.muellermarius.denomination_calculator.translation.DenominationJpaToDomainTranslation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class DenominationPersistenceServiceTest {

    @Mock
    private DenominationRepository denominationRepository;

    @Mock
    private DenominationPartRepository denominationPartRepository;

    @Mock
    private DenominationJpaToDomainTranslation translation;

    @InjectMocks
    private DenominationPersistenceService underTest;

    @Test
    public void testSaveDenominationResult() {
        final DenominationPart inputPart = DenominationPart.builder().build();
        final Denomination input = Denomination.builder().denominationParts(List.of(inputPart)).build();

        final String userId = "user-id";
        final LocalDateTime createdAt = LocalDateTime.now();

        final JpaDenominationPart jpaDenominationPart = new JpaDenominationPart(1L, 1, CashType.FIFTY_CENT, null);
        final JpaDenomination jpaDenomination = new JpaDenomination(
                1L,
                userId,
                createdAt,
                70L,
                Currency.EURO_CENT,
                List.of(jpaDenominationPart)

        );

        Mockito.when(translation.translateToPersistenceEntity(input, userId)).thenReturn(jpaDenomination);
        Mockito.when(translation.translateToPersistenceEntity(inputPart, jpaDenomination)).thenReturn(jpaDenominationPart);
        Mockito.when(denominationRepository.save(jpaDenomination)).thenReturn(jpaDenomination);
        Mockito.when(denominationPartRepository.saveAll(List.of(jpaDenominationPart))).thenReturn(List.of(jpaDenominationPart));

        final LocalDateTime result = underTest.saveDenominationResult(input, userId);

        assertNotNull(result);
        assertEquals(createdAt, result);
        verify(denominationRepository).save(jpaDenomination);
        verify(denominationPartRepository).saveAll(List.of(jpaDenominationPart));
    }
}
