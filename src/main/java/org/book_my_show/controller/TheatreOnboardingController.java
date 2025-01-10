package org.book_my_show.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.book_my_show.domain.status.OnboardingStatus;
import org.book_my_show.dto.TheatreOnboardingDTO;
import org.book_my_show.services.TheatreOnboardingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/theatre-onboarding")
@RequiredArgsConstructor
public class TheatreOnboardingController {
    private final TheatreOnboardingService onboardingService;

    @PostMapping
    public ResponseEntity<TheatreOnboardingDTO> initiateOnboarding(
            @Valid @RequestBody TheatreOnboardingDTO request) {
        return ResponseEntity.ok(onboardingService.initiateOnboarding(request));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<TheatreOnboardingDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam OnboardingStatus status) {
        return ResponseEntity.ok(onboardingService.updateOnboardingStatus(id, status));
    }
}
