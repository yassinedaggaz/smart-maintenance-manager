package com.smartmaintenance.controller;

import com.smartmaintenance.dto.ApiResponse;
import com.smartmaintenance.dto.PanneDTO;
import com.smartmaintenance.service.PanneService;
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
@RequestMapping("/api/v1/pannes")
@Tag(name = "Pannes", description = "Panne management endpoints")
@Slf4j
public class PanneController {

    @Autowired
    private PanneService panneService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('TECHNICIEN')")
    @Operation(summary = "Create a new panne")
    public ResponseEntity<ApiResponse<PanneDTO>> create(@Valid @RequestBody PanneDTO dto) {
        log.info("Creating new panne");
        PanneDTO created = panneService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Panne created successfully", created));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get panne by id")
    public ResponseEntity<ApiResponse<PanneDTO>> getById(@PathVariable Long id) {
        log.info("Getting panne with id: {}", id);
        PanneDTO panne = panneService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(panne));
    }

    @GetMapping
    @Operation(summary = "Get all pannes")
    public ResponseEntity<ApiResponse<List<PanneDTO>>> getAll() {
        log.info("Getting all pannes");
        List<PanneDTO> pannes = panneService.getAll();
        return ResponseEntity.ok(ApiResponse.success(pannes));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update panne")
    public ResponseEntity<ApiResponse<PanneDTO>> update(@PathVariable Long id, @Valid @RequestBody PanneDTO dto) {
        log.info("Updating panne with id: {}", id);
        PanneDTO updated = panneService.update(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Panne updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete panne")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        log.info("Deleting panne with id: {}", id);
        panneService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Panne deleted successfully", null));
    }

    @GetMapping("/statut/{statut}")
    @Operation(summary = "Get pannes by statut")
    public ResponseEntity<ApiResponse<List<PanneDTO>>> getByStatut(@PathVariable String statut) {
        log.info("Getting pannes by statut: {}", statut);
        List<PanneDTO> pannes = panneService.getByStatut(statut);
        return ResponseEntity.ok(ApiResponse.success(pannes));
    }

    @GetMapping("/equipement/{equipementId}")
    @Operation(summary = "Get pannes by equipement")
    public ResponseEntity<ApiResponse<List<PanneDTO>>> getByEquipementId(@PathVariable Long equipementId) {
        log.info("Getting pannes for equipement with id: {}", equipementId);
        List<PanneDTO> pannes = panneService.getByEquipementId(equipementId);
        return ResponseEntity.ok(ApiResponse.success(pannes));
    }

    @PutMapping("/{id}/statut/{nouveauStatut}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Change panne statut")
    public ResponseEntity<ApiResponse<PanneDTO>> changeStatut(@PathVariable Long id,
            @PathVariable String nouveauStatut) {
        log.info("Changing panne statut with id: {} to {}", id, nouveauStatut);
        PanneDTO updated = panneService.changeStatut(id, nouveauStatut);
        return ResponseEntity.ok(ApiResponse.success("Panne statut updated successfully", updated));
    }
}
