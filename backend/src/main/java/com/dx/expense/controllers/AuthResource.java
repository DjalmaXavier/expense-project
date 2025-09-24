package com.dx.expense.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dx.expense.dto.LoginRequestDTO;
import com.dx.expense.dto.LoginResponseDTO;
import com.dx.expense.dto.RegisterDTO;
import com.dx.expense.dto.ErrorResponseDTO;
import com.dx.expense.services.JwtTokenService;
import com.dx.expense.services.StatusCodeService;
import com.dx.expense.services.UserServices;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    private final UserServices userServices;

    private final JwtTokenService tokenService;

    private final StatusCodeService statusService;

    @Autowired
    public AuthResource(UserServices userServices, JwtTokenService tokenService, StatusCodeService statusService) {
        this.userServices = userServices;
        this.tokenService = tokenService;
        this.statusService = statusService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        try {
            userServices.registerUser(registerDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (RuntimeException e) {

            ErrorResponseDTO error = statusService.createErrorResponse(e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            var user = userServices.loginUser(loginRequestDTO);

            var token = tokenService.generateToken(user);

            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Credenciais inválidas: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + e.getMessage());
        }
    }

}
