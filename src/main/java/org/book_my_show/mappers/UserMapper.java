package org.book_my_show.mappers;

import org.book_my_show.domain.user.Email;
import org.book_my_show.domain.user.PhoneNumber;
import org.book_my_show.domain.user.User;
import org.book_my_show.domain.user.UserName;
import org.book_my_show.dto.UserDTO;
import org.book_my_show.dto.UserRegistrationRequestDTO;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public abstract class UserMapper implements BaseMapper<UserDTO, User> {

    @Autowired
    protected UserDeviceMapper userDeviceMapper;

    /*@Mapping(target = "email", expression = "java(Email.of(dto.getEmail()))")
    public abstract User toEntity(UserDTO dto);*/

    @Override
    @Mapping(target = "email", expression = "java(mapEmail(user))")
    @Mapping(target = "phone", expression = "java(mapPhone(user))")
    @Mapping(target = "name", expression = "java(mapUserName(user))")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "userId", expression = "java(user.getId())")
    public abstract UserDTO toDto(User user);

    protected String mapEmail(User user) {
        Email email = user.getEmail();
        return email != null ? email.getEmailId() : null;
    }

    protected String mapPhone(User user) {
        PhoneNumber phone = user.getPhone();
        return phone != null ? phone.getPhoneNumber() : null;
    }

    protected String mapUserName(User user) {
        UserName userName = user.getName();
        return userName != null ? userName.getUserName() : null;
    }

    public User createFromRegistrationRequest(UserRegistrationRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = new User(
                new Email(dto.getEmail()),
                new PhoneNumber(dto.getPhone()),
                new UserName(dto.getName()),
                dto.getPassword()
        );

        if (dto.getPreferences() != null) {
            user.getPreferences().addAll(dto.getPreferences());
        }

        if (dto.getType() != null) {
            user.addUserType(dto.getType());
        }

        return user;
    }

    @Named("updateUser")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public void updateUser(@MappingTarget User user, UserDTO dto) {
        if (dto == null) {
            return;
        }

        // Update only mutable properties
        if (dto.getProfileImageUrl() != null) {
            user.setProfileImage(dto.getProfileImageUrl());
        }

        if (dto.getPreferences() != null) {
            user.getPreferences().clear();
            user.getPreferences().addAll(dto.getPreferences());
        }

        if (dto.getLastLogin() != null) {
            user.updateLastLogin(dto.getLastLogin());
        }

        // Status update with validation
        if (dto.getStatus() != null && user.getUserStatus() != dto.getStatus()) {
            // Add appropriate status change logic based on your business rules
            user.changeUserStatus(dto.getStatus());
        }
    }

    @Named("mapForPasswordChange")
    public void mapForPasswordChange(@MappingTarget User user, String newPassword) {
        user.updateNewPassword(newPassword);
    }

    @Named("updatePreferences")
    public void updatePreferences(@MappingTarget User user, Set<String> preferences) {
        if (preferences != null) {
            user.getPreferences().clear();
            user.getPreferences().addAll(preferences);
        }
    }
}
