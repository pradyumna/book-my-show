package org.book_my_show.mappers;

import org.book_my_show.domain.user.UserDevice;
import org.book_my_show.dto.UserDeviceDTO;
import org.mapstruct.*;

import java.util.Set;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface UserDeviceMapper extends BaseMapper<UserDeviceDTO, UserDevice> {

    @Override
    @Mapping(target = "userId", source = "user.id")
    UserDeviceDTO toDto(UserDevice entity);

    @Named("toUserDeviceDtoSet")
    Set<UserDeviceDTO> toUserDeviceDtoSet(Set<UserDevice> devices);
}
