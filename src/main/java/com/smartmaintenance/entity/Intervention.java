package com.smartmaintenance.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "interventions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Intervention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dateDebut;

    private LocalDateTime dateFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private StatutIntervention statut = StatutIntervention.PLANIFIEE;

    @Column(precision = 10, scale = 2)
    private BigDecimal cout;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String remarques;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipement_id", nullable = false)
    private Equipement equipement;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "technicien_id", nullable = false)
    private Technicien technicien;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private java.time.LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private java.time.LocalDateTime updatedAt;

    public enum StatutIntervention {
        PLANIFIEE,
        EN_COURS,
        TERMINEEE,
        ANNULEE
    }
}
