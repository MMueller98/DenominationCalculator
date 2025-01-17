package de.muellermarius.denomination_calculator.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import de.muellermarius.denomination_calculator.domain.CashType;
import de.muellermarius.denomination_calculator.domain.Currency;
import de.muellermarius.denomination_calculator.domain.Denomination;
import de.muellermarius.denomination_calculator.domain.DenominationPart;
import de.muellermarius.denomination_calculator.service.DenominationService;
import de.muellermarius.denomination_calculator.translation.DenominationDomainToDtoTranslation;

@WebMvcTest(DenominationController.class)
@Disabled
public class DenominationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //@MockBean
    private DenominationService denominationService;

    //@MockBean
    private DenominationDomainToDtoTranslation translation;

    @Test
    public void testCalculateDenomination() throws Exception {
        final String userToken = "user-token";
        final Denomination currentDenomination = createDenomination();
        final Denomination previousDenomination = createDenomination();
        final List<DenominationPart> difference = createDifference();

        Mockito.when(denominationService.getPreviousDenomination(userToken))
            .thenReturn(Optional.of(previousDenomination));

        Mockito.when(denominationService.calculateDenomination(2L, Currency.EURO, userToken))
            .thenReturn(currentDenomination);

        Mockito.when(denominationService.calculateDifference(currentDenomination, Optional.of(previousDenomination)))
            .thenReturn(Optional.of(difference));

        String requestBody = """
    {
        "value": 2,
        "currency": "EUR"
    }
    """;

        mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/denomination/calculate")
                .header("X-User-Token", userToken)
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.denomination").exists())
            .andExpect(jsonPath("$.previousDenomination").exists())
            .andExpect(jsonPath("$.difference").exists());
    }

    private Denomination createDenomination() {
        return Denomination.builder()
            .value(2)
            .currency(Currency.EURO)
            .denominationParts(List.of(
                DenominationPart.builder()
                    .amount(2)
                    .cashType(CashType.TWO_EURO)
                    .build()
            ))
            .build();
    }

    private List<DenominationPart> createDifference() {
        return List.of(
            DenominationPart.builder()
                .amount(2)
                .cashType(CashType.FIVE_EURO)
                .build()
        );
    }
}
