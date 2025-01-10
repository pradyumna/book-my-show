package org.book_my_show.mappers;

import org.book_my_show.dto.AddressDTO;
import org.book_my_show.domain.embed.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(target = "street", source = "street")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "state", source = "state")
    @Mapping(target = "country", source = "country")
    @Mapping(target = "pincode", source = "pincode")
    @Mapping(target = "location", source = "location")
    AddressDTO toDto(Address address);

    Address toDomain(AddressDTO dto);
}
