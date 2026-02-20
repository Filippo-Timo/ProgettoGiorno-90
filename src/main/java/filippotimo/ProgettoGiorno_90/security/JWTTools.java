package filippotimo.ProgettoGiorno_90.security;

import filippotimo.ProgettoGiorno_90.entities.Utente;
import filippotimo.ProgettoGiorno_90.exceptions.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JWTTools {

    @Value("${jwt.secret}")
    private String secret;


    // TODO: 1) METODO PER GENERARE I TOKEN

    public String generateToken(Utente utente) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(Date.from(Instant.now().plus(30, ChronoUnit.DAYS)))
                .subject(String.valueOf(utente.getId()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }


    // TODO: 2) METODO PER VERIFICARE I TOKEN

    public void verifyToken(String token) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        } catch (Exception e) {
            throw new UnauthorizedException("Token non valido, prova ad effettuare nuovamente il login!");
        }
    }


    // TODO: 2) METODO PER ESTRARRE L'ID DAL TOKEN

    public Long extractIdFromToken(String token) {
        return Long.parseLong(Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject());
    }

}
