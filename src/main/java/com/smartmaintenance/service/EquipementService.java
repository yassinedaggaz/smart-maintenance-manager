package com.smartmaintenance.service;

import com.smartmaintenance.dto.EquipementDTO;
import com.smartmaintenance.entity.Equipement;
import com.smartmaintenance.exception.ResourceNotFoundException;
import com.smartmaintenance.mapper.EquipementMapper;
import com.smartmaintenance.repository.EquipementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class EquipementService {

    @Autowired
    private EquipementRepository equipementRepository;

    @Autowired
    private EquipementMapper equipementMapper;

    public EquipementDTO create(EquipementDTO dto) {
        log.info("Creating new equipement: {}", dto.getNom());
        Equipement equipement = equipementMapper.toEntity(dto);
        Equipement saved = equipementRepository.save(equipement);
        return equipementMapper.toDTO(saved);
    }

    public EquipementDTO getById(Long id) {
        log.info("Getting equipement with id: {}", id);
        Equipement equipement = equipementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", "id", id));
        return equipementMapper.toDTO(equipement);
    }

    public List<EquipementDTO> getAll() {
        log.info("Getting all equipements");
        List<Equipement> equipements = equipementRepository.findAll();
        return equipementMapper.toDTOList(equipements);
    }

    public EquipementDTO update(Long id, EquipementDTO dto) {
        log.info("Updating equipement with id: {}", id);
        Equipement equipement = equipementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", "id", id));
        equipementMapper.updateEntityFromDTO(dto, equipement);
        Equipement updated = equipementRepository.save(equipement);
        return equipementMapper.toDTO(updated);
    }

    public void delete(Long id) {
        log.info("Deleting equipement with id: {}", id);
        Equipement equipement = equipementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", "id", id));
        equipementRepository.delete(equipement);
    }

    public List<EquipementDTO> getByEtat(String etat) {
        log.info("Getting equipements by etat: {}", etat);
        Equipement.EtatEquipement etatEnum = Equipement.EtatEquipement.valueOf(etat.toUpperCase());
        List<Equipement> equipements = equipementRepository.findByEtat(etatEnum);
        return equipementMapper.toDTOList(equipements);
    }

    public List<EquipementDTO> getTop5WithMostPannes() {
        log.info("Getting top 5 equipements with most pannes");
        List<Equipement> equipements = equipementRepository.findTop5EquipementsWithMostPannes();
        return equipementMapper.toDTOList(equipements);
    }

    public EquipementDTO changeEtat(Long id, String nouvelEtat) {
        log.info("Changing equipement etat with id: {} to {}", id, nouvelEtat);
        Equipement equipement = equipementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", "id", id));
        equipement.setEtat(Equipement.EtatEquipement.valueOf(nouvelEtat.toUpperCase()));
        Equipement updated = equipementRepository.save(equipement);
        return equipementMapper.toDTO(updated);
    }
}
