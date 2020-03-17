package com.ibm.expensetool.utils;

import com.ibm.expensetool.core.dto.request.UserAcesss;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Log4j2
@Service
@Data
public class JWTokenUtility {


    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expiry}")
    private Long expiryInMs;

    public JWTokenUtility() {
    }

    public String generateToken(UserAcesss userAcesss) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        String jwt = Jwts.builder()
                .setIssuedAt(new Date())
                .signWith(key)
                .compressWith(CompressionCodecs.DEFLATE)
                .setExpiration(new Date(System.currentTimeMillis() + expiryInMs))
                .claim("userAccess", userAcesss.toString())
                .compact();

        return jwt;
    }

    public Jws<Claims> verifyToken(String jwt) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt);

        return claims;
    }
}