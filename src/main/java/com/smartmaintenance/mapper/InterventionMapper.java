package com.smartmaintenance.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import com.smartmaintenance.dto.InterventionDTO;
import com.smartmaintenance.entity.Intervention;
import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface InterventionMapper {

    @Mapping(target = "equipementId", source = "equipement.id")
    @Mapping(target = "equipementNom", source = "equipement.nom")
    @Mapping(target = "technicienId", source = "technicien.id")
    @Mapping(target = "technicienNom", source = "technicien.fullName")
    @Mapping(target = "statut", source = "statut")
    InterventionDTO toDTO(Intervention entity);

    @Mapping(target = "equipement.id", source = "equipementId")
    @Mapping(target = "technicien.id", source = "technicienId")
    @Mapping(target = "statut", source = "statut")
    Intervention toEntity(InterventionDTO dto);

    List<InterventionDTO> toDTOList(List<Intervention> entities);

    List<Intervention> toEntityList(List<InterventionDTO> dtos);

    void updateEntityFromDTO(InterventionDTO dto, @MappingTarget Intervention entity);
}
