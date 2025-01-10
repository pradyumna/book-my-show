package org.book_my_show.repo;

import org.book_my_show.domain.show.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {
    @Query("SELECT s FROM Show s WHERE s.screen.theatre.address.city = :city " +
            "AND s.showTime BETWEEN :startTime AND :endTime")
    List<Show> findShowsInCity(@Param("city") String city,
                               @Param("startTime") LocalDateTime startTime,
                               @Param("endTime") LocalDateTime endTime);

    @Query("SELECT s FROM Show s " +
            "WHERE s.theatre.id = :theatreId " +
            "AND CAST(s.showTime AS DATE) = :showDate " +
            "AND (" +
            "(:showStartTime BETWEEN s.showTime AND s.showTime + s.duration) " +
            "OR (:calculatedEndTime BETWEEN s.showTime AND s.showTime + s.duration) " +
            "OR (s.showTime BETWEEN :showStartTime AND :calculatedEndTime) " +
            "OR (s.showTime + s.duration BETWEEN :showStartTime AND :calculatedEndTime)" +
            ")")
    List<Show> findOverlappingShows(@Param("theatreId") Long theatreId, @Param("showDate") LocalDate showDate,
                                    @Param("showStartTime") LocalDateTime showStartTime,
                                    @Param("calculatedEndTime") LocalDateTime calculatedEndTime);
}
