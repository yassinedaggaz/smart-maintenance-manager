package com.smartmaintenance.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "equipements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Equipement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private EtatEquipement etat = EtatEquipement.EN_SERVICE;

    @Column(nullable = false)
    private LocalDate dateAcquisition;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "equipement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Panne> pannes;

    @OneToMany(mappedBy = "equipement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Intervention> interventions;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private java.time.LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private java.time.LocalDateTime updatedAt;

    public enum EtatEquipement {
        EN_SERVICE,
        EN_PANNE,
        EN_MAINTENANCE
    }
}
