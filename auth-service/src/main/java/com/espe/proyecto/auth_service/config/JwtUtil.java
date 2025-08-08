package com.espe.proyecto.auth_service.config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "tu_clave_secreta_super_larga_y_dificil";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

    public String generateToken(String email, String role) {
        return JWT.create()
            .withIssuer("auth-service")
            .withSubject(email)
            .withClaim("role", role)
            .withIssuedAt(new Date())
            .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hora
            .sign(ALGORITHM);
    }

    public boolean validateToken(String token) {
        try {
            JWT.require(ALGORITHM).withIssuer("auth-service").build().verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }
}