package org.book_my_show.mappers;

import org.book_my_show.domain.onboard.BusinessName;
import org.book_my_show.domain.onboard.RegistrationNumber;
import org.book_my_show.domain.onboard.TaxIdentificationNumber;
import org.book_my_show.domain.theatre.Theatre;
import org.book_my_show.domain.theatre.TheatreName;
import org.book_my_show.dto.TheatreOnboardingDTO;
import org.book_my_show.domain.onboard.TheatreOnboarding;
import org.mapstruct.*;

import java.util.Objects;

@Mapper(componentModel = "spring")
public abstract class TheatreOnboardingMapper {

    @Mapping(target = "theatre", source = "theatre.name.theatreName")
    @Mapping(target = "theatreId", source = "theatre.id")
    @Mapping(target = "businessName", source = "businessName.businessName")
    @Mapping(target = "registrationNumber", source = "registrationNumber.regNum")
    @Mapping(target = "taxIdentificationNumber", source = "taxIdentificationNumber.taxId")
    public abstract TheatreOnboardingDTO toDto(TheatreOnboarding entity);

    @Mapping(target = "theatre", source = ".", qualifiedByName = "createTheatre")
    @Mapping(target = "businessName", source = "businessName", qualifiedByName = "toBusinessName")
    @Mapping(target = "registrationNumber", source = "registrationNumber", qualifiedByName = "toRegistrationNumber")
    @Mapping(target = "taxIdentificationNumber", source = "taxIdentificationNumber", qualifiedByName = "toTaxId")
    @Mapping(target = "verifications", ignore = true)
    public abstract TheatreOnboarding toEntity(TheatreOnboardingDTO dto);

    @Named("createTheatre")
    protected Theatre createTheatre(TheatreOnboardingDTO dto) {
        Theatre theatre = new Theatre(new TheatreName(dto.getTheatre()));
        if (dto.getTheatreId() != null) {
            theatre.setId(dto.getTheatreId());
        }
        return theatre;
    }

    @Named("toBusinessName")
    protected BusinessName toBusinessName(String value) {
        return value != null ? new BusinessName(value) : null;
    }

    @Named("toRegistrationNumber")
    protected RegistrationNumber toRegistrationNumber(String value) {
        return value != null ? new RegistrationNumber(value) : null;
    }

    @Named("toTaxId")
    protected TaxIdentificationNumber toTaxId(String value) {
        return value != null ? new TaxIdentificationNumber(value) : null;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "theatre", source = ".", qualifiedByName = "updateTheatre")
    @Mapping(target = "businessName", source = "businessName", qualifiedByName = "toBusinessName")
    @Mapping(target = "registrationNumber", source = "registrationNumber", qualifiedByName = "toRegistrationNumber")
    @Mapping(target = "taxIdentificationNumber", source = "taxIdentificationNumber", qualifiedByName = "toTaxId")
    @Mapping(target = "verifications", ignore = true)
    public abstract void updateEntity(@MappingTarget TheatreOnboarding entity, TheatreOnboardingDTO dto);

    @Named("updateTheatre")
    protected Theatre updateTheatre(TheatreOnboardingDTO dto) {
        if (dto.getTheatre() == null) {
            return null;
        }
        return new Theatre(new TheatreName(dto.getTheatre()));
    }

    // Value Object Mappings
    @AfterMapping
    protected void mapValueObjects(TheatreOnboardingDTO dto, @MappingTarget TheatreOnboarding entity) {
        if (dto.getBusinessName() != null) {
            entity.setBusinessName(new BusinessName(dto.getBusinessName()));
        }
        if (dto.getRegistrationNumber() != null) {
            entity.setRegistrationNumber(new RegistrationNumber(dto.getRegistrationNumber()));
        }
        if (dto.getTaxIdentificationNumber() != null) {
            entity.setTaxIdentificationNumber(new TaxIdentificationNumber(dto.getTaxIdentificationNumber()));
        }
    }

    // Custom validation
    @BeforeMapping
    protected void validateDto(TheatreOnboardingDTO dto) {
        Objects.requireNonNull(dto, "DTO cannot be null");
    }
}
