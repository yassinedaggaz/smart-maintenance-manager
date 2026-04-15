package com.smartmaintenance.service;

import com.smartmaintenance.dto.PanneDTO;
import com.smartmaintenance.entity.Panne;
import com.smartmaintenance.exception.ResourceNotFoundException;
import com.smartmaintenance.mapper.PanneMapper;
import com.smartmaintenance.repository.PanneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class PanneService {

    @Autowired
    private PanneRepository panneRepository;

    @Autowired
    private PanneMapper panneMapper;

    public PanneDTO create(PanneDTO dto) {
        log.info("Creating new panne");
        Panne panne = panneMapper.toEntity(dto);
        Panne saved = panneRepository.save(panne);
        return panneMapper.toDTO(saved);
    }

    public PanneDTO getById(Long id) {
        log.info("Getting panne with id: {}", id);
        Panne panne = panneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Panne", "id", id));
        return panneMapper.toDTO(panne);
    }

    public List<PanneDTO> getAll() {
        log.info("Getting all pannes");
        List<Panne> pannes = panneRepository.findAll();
        return panneMapper.toDTOList(pannes);
    }

    public PanneDTO update(Long id, PanneDTO dto) {
        log.info("Updating panne with id: {}", id);
        Panne panne = panneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Panne", "id", id));
        panneMapper.updateEntityFromDTO(dto, panne);
        Panne updated = panneRepository.save(panne);
        return panneMapper.toDTO(updated);
    }

    public void delete(Long id) {
        log.info("Deleting panne with id: {}", id);
        Panne panne = panneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Panne", "id", id));
        panneRepository.delete(panne);
    }

    public List<PanneDTO> getByStatut(String statut) {
        log.info("Getting pannes by statut: {}", statut);
        Panne.StatutPanne statutEnum = Panne.StatutPanne.valueOf(statut.toUpperCase());
        List<Panne> pannes = panneRepository.findByStatut(statutEnum);
        return panneMapper.toDTOList(pannes);
    }

    public List<PanneDTO> getByEquipementId(Long equipementId) {
        log.info("Getting pannes for equipement with id: {}", equipementId);
        List<Panne> pannes = panneRepository.findByEquipementId(equipementId);
        return panneMapper.toDTOList(pannes);
    }

    public PanneDTO changeStatut(Long id, String nouveauStatut) {
        log.info("Changing panne statut with id: {} to {}", id, nouveauStatut);
        Panne panne = panneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Panne", "id", id));
        panne.setStatut(Panne.StatutPanne.valueOf(nouveauStatut.toUpperCase()));
        Panne updated = panneRepository.save(panne);
        return panneMapper.toDTO(updated);
    }

    public Long countByStatut(String statut) {
        Panne.StatutPanne statutEnum = Panne.StatutPanne.valueOf(statut.toUpperCase());
        return panneRepository.countByStatut(statutEnum);
    }
}
