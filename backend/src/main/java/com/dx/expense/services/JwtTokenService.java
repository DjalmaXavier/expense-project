package com.dx.expense.services;

import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

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

    @PostConstruct
    public void init() {
        try {
            this.key = Keys.hmacShaKeyFor(fileKey.getBytes());

        } catch (Exception e) {
            throw new IllegalStateException("Erro ao carregar chave JWT", e);
        }
    }

    public String generateToken(User user) {
        try {
            return Jwts.builder()
                    .issuer("expense-control")
                    .subject(user.getLogin())
                    .issuedAt(new Date())
                    .expiration(Date.from(Instant.now().plusSeconds(60 * 15))) // 15 min
                    .claim("name", user.getName())
                    .claim("roles", user.getRoles())
                    .signWith(key)
                    .compact();
        } catch (JwtException e) {
            throw new RuntimeException("Falha ao gerar token: " + e);
        }

    }

    // Verifica a validade do Token
    public boolean isTokenValid(String token, String username) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key) // Pega a chave que criamos
                    .build()
                    .parseSignedClaims(token) // Compara
                    .getPayload(); // Se bateu, traz o payload

            return username.equals(claims.getSubject()) && !claims.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}