package com.smartmaintenance.controller;

import com.smartmaintenance.dto.ApiResponse;
import com.smartmaintenance.dto.InterventionDTO;
import com.smartmaintenance.service.InterventionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/interventions")
@Tag(name = "Interventions", description = "Intervention management endpoints")
@Slf4j
public class InterventionController {

    @Autowired
    private InterventionService interventionService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new intervention")
    public ResponseEntity<ApiResponse<InterventionDTO>> create(@Valid @RequestBody InterventionDTO dto) {
        log.info("Creating new intervention");
        InterventionDTO created = interventionService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Intervention created successfully", created));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get intervention by id")
    public ResponseEntity<ApiResponse<InterventionDTO>> getById(@PathVariable Long id) {
        log.info("Getting intervention with id: {}", id);
        InterventionDTO intervention = interventionService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(intervention));
    }

    @GetMapping
    @Operation(summary = "Get all interventions")
    public ResponseEntity<ApiResponse<List<InterventionDTO>>> getAll() {
        log.info("Getting all interventions");
        List<InterventionDTO> interventions = interventionService.getAll();
        return ResponseEntity.ok(ApiResponse.success(interventions));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update intervention")
    public ResponseEntity<ApiResponse<InterventionDTO>> update(@PathVariable Long id, @Valid @RequestBody InterventionDTO dto) {
        log.info("Updating intervention with id: {}", id);
        InterventionDTO updated = interventionService.update(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Intervention updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete intervention")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        log.info("Deleting intervention with id: {}", id);
        interventionService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Intervention deleted successfully", null));
    }

    @GetMapping("/statut/{statut}")
    @Operation(summary = "Get interventions by statut")
    public ResponseEntity<ApiResponse<List<InterventionDTO>>> getByStatut(@PathVariable String statut) {
        log.info("Getting interventions by statut: {}", statut);
        List<InterventionDTO> interventions = interventionService.getByStatut(statut);
        return ResponseEntity.ok(ApiResponse.success(interventions));
    }

    @GetMapping("/equipement/{equipementId}")
    @Operation(summary = "Get interventions by equipement")
    public ResponseEntity<ApiResponse<List<InterventionDTO>>> getByEquipementId(@PathVariable Long equipementId) {
        log.info("Getting interventions for equipement with id: {}", equipementId);
        List<InterventionDTO> interventions = interventionService.getByEquipementId(equipementId);
        return ResponseEntity.ok(ApiResponse.success(interventions));
    }

    @GetMapping("/technicien/{technicienId}")
    @Operation(summary = "Get interventions by technicien")
    public ResponseEntity<ApiResponse<List<InterventionDTO>>> getByTechnicienId(@PathVariable Long technicienId) {
        log.info("Getting interventions for technicien with id: {}", technicienId);
        List<InterventionDTO> interventions = interventionService.getByTechnicienId(technicienId);
        return ResponseEntity.ok(ApiResponse.success(interventions));
    }

    @PostMapping("/{interventionId}/assign/{technicienId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Assign technicien to intervention")
    public ResponseEntity<ApiResponse<InterventionDTO>> assignTechnicien(@PathVariable Long interventionId, @PathVariable Long technicienId) {
        log.info("Assigning technicien {} to intervention {}", technicienId, interventionId);
        InterventionDTO updated = interventionService.assignTechnicien(interventionId, technicienId);
        return ResponseEntity.ok(ApiResponse.success("Technicien assigned successfully", updated));
    }

    @PutMapping("/{id}/statut/{nouveauStatut}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Change intervention statut")
    public ResponseEntity<ApiResponse<InterventionDTO>> changeStatut(@PathVariable Long id, @PathVariable String nouveauStatut) {
        log.info("Changing intervention statut with id: {} to {}", id, nouveauStatut);
        InterventionDTO updated = interventionService.changeStatut(id, nouveauStatut);
        return ResponseEntity.ok(ApiResponse.success("Intervention statut updated successfully", updated));
    }
}
