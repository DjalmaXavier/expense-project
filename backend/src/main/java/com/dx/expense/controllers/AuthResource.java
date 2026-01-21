package com.dx.expense.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dx.expense.dto.LoginRequestDTO;
import com.dx.expense.dto.LoginResponseDTO;
import com.dx.expense.dto.RegisterDTO;
import com.dx.expense.entities.User;
import com.dx.expense.services.JwtTokenService;
import com.dx.expense.services.UserServices;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.Duration;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    private final UserServices userServices;

    private final JwtTokenService tokenService;

    @Autowired
    public AuthResource(UserServices userServices, JwtTokenService tokenService) {
        this.userServices = userServices;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        userServices.registerUser(registerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
        try {
            var user = userServices.loginUser(loginRequestDTO);

            var acessToken = tokenService.generateAccessToken(user);

            var refreshToken = tokenService.generateRefreshToken(user);

            ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("None")
                    .path("/")
                    .maxAge(Duration.ofDays(7))
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return ResponseEntity.ok(new LoginResponseDTO(acessToken));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + e.getMessage());
        }
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        try {

            if (request.getCookies() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nenhum cookie encontrado");
            }

            String refreshToken = Arrays.stream(request.getCookies())
                    .filter(cookie -> "refreshToken".equals(cookie.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);

            if (refreshToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token de atualização não encontrado");
            }

            String acessToken = tokenService.generateAccessTokenFromClaims(refreshToken);

            String newRefreshToken = tokenService.generateRefreshTokenFromClaims(refreshToken);

            ResponseCookie cookie = ResponseCookie.from("refreshToken", newRefreshToken)
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("None")
                    .path("/")
                    .maxAge(Duration.ofDays(7))
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return ResponseEntity.ok(new LoginResponseDTO(acessToken));
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

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("None")
                    .path("/")
                    .maxAge(0)
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return ResponseEntity.ok().body("Logout realizado com sucesso");
        } catch (Exception e) {
            System.err.println("Erro ao fazer logout: " + e.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao fazer logout: " + e.getMessage());
        }
    }
}
