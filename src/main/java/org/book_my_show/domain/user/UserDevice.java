package org.book_my_show.domain.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.book_my_show.domain.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_devices")
@Getter @Setter
public class UserDevice extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String deviceId;

    private String deviceType;

    private String fcmToken;

    @Column(name = "last_used")
    private LocalDateTime lastUsed;

    @Column(name = "is_active")
    private boolean active = true;
}
