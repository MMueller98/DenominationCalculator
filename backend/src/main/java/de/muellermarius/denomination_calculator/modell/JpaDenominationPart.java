package de.muellermarius.denomination_calculator.modell;

import de.muellermarius.denomination_calculator.domain.CashType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DenominationPart")
public class JpaDenominationPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long amount;

    @Enumerated(EnumType.STRING)
    private CashType cashType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "denominationId")
    private JpaDenomination denominationResult;
}
