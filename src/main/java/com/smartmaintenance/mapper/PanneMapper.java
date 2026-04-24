package com.smartmaintenance.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import com.smartmaintenance.dto.PanneDTO;
import com.smartmaintenance.entity.Panne;
import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PanneMapper {

    @Mapping(target = "equipementId", source = "equipement.id")
    @Mapping(target = "equipementNom", source = "equipement.nom")
    @Mapping(target = "categorie", source = "categorie")
    @Mapping(target = "statut", source = "statut")
    PanneDTO toDTO(Panne entity);

    @Mapping(target = "equipement.id", source = "equipementId")
    @Mapping(target = "categorie", source = "categorie")
    @Mapping(target = "statut", source = "statut")
    Panne toEntity(PanneDTO dto);

    List<PanneDTO> toDTOList(List<Panne> entities);

    List<Panne> toEntityList(List<PanneDTO> dtos);

    void updateEntityFromDTO(PanneDTO dto, @MappingTarget Panne entity);
}
