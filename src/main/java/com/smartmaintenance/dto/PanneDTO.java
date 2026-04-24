package com.smartmaintenance.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PanneDTO {

    private Long id;
    private String description;
    private String categorie;
    private LocalDate dateSignalement;
    private String statut;
    private String remarques;
    private Long equipementId;
    private String equipementNom;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
