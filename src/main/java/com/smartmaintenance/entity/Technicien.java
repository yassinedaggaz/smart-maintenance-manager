package com.smartmaintenance.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "techniciens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Technicien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private java.time.LocalDateTime dateEmbauche;

    @Column(columnDefinition = "TEXT")
    private String competences;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private DisponibiliteStatut disponibilite = DisponibiliteStatut.DISPONIBLE;

    @OneToMany(mappedBy = "technicien", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Intervention> interventions;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private java.time.LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private java.time.LocalDateTime updatedAt;

    public enum DisponibiliteStatut {
        DISPONIBLE,
        OCCUPE,
        EN_CONGE
    }

    public String getFullName() {
        return prenom + " " + nom;
    }
}
