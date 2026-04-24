package com.smartmaintenance.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipementDTO {

    private Long id;
    private String nom;
    private String etat;
    private LocalDate dateAcquisition;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<PanneDTO> pannes;
    private List<InterventionDTO> interventions;
}
