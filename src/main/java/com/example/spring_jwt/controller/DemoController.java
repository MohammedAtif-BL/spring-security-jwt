package com.example.spring_jwt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class DemoController {
    @GetMapping("/public")
    public ResponseEntity<String> publicEndpoint() {
        return ResponseEntity.ok("This endpoint is accessible by anyone.");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public ResponseEntity<String> userEndpoint() {
        return ResponseEntity.ok("This endpoint is accessible by USER and ADMIN.");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<String> adminEndpoint() {
        return ResponseEntity.ok("This endpoint is accessible only by ADMIN.");
    }
}
