package org.book_my_show.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.book_my_show.domain.status.UserStatus;
import org.book_my_show.domain.status.UserType;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends BaseDTO {
    private Long userId;
    private String name;
    private String email;
    private String password;
    private String refreshToken;
    private String tokenExpiry;
    private String phone;
    private UserType type;
    private UserStatus status;
    private String profileImageUrl;
    private boolean emailVerified;
    private boolean phoneVerified;
    private LocalDateTime lastLogin;
    private Set<String> preferences;
}
