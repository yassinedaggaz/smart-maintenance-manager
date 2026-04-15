package com.smartmaintenance.service;

import com.smartmaintenance.dto.TechnicienDTO;
import com.smartmaintenance.entity.Technicien;
import com.smartmaintenance.exception.BadRequestException;
import com.smartmaintenance.exception.ResourceNotFoundException;
import com.smartmaintenance.mapper.TechnicienMapper;
import com.smartmaintenance.repository.TechnicienRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class TechnicienService {

    @Autowired
    private TechnicienRepository technicienRepository;

    @Autowired
    private TechnicienMapper technicienMapper;

    public TechnicienDTO create(TechnicienDTO dto) {
        log.info("Creating new technicien: {}", dto.getFullName());

        if (technicienRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists");
        }

        Technicien technicien = technicienMapper.toEntity(dto);
        Technicien saved = technicienRepository.save(technicien);
        return technicienMapper.toDTO(saved);
    }

    public TechnicienDTO getById(Long id) {
        log.info("Getting technicien with id: {}", id);
        Technicien technicien = technicienRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Technicien", "id", id));
        return technicienMapper.toDTO(technicien);
    }

    public List<TechnicienDTO> getAll() {
        log.info("Getting all techniciens");
        List<Technicien> techniciens = technicienRepository.findAll();
        return technicienMapper.toDTOList(techniciens);
    }

    public TechnicienDTO update(Long id, TechnicienDTO dto) {
        log.info("Updating technicien with id: {}", id);
        Technicien technicien = technicienRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Technicien", "id", id));

        if (!technicien.getEmail().equals(dto.getEmail()) && 
            technicienRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists");
        }

        technicienMapper.updateEntityFromDTO(dto, technicien);
        Technicien updated = technicienRepository.save(technicien);
        return technicienMapper.toDTO(updated);
    }

    public void delete(Long id) {
        log.info("Deleting technicien with id: {}", id);
        Technicien technicien = technicienRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Technicien", "id", id));
        technicienRepository.delete(technicien);
    }

    public List<TechnicienDTO> getAvailable() {
        log.info("Getting available techniciens");
        List<Technicien> techniciens = technicienRepository
                .findByDisponibilite(Technicien.DisponibiliteStatut.DISPONIBLE);
        return technicienMapper.toDTOList(techniciens);
    }

    public TechnicienDTO changeDisponibilite(Long id, String nouvelleDisponibilite) {
        log.info("Changing technicien disponibilite with id: {} to {}", id, nouvelleDisponibilite);
        Technicien technicien = technicienRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Technicien", "id", id));
        technicien.setDisponibilite(
                Technicien.DisponibiliteStatut.valueOf(nouvelleDisponibilite.toUpperCase())
        );
        Technicien updated = technicienRepository.save(technicien);
        return technicienMapper.toDTO(updated);
    }

    public List<TechnicienDTO> getTechniciensByInterventionCount() {
        log.info("Getting techniciens by intervention count");
        List<Technicien> techniciens = technicienRepository.findTechniciensByInterventionCount();
        return technicienMapper.toDTOList(techniciens);
    }
}
