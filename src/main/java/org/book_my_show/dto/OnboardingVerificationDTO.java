package org.book_my_show.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.book_my_show.domain.status.VerificationStatus;
import org.book_my_show.domain.status.VerificationType;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnboardingVerificationDTO extends BaseDTO{
    private Long theatreOnboardingId;
    private VerificationType type;
    private VerificationStatus status;
    private String verifiedBy;
    private LocalDateTime verificationDate;
    private String comments;
}
