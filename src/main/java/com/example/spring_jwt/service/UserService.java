package com.example.spring_jwt.service;

import com.example.spring_jwt.config.JwtService;
import com.example.spring_jwt.dto.AuthenticationRequest;
import com.example.spring_jwt.dto.AuthenticationResponse;
import com.example.spring_jwt.dto.RegisterRequest;
import com.example.spring_jwt.entity.User;
import com.example.spring_jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        var savedUser = repository.save(user);
//        var jwtToken = jwtService.generateToken(user);
//        var refreshToken = jwtService.generateRefreshToken(user);
//        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .message("User registered successfully")
                .user(savedUser)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid email or password"));
        var jwtToken = jwtService.generateToken(user);
//        var refreshToken = jwtService.generateRefreshToken(user);
//        revokeAllUserTokens(user);
//        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .message("Login successful: " +jwtToken)
                .build();
    }

}
