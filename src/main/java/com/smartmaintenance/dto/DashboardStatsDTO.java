package com.smartmaintenance.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardStatsDTO {

    private Long totalEquipements;
    private Long totalPannes;
    private Long totalTechniciens;
    private Long totalInterventions;
    
    private Long interventionsEnCours;
    private Long interventionsPlanifiees;
    private Long interventionsTerminees;
    
    private BigDecimal coutTotalMaintenance;
    private Double coutMoyenParIntervention;
    
    private List<EquipementPanneDTO> equipementsLePlusEnPanne;
    private List<TechnicienChargeDTO> technicienChargeWork;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class EquipementPanneDTO {
        private Long equipementId;
        private String equipementNom;
        private Long nombrePannes;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TechnicienChargeDTO {
        private Long technicienId;
        private String technicienNom;
        private Long nombreInterventions;
    }
}
