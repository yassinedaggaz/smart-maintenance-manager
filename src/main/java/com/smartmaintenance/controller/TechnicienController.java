package com.smartmaintenance.controller;

import com.smartmaintenance.dto.ApiResponse;
import com.smartmaintenance.dto.TechnicienDTO;
import com.smartmaintenance.service.TechnicienService;
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
@RequestMapping("/api/v1/techniciens")
@Tag(name = "Techniciens", description = "Technicien management endpoints")
@Slf4j
public class TechnicienController {

    @Autowired
    private TechnicienService technicienService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new technicien")
    public ResponseEntity<ApiResponse<TechnicienDTO>> create(@Valid @RequestBody TechnicienDTO dto) {
        log.info("Creating new technicien: {}", dto.getFullName());
        TechnicienDTO created = technicienService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Technicien created successfully", created));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get technicien by id")
    public ResponseEntity<ApiResponse<TechnicienDTO>> getById(@PathVariable Long id) {
        log.info("Getting technicien with id: {}", id);
        TechnicienDTO technicien = technicienService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(technicien));
    }

    @GetMapping
    @Operation(summary = "Get all techniciens")
    public ResponseEntity<ApiResponse<List<TechnicienDTO>>> getAll() {
        log.info("Getting all techniciens");
        List<TechnicienDTO> techniciens = technicienService.getAll();
        return ResponseEntity.ok(ApiResponse.success(techniciens));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update technicien")
    public ResponseEntity<ApiResponse<TechnicienDTO>> update(@PathVariable Long id, @Valid @RequestBody TechnicienDTO dto) {
        log.info("Updating technicien with id: {}", id);
        TechnicienDTO updated = technicienService.update(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Technicien updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete technicien")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        log.info("Deleting technicien with id: {}", id);
        technicienService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Technicien deleted successfully", null));
    }

    @GetMapping("/available")
    @Operation(summary = "Get available techniciens")
    public ResponseEntity<ApiResponse<List<TechnicienDTO>>> getAvailable() {
        log.info("Getting available techniciens");
        List<TechnicienDTO> techniciens = technicienService.getAvailable();
        return ResponseEntity.ok(ApiResponse.success(techniciens));
    }

    @PutMapping("/{id}/disponibilite/{nouvelleDisponibilite}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Change technicien disponibilite")
    public ResponseEntity<ApiResponse<TechnicienDTO>> changeDisponibilite(@PathVariable Long id, @PathVariable String nouvelleDisponibilite) {
        log.info("Changing technicien disponibilite with id: {} to {}", id, nouvelleDisponibilite);
        TechnicienDTO updated = technicienService.changeDisponibilite(id, nouvelleDisponibilite);
        return ResponseEntity.ok(ApiResponse.success("Technicien disponibilite updated successfully", updated));
    }

    @GetMapping("/workload")
    @Operation(summary = "Get techniciens by workload")
    public ResponseEntity<ApiResponse<List<TechnicienDTO>>> getTechniciensByWorkload() {
        log.info("Getting techniciens by workload");
        List<TechnicienDTO> techniciens = technicienService.getTechniciensByInterventionCount();
        return ResponseEntity.ok(ApiResponse.success(techniciens));
    }
}
