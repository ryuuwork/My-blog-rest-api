package com.tuananhdo.controller;

import com.tuananhdo.payload.JWTAuthResponse;
import com.tuananhdo.payload.LoginDTO;
import com.tuananhdo.payload.RegisterDTO;
import com.tuananhdo.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "REST APIs for Authentication Resource")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDTO loginDTO) {
        String token = authService.login(loginDTO);
        JWTAuthResponse authResponse = new JWTAuthResponse();
        authResponse.setAccessToken(token);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) throws RoleNotFoundException {
        String response = authService.register(registerDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
