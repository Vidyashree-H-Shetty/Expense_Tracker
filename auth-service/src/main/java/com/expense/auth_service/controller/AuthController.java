package com.expense.auth_service.controller;
import com.expense.auth_service.dto.AuthRequest;
import com.expense.auth_service.dto.AuthResponse;
import com.expense.auth_service.dto.RegisterRequest;
import com.expense.auth_service.dto.ValidateTokenResponse;
import com.expense.auth_service.service.AuthService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        System.out.println("Reg hit");
        return ResponseEntity.ok(authService.register(request));
    }

    @GetMapping("/get")
    public String GetName(){
        return "Api hit";
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        System.out.println(request+" is coming");
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/validate")
    public ResponseEntity<ValidateTokenResponse> validateToken(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return ResponseEntity.ok(authService.validate(token));
    }
}

