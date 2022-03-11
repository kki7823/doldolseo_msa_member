package com.gikim.doldolseo_msa_member.utils;

import io.jsonwebtoken.*;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {
    private static final String JWT_SECRET = "DOLDOLSEOTESTSECRET";
    private static final String API_KEY = "DOLDOLSEOTESTAPIKEY";
    private static final int JWT_EXPIRATION_MS = 60480000;
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    public boolean validateToken(String token, UserDetails userDetails) {
        final String id = getIdFromToken(token);
        return (id.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String getIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getId);
    }


    public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String generateToken(String id, String role) {
        return doGenerateToken(id, role, new HashMap<>());
    }

    public String generateToken(String id, String role, Map<String, Object> claims) {
        return doGenerateToken(id, role, claims);
    }

    public String doGenerateToken(String id, String role, Map<String, Object> claims) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_MS);

        claims.put("api-key", API_KEY);
        claims.put("role", role);
        String token = Jwts.builder()
                .setClaims(claims)
                .setId(id)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();

        System.out.println("token : " + token);

        return token;
    }

    public void setToken(HttpServletResponse response, String token) {
//        Cookie jwtCookie = new Cookie("token", token);
//        jwtCookie.setMaxAge(7 * 24 * 60 * 60);
//        jwtCookie.setSecure(true);
//        jwtCookie.setHttpOnly(true);
//        jwtCookie.setPath("/");

        ResponseCookie jwtCookie = ResponseCookie.from("token", token)
                .maxAge(7 * 24 * 60 * 60)
                .path("/")
                .sameSite("None")
                .build();

        response.addHeader("Set-Cookie", jwtCookie.toString());
    }


}