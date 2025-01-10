package org.book_my_show.controller;


import lombok.RequiredArgsConstructor;
import org.book_my_show.dto.BookingDTO;
import org.book_my_show.dto.BookingRequestDTO;
import org.book_my_show.services.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Object> bookTicket(@RequestBody BookingRequestDTO requestDTO){
        return ResponseEntity.ok(bookingService.bookMovieTicket(requestDTO));
    }
}
