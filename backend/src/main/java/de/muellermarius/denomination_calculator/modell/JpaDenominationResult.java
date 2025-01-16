package de.muellermarius.denomination_calculator.modell;

import de.muellermarius.denomination_calculator.domain.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DenominationResult")
public class JpaDenominationResult {

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
    @SQLRestriction("calculation_type = 'DENOMINATION'")
    private List<JpaDenominationPart> denomination;

    @OneToMany(mappedBy = "denominationResult", fetch = FetchType.LAZY)
    @SQLRestriction("calculation_type = 'DIFFERENCE'")
    private List<JpaDenominationPart> difference;
}
