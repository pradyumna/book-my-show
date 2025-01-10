package org.book_my_show.domain.onboard;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.book_my_show.domain.BaseEntity;
import org.book_my_show.domain.status.VerificationStatus;
import org.book_my_show.domain.status.VerificationType;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "onboarding_verification")
@Getter @Setter
public class OnboardingVerification extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "theatre_onboarding_id")
    private TheatreOnboarding theatreOnboarding;

    @Enumerated(EnumType.STRING)
    private VerificationType type;

    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;

    private String verifiedBy;

    private LocalDateTime verificationDate;

    private String comments;

    public OnboardingVerification(){}

    protected OnboardingVerification(TheatreOnboarding onboarding, VerificationType type) {
        this.theatreOnboarding = Objects.requireNonNull(onboarding);
        this.type = Objects.requireNonNull(type);
    }

    public void updateStatus(VerificationStatus newStatus, String verifiedBy, String comments) {
        validateStatusTransition(newStatus);
        this.verificationStatus = newStatus;
        this.verifiedBy = verifiedBy;
        this.comments = comments;
        this.verificationDate = LocalDateTime.now();
    }

    private void validateStatusTransition(VerificationStatus newStatus) {
        if (this.verificationStatus == VerificationStatus.APPROVED ||
                this.verificationStatus == VerificationStatus.REJECTED) {
            throw new IllegalStateException("Cannot change status once finalized");
        }
    }
}
