package com.smartmaintenance.repository;

import com.smartmaintenance.entity.Intervention;
import com.smartmaintenance.entity.Intervention.StatutIntervention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface InterventionRepository extends JpaRepository<Intervention, Long> {

    List<Intervention> findByStatut(StatutIntervention statut);

    List<Intervention> findByEquipementId(Long equipementId);

    List<Intervention> findByTechnicienId(Long technicienId);

    @Query("SELECT COUNT(i) FROM Intervention i WHERE i.statut = ?1")
    Long countByStatut(StatutIntervention statut);

    @Query("SELECT SUM(i.cout) FROM Intervention i WHERE i.statut = ?1")
    BigDecimal sumCoutByStatut(StatutIntervention statut);

    @Query("SELECT SUM(i.cout) FROM Intervention i")
    BigDecimal sumTotalCout();

    @Query("SELECT AVG(i.cout) FROM Intervention i WHERE i.cout IS NOT NULL")
    Double avgCout();
}
