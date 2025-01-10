package org.book_my_show.controller;


import lombok.RequiredArgsConstructor;
import org.book_my_show.dto.TheatreDTO;
import org.book_my_show.services.TheatreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/theatre")
@RequiredArgsConstructor
public class TheatreController {

    private final TheatreService theatreService;

    @PostMapping
    public ResponseEntity<TheatreDTO> createTheatre(@RequestBody TheatreDTO theatreDTO){
        theatreDTO = theatreService.createTheatre(theatreDTO);
        return ResponseEntity.ok(theatreDTO);
    }

}
