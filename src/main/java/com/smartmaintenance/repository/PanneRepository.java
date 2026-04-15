package com.smartmaintenance.repository;

import com.smartmaintenance.entity.Panne;
import com.smartmaintenance.entity.Panne.StatutPanne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PanneRepository extends JpaRepository<Panne, Long> {

    List<Panne> findByStatut(StatutPanne statut);

    List<Panne> findByEquipementId(Long equipementId);

    @Query("SELECT COUNT(p) FROM Panne p WHERE p.statut = ?1")
    Long countByStatut(StatutPanne statut);

    @Query("SELECT p FROM Panne p WHERE p.equipement.id = ?1 AND p.statut = ?2")
    List<Panne> findPannesByEquipementAndStatut(Long equipementId, StatutPanne statut);
}
