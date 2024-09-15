package com.annapurna.annapurna.ServiceImpl;

import com.annapurna.annapurna.Service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtServiceImpl implements JwtService {

    @Value("${authentication.secret.key}")
    String secretKey;

    /**
     *
     * @param userNameOrEmail
     * @return
     */
    @Override
    public String generateToken(String userNameOrEmail){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims,userNameOrEmail);
    }

    /**
     *
     * @param claims
     * @param userNameOrEmail
     * @return
     */
    private String createToken(Map<String, Object> claims, String userNameOrEmail) {
        return Jwts.builder().setClaims(claims)
                .setSubject(userNameOrEmail)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     *
     * @return
     */
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     *
     * @param token
     * @return
     */
    @Override
    public String extractUserName(String token){
        return extractClaims(token, Claims::getSubject);
    }

    /**
     *
     * @param token
     * @return
     */
    @Override
    public Date extractExpiration(String token){
        return extractClaims(token,Claims::getExpiration);
    }

    /**
     *
     * @param token
     * @param claimsResolver
     * @return
     * @param <T>
     */
    private <T> T extractClaims(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     *
     * @param token
     * @return
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    /**
     *
     * @param token
     * @return
     */
    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    /**
     *
     * @param token
     * @param userDetails
     * @return
     */
    @Override
    public Boolean validateToken(String token, UserDetails userDetails){
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
