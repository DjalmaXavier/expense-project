package com.dx.expense.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.NoSuchElementException;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dx.expense.entities.User;
import com.dx.expense.repository.UserRepository;

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

    private final UserRepository userRepository;

    private final long ACCESS_TOKEN_EXP = 60 * 15; // 15 min
    private final long REFRESH_TOKEN_EXP = 7; // 7 dias

    @Autowired
    public JwtTokenService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    public User extractUser(String token) {
        try {
            String username = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();

            return userRepository.findByLogin(username)
                    .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado no token"));
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Token inválido: " + e.getMessage());
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