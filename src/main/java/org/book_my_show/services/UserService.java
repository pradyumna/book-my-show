package org.book_my_show.services;

import lombok.RequiredArgsConstructor;
import org.book_my_show.domain.user.Email;
import org.book_my_show.dto.UserDTO;
import org.book_my_show.dto.UserRegistrationRequestDTO;
import org.book_my_show.domain.user.User;
import org.book_my_show.exceptions.DuplicateResourceException;
import org.book_my_show.exceptions.ResourceNotFoundException;
import org.book_my_show.mappers.UserMapper;
import org.book_my_show.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDTO registerUser(UserRegistrationRequestDTO request) {
        try {
            if (userRepository.existsByEmail(Email.of(request.getEmail()))) {
                throw new DuplicateResourceException("Email already registered");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        User user = userMapper.createFromRegistrationRequest(request);
        user = userRepository.save(user);

        return userMapper.toDto(user);
    }

    public UserDTO updatePreferences(Long userId, Set<String> preferences) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userMapper.updatePreferences(user,preferences);
        user = userRepository.save(user);

        return userMapper.toDto(user);
    }
}
