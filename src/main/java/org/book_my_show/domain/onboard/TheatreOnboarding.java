package org.book_my_show.domain.onboard;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.book_my_show.domain.BaseEntity;
import org.book_my_show.domain.status.OnboardingStatus;
import org.book_my_show.domain.status.VerificationStatus;
import org.book_my_show.domain.status.VerificationType;
import org.book_my_show.domain.theatre.Theatre;
import org.book_my_show.domain.theatre.TheatreName;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "theatre_onboarding")
@Getter @Setter
public class TheatreOnboarding extends BaseEntity {
    @OneToOne(cascade = CascadeType.ALL)
    private Theatre theatre;

    @Embedded
    private BusinessName businessName;

    @Embedded
    private RegistrationNumber registrationNumber;

    @Embedded
    private TaxIdentificationNumber taxIdentificationNumber;

    @OneToMany(mappedBy = "theatreOnboarding", cascade = CascadeType.ALL)
    private Set<OnboardingVerification> verifications = new HashSet<>();

    private OnboardingStatus onboardingStatus = OnboardingStatus.INITIATED;

    public TheatreOnboarding(){}

    public TheatreOnboarding(Theatre theatre, BusinessName businessName,
                             RegistrationNumber regNumber, TaxIdentificationNumber taxId) {
        this.theatre = Objects.requireNonNull(theatre);
        this.businessName = Objects.requireNonNull(businessName);
        this.registrationNumber = Objects.requireNonNull(regNumber);
        this.taxIdentificationNumber = Objects.requireNonNull(taxId);
        initializeVerifications();
    }

    private void initializeVerifications() {
        Arrays.stream(VerificationType.values())
                .forEach(type -> addVerification(type));
    }

    public void setName(String name){
        this.theatre = new Theatre(new TheatreName(name));
    }

    public void addVerification(VerificationType type) {
        OnboardingVerification verification = new OnboardingVerification(this, type);
        verifications.add(verification);
    }

    public void updateVerificationStatus(VerificationType type,
                                         VerificationStatus status,
                                         String verifiedBy,
                                         String comments) {
        findVerification(type)
                .updateStatus(status, verifiedBy, comments);

        checkAndUpdateOnboardingStatus();
    }

    private OnboardingVerification findVerification(VerificationType type) {
        Objects.requireNonNull(type, "Verification type cannot be null");

        return verifications.stream()
                .filter(verification -> type.equals(verification.getType()))
                .findFirst()
                .orElseThrow(() -> new VerificationNotFoundException(this.getId(), type));
    }

    private void checkAndUpdateOnboardingStatus() {
        if (areAllVerificationsComplete()) {
            this.onboardingStatus = OnboardingStatus.ACTIVE;
        }
    }

    private boolean areAllVerificationsComplete() {
        return verifications.stream()
                .allMatch(v -> v.getVerificationStatus() == VerificationStatus.APPROVED);
    }

    public static class VerificationNotFoundException extends RuntimeException{
        private final Long theatreOnboardingId;
        private final VerificationType type;

        public VerificationNotFoundException(Long theatreOnboardingId, VerificationType type) {
            super(String.format("Verification not found for theatre onboarding ID: %d and type: %s",
                    theatreOnboardingId, type));
            this.theatreOnboardingId = theatreOnboardingId;
            this.type = type;
        }
    }
}
