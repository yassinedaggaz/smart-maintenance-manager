package com.smartmaintenance.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import com.smartmaintenance.dto.EquipementDTO;
import com.smartmaintenance.entity.Equipement;
import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface EquipementMapper {

    @Mapping(target = "etat", source = "etat")
    EquipementDTO toDTO(Equipement entity);

    @Mapping(target = "etat", source = "etat")
    Equipement toEntity(EquipementDTO dto);

    List<EquipementDTO> toDTOList(List<Equipement> entities);

    List<Equipement> toEntityList(List<EquipementDTO> dtos);

    void updateEntityFromDTO(EquipementDTO dto, @MappingTarget Equipement entity);
}
