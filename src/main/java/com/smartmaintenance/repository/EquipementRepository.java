package com.smartmaintenance.repository;

import com.smartmaintenance.entity.Equipement;
import com.smartmaintenance.entity.Equipement.EtatEquipement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EquipementRepository extends JpaRepository<Equipement, Long> {

    List<Equipement> findByEtat(EtatEquipement etat);

    @Query("SELECT e FROM Equipement e WHERE e.etat = ?1")
    List<Equipement> findEquipementsByEtat(EtatEquipement etat);

    @Query(value = "SELECT e.*, COUNT(p.id) as panne_count FROM equipements e " +
            "LEFT JOIN pannes p ON e.id = p.equipement_id " +
            "GROUP BY e.id ORDER BY panne_count DESC LIMIT 5", nativeQuery = true)
    List<Equipement> findTop5EquipementsWithMostPannes();
}
