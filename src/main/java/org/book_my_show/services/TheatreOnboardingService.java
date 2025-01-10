package org.book_my_show.services;


import lombok.RequiredArgsConstructor;
import org.book_my_show.dto.TheatreOnboardingDTO;
import org.book_my_show.domain.onboard.TheatreOnboarding;
import org.book_my_show.domain.status.OnboardingStatus;
import org.book_my_show.exceptions.ResourceNotFoundException;
import org.book_my_show.mappers.TheatreOnboardingMapper;
import org.book_my_show.repo.TheatreOnboardingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TheatreOnboardingService {
    private final TheatreOnboardingRepository onboardingRepository;
    private final TheatreOnboardingMapper onboardingMapper;

    public TheatreOnboardingDTO initiateOnboarding(TheatreOnboardingDTO request) {
        TheatreOnboarding onboarding = onboardingMapper.toEntity(request);
        onboarding = onboardingRepository.save(onboarding);
        System.out.println("#### Onboarding:[" + onboarding + "] ####");
        return onboardingMapper.toDto(onboarding);
    }

    public TheatreOnboardingDTO updateOnboardingStatus(Long id, OnboardingStatus status) {
        TheatreOnboarding onboarding = onboardingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Onboarding not found"));

        onboarding = onboardingRepository.save(onboarding);

        return onboardingMapper.toDto(onboarding);
    }
}
