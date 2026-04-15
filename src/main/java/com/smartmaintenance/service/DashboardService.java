package com.smartmaintenance.service;

import com.smartmaintenance.dto.DashboardStatsDTO;
import com.smartmaintenance.entity.Equipement;
import com.smartmaintenance.entity.Intervention;
import com.smartmaintenance.entity.Panne;
import com.smartmaintenance.entity.Technicien;
import com.smartmaintenance.repository.EquipementRepository;
import com.smartmaintenance.repository.InterventionRepository;
import com.smartmaintenance.repository.PanneRepository;
import com.smartmaintenance.repository.TechnicienRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@Slf4j
public class DashboardService {

    @Autowired
    private EquipementRepository equipementRepository;

    @Autowired
    private PanneRepository panneRepository;

    @Autowired
    private TechnicienRepository technicienRepository;

    @Autowired
    private InterventionRepository interventionRepository;

    public DashboardStatsDTO getDashboardStats() {
        log.info("Generating dashboard statistics");

        Long totalEquipements = equipementRepository.count();
        Long totalPannes = panneRepository.count();
        Long totalTechniciens = technicienRepository.count();
        Long totalInterventions = interventionRepository.count();

        Long interventionsEnCours = interventionRepository.countByStatut(Intervention.StatutIntervention.EN_COURS);
        Long interventionsPlanifiees = interventionRepository.countByStatut(Intervention.StatutIntervention.PLANIFIEE);
        Long interventionsTerminees = interventionRepository.countByStatut(Intervention.StatutIntervention.TERMINEEE);

        BigDecimal coutTotalMaintenance = interventionRepository.sumTotalCout();
        if (coutTotalMaintenance == null) {
            coutTotalMaintenance = BigDecimal.ZERO;
        }

        Double coutMoyenParIntervention = interventionRepository.avgCout();
        if (coutMoyenParIntervention == null) {
            coutMoyenParIntervention = 0.0;
        }

        List<DashboardStatsDTO.EquipementPanneDTO> equipementsLePlusEnPanne = getEquipementsWithMostPannes();
        List<DashboardStatsDTO.TechnicienChargeDTO> technicienChargeWork = getTechnicienWorkLoad();

        return DashboardStatsDTO.builder()
                .totalEquipements(totalEquipements)
                .totalPannes(totalPannes)
                .totalTechniciens(totalTechniciens)
                .totalInterventions(totalInterventions)
                .interventionsEnCours(interventionsEnCours)
                .interventionsPlanifiees(interventionsPlanifiees)
                .interventionsTerminees(interventionsTerminees)
                .coutTotalMaintenance(coutTotalMaintenance)
                .coutMoyenParIntervention(coutMoyenParIntervention)
                .equipementsLePlusEnPanne(equipementsLePlusEnPanne)
                .technicienChargeWork(technicienChargeWork)
                .build();
    }

    private List<DashboardStatsDTO.EquipementPanneDTO> getEquipementsWithMostPannes() {
        List<Equipement> equipements = equipementRepository.findTop5EquipementsWithMostPannes();

        return equipements.stream()
                .map(e -> DashboardStatsDTO.EquipementPanneDTO.builder()
                        .equipementId(e.getId())
                        .equipementNom(e.getNom())
                        .nombrePannes((long) (e.getPannes() != null ? e.getPannes().size() : 0))
                        .build()
                )
                .collect(Collectors.toList());
    }

    private List<DashboardStatsDTO.TechnicienChargeDTO> getTechnicienWorkLoad() {
        List<Technicien> techniciens = technicienRepository.findTechniciensByInterventionCount();

        return techniciens.stream()
                .map(t -> DashboardStatsDTO.TechnicienChargeDTO.builder()
                        .technicienId(t.getId())
                        .technicienNom(t.getFullName())
                        .nombreInterventions((long) (t.getInterventions() != null ? t.getInterventions().size() : 0))
                        .build()
                )
                .collect(Collectors.toList());
    }
}
