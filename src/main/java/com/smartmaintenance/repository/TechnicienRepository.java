package com.smartmaintenance.repository;

import com.smartmaintenance.entity.Technicien;
import com.smartmaintenance.entity.Technicien.DisponibiliteStatut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TechnicienRepository extends JpaRepository<Technicien, Long> {

    Optional<Technicien> findByEmail(String email);

    List<Technicien> findByDisponibilite(DisponibiliteStatut disponibilite);

    @Query("SELECT t FROM Technicien t WHERE t.disponibilite = ?1")
    List<Technicien> findAvailableTechniciens(DisponibiliteStatut statut);

    @Query(value = "SELECT t.*, COUNT(i.id) as intervention_count FROM techniciens t " +
            "LEFT JOIN interventions i ON t.id = i.technicien_id " +
            "GROUP BY t.id ORDER BY intervention_count DESC", nativeQuery = true)
    List<Technicien> findTechniciensByInterventionCount();
}
