package com.smartmaintenance.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "pannes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Panne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriePanne categorie;


    @Column(nullable = false)
    @Builder.Default
    private LocalDate dateSignalement = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipement_id", nullable = false)
    private Equipement equipement;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private StatutPanne statut = StatutPanne.SIGNALEEE;

    @Column(columnDefinition = "TEXT")
    private String remarques;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private java.time.LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private java.time.LocalDateTime updatedAt;

    public enum CategoriePanne {
        ELECTRIQUE,
        MECANIQUE,
        HYDRAULIQUE,
        ELECTRONIQUE,
        LOGICIEL,
        AUTRE
    }

    public enum StatutPanne {
        SIGNALEEE,
        EN_COURS,
        RESOLUE
    }
}
