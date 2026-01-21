package com.dx.expense.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dx.expense.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Service
public class JwtTokenService {

    // Variavel de ambiente
    @Value("${jwt.key}")
    private String fileKey;

    private SecretKey key;

    private final long ACCESS_TOKEN_EXP = 60 * 15; // 15 min
    private final long REFRESH_TOKEN_EXP = 7; // 7 dias

    @Autowired
    public JwtTokenService() {
    }

    @PostConstruct
    public void init() {
        try {
            this.key = Keys.hmacShaKeyFor(fileKey.getBytes());

        } catch (Exception e) {
            throw new IllegalStateException("Erro ao carregar chave JWT", e);
        }
    }

    public String generateAccessToken(User user) {
        try {
            return Jwts.builder()
                    .issuer("expense-control")
                    .subject(user.getLogin())
                    .issuedAt(new Date())
                    .expiration(Date.from(Instant.now().plusSeconds(ACCESS_TOKEN_EXP))) // 15 min
                    .claim("name", user.getName())
                    .claim("roles", user.getRoles())
                    .signWith(key)
                    .compact();
        } catch (JwtException e) {
            throw new RuntimeException("Falha ao gerar token: " + e);
        }

    }

    public String generateRefreshToken(User user) {
        try {
            return Jwts.builder()
                    .issuer("expense-control")
                    .subject(user.getLogin())
                    .issuedAt(new Date())
                    .expiration(Date.from(Instant.now().plus(REFRESH_TOKEN_EXP, ChronoUnit.DAYS)))
                    .claim("name", user.getName())
                    .claim("roles", user.getRoles())
                    .signWith(key)
                    .compact();
        } catch (JwtException e) {
            throw new RuntimeException("Falha ao gerar  refresh token: " + e);
        }

    }

    public String generateAccessTokenFromClaims(String token) {

        Claims claims = isTokenValid(token);

        return Jwts.builder()
                .issuer("expense-control")
                .subject(claims.getSubject())
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plusSeconds(ACCESS_TOKEN_EXP))) // 15 min
                .claim("name", claims.get("name"))
                .claim("roles", claims.get("roles"))
                .signWith(key)
                .compact();
    }

    public String generateRefreshTokenFromClaims(String token) {

        Claims claims = isTokenValid(token);

        return Jwts.builder()
                .issuer("expense-control")
                .subject(claims.getSubject())
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plus(REFRESH_TOKEN_EXP, ChronoUnit.DAYS)))
                .claim("name", claims.get("name"))
                .claim("roles", claims.get("roles"))
                .signWith(key)
                .compact();
    }

    // Verifica a validade do Token
    public Claims isTokenValid(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return claims;
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }
}