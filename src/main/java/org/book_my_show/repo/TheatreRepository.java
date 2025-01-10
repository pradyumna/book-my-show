package org.book_my_show.repo;

import org.book_my_show.domain.theatre.Theatre;
import org.book_my_show.dto.TheatreDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre,Long> {

    @Query("SELECT t FROM Theatre t WHERE t.address.city = :city")
    List<TheatreDTO> findTheatresByCity(@Param("city") String city);
}
