package com.jwt_revision.Test_jwt_methods.service;
import com.jwt_revision.Test_jwt_methods.model.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtServiceImpl {

    private String SECRET_KEY= "6fb0886c4d90872c4544f5b073d93560b58e9454e65de7267b7012f73e778cdb";

    public String generateToken(Users users){
        String token= Jwts
                .builder()
                .claim("role", users.getRole())
                .subject(users.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24*60*60*1000))
                .signWith(getSignInKey())
                .compact();
        return token;
    }

    private SecretKey getSignInKey() {
        // this method will decode the base64 url i.e SECRET_KEY and return the secret key
        // it will used to verify token
        byte[] keyBytes= Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractCompleteClaims(String token){
        // this will be used to fetch complete payload
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver){
        // this method is used to extract specific field from payload
        Claims claims= extractCompleteClaims(token);
        System.out.println("Claims=> "+claims);
        return resolver.apply(claims);
    }


    public String extractUsername(String token){
        System.out.println("extractClaim => "+extractClaim(token, Claims::getSubject));
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isValid(String token, UserDetails user) {
        String username = extractUsername(token);
        System.out.println("username => "+username);
        return (username.equals(user.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
