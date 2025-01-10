package org.book_my_show.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.book_my_show.dto.UserDTO;
import org.book_my_show.dto.UserPreferencesUpdateDTO;
import org.book_my_show.dto.UserRegistrationRequestDTO;
import org.book_my_show.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserRegistrationRequestDTO request) {
        return ResponseEntity.ok(userService.registerUser(request));
    }

    @PutMapping("/{userId}/preferences")
    public ResponseEntity<UserDTO> updatePreferences(
            @PathVariable Long userId,
            @RequestBody UserPreferencesUpdateDTO request) {
        return ResponseEntity.ok(userService.updatePreferences(userId, request.getPreferences()));
    }
}
