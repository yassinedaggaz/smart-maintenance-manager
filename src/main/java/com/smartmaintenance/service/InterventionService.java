package com.smartmaintenance.service;

import com.smartmaintenance.dto.InterventionDTO;
import com.smartmaintenance.entity.Equipement;
import com.smartmaintenance.entity.Intervention;
import com.smartmaintenance.entity.Technicien;
import com.smartmaintenance.exception.BadRequestException;
import com.smartmaintenance.exception.ResourceNotFoundException;
import com.smartmaintenance.mapper.InterventionMapper;
import com.smartmaintenance.repository.EquipementRepository;
import com.smartmaintenance.repository.InterventionRepository;
import com.smartmaintenance.repository.TechnicienRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@Slf4j
public class InterventionService {

    @Autowired
    private InterventionRepository interventionRepository;

    @Autowired
    private EquipementRepository equipementRepository;

    @Autowired
    private TechnicienRepository technicienRepository;

    @Autowired
    private InterventionMapper interventionMapper;

    @Autowired
    private TechnicienService technicienService;

    public InterventionDTO create(InterventionDTO dto) {
        log.info("Creating new intervention");

        Equipement equipement = equipementRepository.findById(dto.getEquipementId())
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", "id", dto.getEquipementId()));

        Technicien technicien = technicienRepository.findById(dto.getTechnicienId())
                .orElseThrow(() -> new ResourceNotFoundException("Technicien", "id", dto.getTechnicienId()));

        Intervention intervention = interventionMapper.toEntity(dto);
        intervention.setEquipement(equipement);
        intervention.setTechnicien(technicien);

        Intervention saved = interventionRepository.save(intervention);
        return interventionMapper.toDTO(saved);
    }

    public InterventionDTO getById(Long id) {
        log.info("Getting intervention with id: {}", id);
        Intervention intervention = interventionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Intervention", "id", id));
        return interventionMapper.toDTO(intervention);
    }

    public List<InterventionDTO> getAll() {
        log.info("Getting all interventions");
        List<Intervention> interventions = interventionRepository.findAll();
        return interventionMapper.toDTOList(interventions);
    }

    public InterventionDTO update(Long id, InterventionDTO dto) {
        log.info("Updating intervention with id: {}", id);
        Intervention intervention = interventionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Intervention", "id", id));

        if (dto.getEquipementId() != null) {
            Equipement equipement = equipementRepository.findById(dto.getEquipementId())
                    .orElseThrow(() -> new ResourceNotFoundException("Equipement", "id", dto.getEquipementId()));
            intervention.setEquipement(equipement);
        }

        if (dto.getTechnicienId() != null) {
            Technicien technicien = technicienRepository.findById(dto.getTechnicienId())
                    .orElseThrow(() -> new ResourceNotFoundException("Technicien", "id", dto.getTechnicienId()));
            intervention.setTechnicien(technicien);
        }

        interventionMapper.updateEntityFromDTO(dto, intervention);
        Intervention updated = interventionRepository.save(intervention);
        return interventionMapper.toDTO(updated);
    }

    public void delete(Long id) {
        log.info("Deleting intervention with id: {}", id);
        Intervention intervention = interventionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Intervention", "id", id));
        interventionRepository.delete(intervention);
    }

    public List<InterventionDTO> getByStatut(String statut) {
        log.info("Getting interventions by statut: {}", statut);
        Intervention.StatutIntervention statutEnum = 
                Intervention.StatutIntervention.valueOf(statut.toUpperCase().replace("EE", ""));
        List<Intervention> interventions = interventionRepository.findByStatut(statutEnum);
        return interventionMapper.toDTOList(interventions);
    }

    public List<InterventionDTO> getByEquipementId(Long equipementId) {
        log.info("Getting interventions for equipement with id: {}", equipementId);
        List<Intervention> interventions = interventionRepository.findByEquipementId(equipementId);
        return interventionMapper.toDTOList(interventions);
    }

    public List<InterventionDTO> getByTechnicienId(Long technicienId) {
        log.info("Getting interventions for technicien with id: {}", technicienId);
        List<Intervention> interventions = interventionRepository.findByTechnicienId(technicienId);
        return interventionMapper.toDTOList(interventions);
    }

    public InterventionDTO assignTechnicien(Long interventionId, Long technicienId) {
        log.info("Assigning technicien {} to intervention {}", technicienId, interventionId);

        Intervention intervention = interventionRepository.findById(interventionId)
                .orElseThrow(() -> new ResourceNotFoundException("Intervention", "id", interventionId));

        Technicien technicien = technicienRepository.findById(technicienId)
                .orElseThrow(() -> new ResourceNotFoundException("Technicien", "id", technicienId));

        if (!technicien.getDisponibilite().equals(Technicien.DisponibiliteStatut.DISPONIBLE)) {
            throw new BadRequestException("Technicien is not available");
        }

        intervention.setTechnicien(technicien);
        Intervention updated = interventionRepository.save(intervention);
        return interventionMapper.toDTO(updated);
    }

    public InterventionDTO changeStatut(Long id, String nouveauStatut) {
        log.info("Changing intervention statut with id: {} to {}", id, nouveauStatut);
        Intervention intervention = interventionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Intervention", "id", id));
        intervention.setStatut(
                Intervention.StatutIntervention.valueOf(nouveauStatut.toUpperCase().replace("EE", ""))
        );
        Intervention updated = interventionRepository.save(intervention);
        return interventionMapper.toDTO(updated);
    }

    public Long countByStatut(String statut) {
        Intervention.StatutIntervention statutEnum = 
                Intervention.StatutIntervention.valueOf(statut.toUpperCase().replace("EE", ""));
        return interventionRepository.countByStatut(statutEnum);
    }

    public BigDecimal getTotalCost() {
        return interventionRepository.sumTotalCout();
    }

    public Double getAverageCost() {
        return interventionRepository.avgCout();
    }
}
