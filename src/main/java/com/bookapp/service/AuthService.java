package com.bookapp.service;

import com.bookapp.dto.AuthRequest;
import com.bookapp.dto.AuthResponse;
import com.bookapp.dto.RegisterRequest;
import com.bookapp.model.User;
import com.bookapp.repository.UserRepository;
import com.bookapp.security.JwtService;
import com.bookapp.security.UserDetailsImpl; // Import your custom UserDetails
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .build();

        userRepository.save(user);

        // Create UserDetailsImpl from the saved user
        UserDetails userDetails = new UserDetailsImpl(user);
        String jwt = jwtService.generateToken(userDetails);

        return new AuthResponse(jwt);
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Create UserDetailsImpl from the logged-in user
        UserDetails userDetails = new UserDetailsImpl(user);
        String jwt = jwtService.generateToken(userDetails);

        return new AuthResponse(jwt);
    }
}
