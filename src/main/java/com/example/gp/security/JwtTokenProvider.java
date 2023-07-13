package com.example.gp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;



@Component
@Slf4j
public class JwtTokenProvider {

    private final String secretKey = "mySecretKey";
    private final long validitySeconds = 3600000; // 1시간

    public String createToken(String nick){
        Claims claims = Jwts.claims().setSubject(nick);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validitySeconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        }catch (ExpiredJwtException e){
            log.error("Token is expired",e);
            return false;
        }
        catch(Exception e){
            log.error("validateToken failed with exception: ", e);
            return false;
        }
    }

    public String getUsername(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

}
