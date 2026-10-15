package com.expense.auth_service.service;

import com.expense.auth_service.dto.AuthRequest;
import com.expense.auth_service.dto.AuthResponse;
import com.expense.auth_service.dto.RegisterRequest;
import com.expense.auth_service.dto.ValidateTokenResponse;
import com.expense.auth_service.entity.User;
import com.expense.auth_service.repository.UserRepository;
import com.expense.auth_service.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       AuthenticationManager authenticationManager,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        return "User registered successfully";
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(user.getEmail(), List.of("USER"));
        return new AuthResponse(token, user.getEmail(), List.of("USER"));
    }

    public ValidateTokenResponse validate(String token) {
        boolean valid = jwtService.isTokenValid(token);
        if (!valid) {
            return new ValidateTokenResponse(null, List.of(), false);
        }
        return new ValidateTokenResponse(
                jwtService.extractUsername(token),
                jwtService.extractRoles(token),
                true
        );
    }
}
