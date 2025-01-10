package org.book_my_show.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.book_my_show.domain.onboard.BusinessName;
import org.book_my_show.domain.onboard.OnboardingVerification;
import org.book_my_show.domain.onboard.RegistrationNumber;
import org.book_my_show.domain.onboard.TaxIdentificationNumber;
import org.book_my_show.domain.status.OnboardingStatus;
import org.book_my_show.domain.theatre.Theatre;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TheatreOnboardingDTO extends BaseDTO{
    private Long theatreId;
    private String  theatre;
    private String  businessName;
    private String  registrationNumber;
    private String  taxIdentificationNumber;
    private OnboardingStatus status;
    private Set<OnboardingVerification> verifications;
}
