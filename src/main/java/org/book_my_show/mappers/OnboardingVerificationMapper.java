package org.book_my_show.mappers;

import org.book_my_show.dto.OnboardingVerificationDTO;
import org.book_my_show.domain.onboard.OnboardingVerification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OnboardingVerificationMapper {
    @Mapping(source = "theatreOnboarding.id", target = "theatreOnboardingId")
    OnboardingVerificationDTO toDto(OnboardingVerification entity);

    @Mapping(source = "theatreOnboardingId", target = "theatreOnboarding.id")
    OnboardingVerification toEntity(OnboardingVerificationDTO dto);

    List<OnboardingVerificationDTO> toDtoList(List<OnboardingVerification> entities);
}
