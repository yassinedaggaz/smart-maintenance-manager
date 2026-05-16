package com.smartmaintenance.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TechnicienDTO {

    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private LocalDateTime dateEmbauche;
    private String competences;
    private String disponibilite;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<InterventionDTO> interventions;

    public String getFullName() {
        return prenom + " " + nom;
    }
}
