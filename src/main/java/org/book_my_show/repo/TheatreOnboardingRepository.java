package org.book_my_show.repo;

import org.book_my_show.domain.onboard.TheatreOnboarding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TheatreOnboardingRepository extends JpaRepository<TheatreOnboarding, Long> {
    //List<TheatreOnboarding> findByStatus(OnboardingStatus status);
    Optional<TheatreOnboarding> findByTheatreId(Long theatreId);
}
