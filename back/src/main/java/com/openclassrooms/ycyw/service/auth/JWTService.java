package com.openclassrooms.ycyw.service.auth;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import com.openclassrooms.ycyw.model.UserEntity;

/**
 * Service for handling JWT-based authentication.
 * <p>
 * This service provides functionalities for generating, decoding, and validating JWT tokens.
 * It also manages JWT storage in HTTP-only cookies for security.
 * </p>
 */
@Service
public class JWTService {

    @Value("${jwt.iss}")
    private String jwtIssuer;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    /**
     * Constructs an instance of {@code JWTService} with a required {@link JwtEncoder}.
     *
     * @param jwtEncoder the JWT encoder used for signing tokens.
     */
    public JWTService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    /**
     * Extracts the user ID from a given JWT token.
     *
     * @param token the JWT token.
     * @return the extracted user ID.
     */
    public Long extractUserId(String token) {
        return jwtDecoder.decode(token).getClaim("userId");
    }

    /**
     * Extracts the username (subject) from a given JWT token.
     *
     * @param token the JWT token.
     * @return the extracted username.
     */
    public String extractUsername(String token) {
        return jwtDecoder.decode(token).getClaim("sub");
    }

    /**
     * Generates a JWT authentication cookie for a user.
     * <p>
     * This method generates a JWT and stores it in a secure HTTP-only cookie.
     * </p>
     *
     * @param user the authenticated user.
     * @return a {@link ResponseCookie} containing the JWT.
     */
    public ResponseCookie generateJwtCookie(UserEntity user) {
        String token = generateToken(user);
        return ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .path("/")
                .maxAge(3600)
                .sameSite("Strict")
                .build();
    }

    /**
     * Generates a logout cookie that removes the JWT from the client.
     *
     * @return a {@link ResponseCookie} configured to remove the JWT.
     */
    public ResponseCookie generateJwtLogoutCookie() {
        return ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
    }

    /**
     * Validates a JWT token.
     * <p>
     * This method checks if the token is correctly signed and not expired.
     * </p>
     *
     * @param token the JWT token.
     * @return {@code true} if the token is valid, {@code false} otherwise.
     */
    public boolean validateToken(String token) {
        try {
            Jwt decodedJwt = jwtDecoder.decode(token);
            Instant expirationTime = decodedJwt.getExpiresAt();
            if (expirationTime == null || expirationTime.isBefore(Instant.now())) {
                return false;
            }
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    /**
     * Generates a JWT token for a given user.
     *
     * @param user the authenticated user.
     * @return the generated JWT token.
     */
    private String generateToken(UserEntity user) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(jwtIssuer)
                .issuedAt(now)
                .subject(user.getUsername())
                .claim("userId", user.getId())
                .expiresAt(now.plus(jwtExpiration, ChronoUnit.MILLIS))
                .build();

        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters
                .from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
        return jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
    }
}
