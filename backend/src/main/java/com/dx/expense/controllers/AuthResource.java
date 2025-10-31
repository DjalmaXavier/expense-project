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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

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

            return ResponseEntity.ok(new LoginResponseDTO(token, user.getName()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Credenciais inválidas: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + e.getMessage());
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String authHeader,
            @RequestBody String name) {
        try {

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.badRequest().body("Formato de token inválido");
            }
            String token = authHeader.substring(7);

            String newToken = tokenService.refreshToken(token, name);
            return ResponseEntity.ok(newToken);

        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("Nullpointer: " + e);
            return ResponseEntity.internalServerError().body("Erro ao renovar token: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro ao renovar token: " + e.getStackTrace());
            return ResponseEntity.internalServerError().body("Erro ao renovar token: " + e.getMessage());
        }
    }

}
