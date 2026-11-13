package com.bookapp.service;

import com.bookapp.dto.AuthRequest;
import com.bookapp.dto.AuthResponse;
import com.bookapp.dto.RegisterRequest;
import com.bookapp.exception.ApiException;
import com.bookapp.model.User;
import com.bookapp.repository.UserRepository;
import com.bookapp.security.JwtService;
import com.bookapp.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {
        String email = request.getEmail().trim().toLowerCase();
        if (userRepository.existsByEmail(email)) {
            throw new ApiException(HttpStatus.CONFLICT, "Email is already registered");
        }

        User user = User.builder()
                .username(request.getUsername().trim())
                .email(email)
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .build();

        userRepository.save(user);
        return new AuthResponse(jwtService.generateToken(new UserDetailsImpl(user)));
    }

    public AuthResponse login(AuthRequest request) {
        String email = request.getEmail().trim().toLowerCase();
        // Same message for "unknown email" and "wrong password", so attackers can't find out which emails exist
        User user = userRepository.findByEmail(email)
                .filter(u -> passwordEncoder.matches(request.getPassword(), u.getPassword()))
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));

        return new AuthResponse(jwtService.generateToken(new UserDetailsImpl(user)));
    }
}
