package org.book_my_show.mappers;

import org.book_my_show.domain.BaseEntity;
import org.book_my_show.dto.BaseDTO;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;
import java.time.temporal.Temporal;

@MapperConfig(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG
)
public interface BaseMapper<D extends BaseDTO, E extends BaseEntity> {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    D toDto(E entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "version", ignore = true) // Version is managed by JPA
    E toDomain(D dto);

    // Common mapping methods for temporal types
    default LocalDateTime map(Temporal temporal) {
        if (temporal == null) {
            return null;
        }
        return LocalDateTime.from(temporal);
    }
}
