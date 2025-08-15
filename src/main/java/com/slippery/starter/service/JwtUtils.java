package com.slippery.starter.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtils {
    @Value("${jwt.secretkey}")
    private String secretKey ;
    @Getter
    private final long jwtExpiration =86400000;

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T extractClaims(String token, Function<Claims,T> claimsResolver){
        return claimsResolver.apply(Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload());
    }

    public String extractUserNameFromToken(String token){
        return extractClaims(token, Claims::getSubject);
    }

    public String generateToken(String username) {
        Map<String, Object> claims =new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+jwtExpiration))
                .and()
                .signWith(getSignInKey())
                .compact();

    }
    public String generateFreshToken(String username){
        HashMap<String,Object> claims =new HashMap<>();
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+jwtExpiration))
                .signWith(getSignInKey())
                .compact();
    }
    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username =extractUserNameFromToken(token);
        return (username.equals(userDetails.getUsername())&&!isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return(extractTokenExpiration(token).before(new Date()));
    }

    private Date extractTokenExpiration(String token) {
        return extractClaims(token,Claims::getExpiration);
    }


}