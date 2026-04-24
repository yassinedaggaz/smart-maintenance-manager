package com.smartmaintenance.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import com.smartmaintenance.dto.TechnicienDTO;
import com.smartmaintenance.entity.Technicien;
import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TechnicienMapper {

    @Mapping(target = "disponibilite", source = "disponibilite")
    TechnicienDTO toDTO(Technicien entity);

    @Mapping(target = "disponibilite", source = "disponibilite")
    Technicien toEntity(TechnicienDTO dto);

    List<TechnicienDTO> toDTOList(List<Technicien> entities);

    List<Technicien> toEntityList(List<TechnicienDTO> dtos);

    void updateEntityFromDTO(TechnicienDTO dto, @MappingTarget Technicien entity);
}
