package de.muellermarius.denomination_calculator.modell;

import de.muellermarius.denomination_calculator.domain.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Denomination")
public class JpaDenomination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private long amount;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @OneToMany(mappedBy = "denominationResult", fetch = FetchType.LAZY)
    private List<JpaDenominationPart> denominationParts;
}
