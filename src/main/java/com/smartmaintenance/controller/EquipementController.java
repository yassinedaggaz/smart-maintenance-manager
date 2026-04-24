package com.smartmaintenance.controller;

import com.smartmaintenance.dto.ApiResponse;
import com.smartmaintenance.dto.EquipementDTO;
import com.smartmaintenance.service.EquipementService;
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
@RequestMapping("/api/v1/equipements")
@Tag(name = "Equipements", description = "Equipement management endpoints")
@Slf4j
public class EquipementController {

    @Autowired
    private EquipementService equipementService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new equipement")
    public ResponseEntity<ApiResponse<EquipementDTO>> create(@Valid @RequestBody EquipementDTO dto) {
        log.info("Creating new equipement: {}", dto.getNom());
        EquipementDTO created = equipementService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Equipement created successfully", created));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get equipement by id")
    public ResponseEntity<ApiResponse<EquipementDTO>> getById(@PathVariable Long id) {
        log.info("Getting equipement with id: {}", id);
        EquipementDTO equipement = equipementService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(equipement));
    }

    @GetMapping
    @Operation(summary = "Get all equipements")
    public ResponseEntity<ApiResponse<List<EquipementDTO>>> getAll() {
        log.info("Getting all equipements");
        List<EquipementDTO> equipements = equipementService.getAll();
        return ResponseEntity.ok(ApiResponse.success(equipements));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update equipement")
    public ResponseEntity<ApiResponse<EquipementDTO>> update(@PathVariable Long id, @Valid @RequestBody EquipementDTO dto) {
        log.info("Updating equipement with id: {}", id);
        EquipementDTO updated = equipementService.update(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Equipement updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete equipement")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        log.info("Deleting equipement with id: {}", id);
        equipementService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Equipement deleted successfully", null));
    }

    @GetMapping("/etat/{etat}")
    @Operation(summary = "Get equipements by etat")
    public ResponseEntity<ApiResponse<List<EquipementDTO>>> getByEtat(@PathVariable String etat) {
        log.info("Getting equipements by etat: {}", etat);
        List<EquipementDTO> equipements = equipementService.getByEtat(etat);
        return ResponseEntity.ok(ApiResponse.success(equipements));
    }

    @GetMapping("/top/pannes")
    @Operation(summary = "Get top 5 equipements with most pannes")
    public ResponseEntity<ApiResponse<List<EquipementDTO>>> getTop5WithMostPannes() {
        log.info("Getting top 5 equipements with most pannes");
        List<EquipementDTO> equipements = equipementService.getTop5WithMostPannes();
        return ResponseEntity.ok(ApiResponse.success(equipements));
    }

    @PutMapping("/{id}/etat/{nouvelEtat}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Change equipement etat")
    public ResponseEntity<ApiResponse<EquipementDTO>> changeEtat(@PathVariable Long id, @PathVariable String nouvelEtat) {
        log.info("Changing equipement etat with id: {} to {}", id, nouvelEtat);
        EquipementDTO updated = equipementService.changeEtat(id, nouvelEtat);
        return ResponseEntity.ok(ApiResponse.success("Equipement etat updated successfully", updated));
    }
}
