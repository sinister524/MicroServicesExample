package com.mytona.testtusk.OrderService.configuration.jwt;

import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
@Log4j2
public class JwtProvider {

    @Value("$(jwt.secret)")
    private String jwtSecret;

    public String generateToken(String login) {
        Date date = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.warn("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            log.warn("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            log.warn("Malformed jwt");
        } catch (SignatureException sEx) {
            log.warn("Invalid signature");
        } catch (Exception e) {
            log.warn("invalid token");
        }
        return false;
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

}
