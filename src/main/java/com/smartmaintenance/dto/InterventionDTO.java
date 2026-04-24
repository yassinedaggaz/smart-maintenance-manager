package com.smartmaintenance.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterventionDTO {

    private Long id;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String statut;
    private BigDecimal cout;
    private String description;
    private String remarques;
    private Long equipementId;
    private String equipementNom;
    private Long technicienId;
    private String technicienNom;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
