package org.book_my_show.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDeviceDTO extends BaseDTO{
    private Long userId;
    private String deviceId;
    private String deviceType;
    private String fcmToken;
    private LocalDateTime lastUsed;
    private boolean active;
}
