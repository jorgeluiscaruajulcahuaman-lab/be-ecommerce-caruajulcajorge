package com.example.api.security;

import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final SecretKey key;
    private final long expiration;

    public JwtService(@Value("${jwt.secret}") String secret,
                      @Value("${jwt.expiration}") long expiration) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.expiration = expiration;
    }

    public String generarToken(UserDetails user, String nombre) {
        Date ahora = new Date();
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("nombre", nombre)
                .claim("roles", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList())
                .issuedAt(ahora)
                .expiration(new Date(ahora.getTime() + expiration))
                .signWith(key)
                .compact();
    }

    public String extraerUsername(String token) {
        return claims(token).getSubject();
    }

    public boolean esValido(String token, UserDetails user) {
        Claims c = claims(token);
        return c.getSubject().equals(user.getUsername())
                && c.getExpiration().after(new Date());
    }

    private Claims claims(String token) {
        return Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token).getPayload();
    }
}
