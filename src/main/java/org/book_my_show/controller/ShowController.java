package org.book_my_show.controller;

import lombok.RequiredArgsConstructor;
import org.book_my_show.domain.show.Show;
import org.book_my_show.dto.ShowDTO;
import org.book_my_show.services.ShowService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shows")
@RequiredArgsConstructor
public class ShowController {
    private final ShowService showService;

    @GetMapping("/city/{city}")
    public ResponseEntity<List<ShowDTO>> getShowsInCity(
            @PathVariable String city,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(showService.getShowsInCity(city, date));
    }

    @PostMapping
    public ResponseEntity<ShowDTO> createShow(@RequestBody ShowDTO showDTO){
        return ResponseEntity.ok(showService.createShow(showDTO));
    }
}
