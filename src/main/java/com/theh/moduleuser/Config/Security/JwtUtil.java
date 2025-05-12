package com.theh.moduleuser.Config.Security;

import com.theh.moduleuser.Exceptions.EntityNotFoundException;
import com.theh.moduleuser.Exceptions.ErrorCodes;
import com.theh.moduleuser.Exceptions.JwtValidationException;
import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtUtil {

    private String SECRET_KEY="secret";

    public String extractUserName(String token){
    return extractClaim(token, Claims::getSubject);
    }
    public Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public Claims extractAllClaims(String token){
        //return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtValidationException("Le token JWT est expiré", e);
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new JwtValidationException("Le token JWT est invalide : " + e.getMessage(), e);
        }
    }
//TODO si necessaire changé Public to private
    public boolean isTokenExpired(String token){
        try {
            return extractExpiration(token).before(new Date());
        } catch (ExpiredJwtException e){
            return true;
        }
    }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims=new HashMap<>();
        return creatToken(claims,userDetails.getUsername());
    }
    public String generateRefreshToken(UserDetails userDetails){
        Map<String, Object> claims=new HashMap<>();
        return creatRefreshToken(claims,userDetails.getUsername());
    }

    private String creatToken(Map<String, Object> claims, String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) //TODO 15 minute
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY).compact();
    }
    private String creatRefreshToken(Map<String, Object> claims, String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // TODO 7jours
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY).compact();
    }
    public Boolean validToken(String token,UserDetails userDetails){
        final String username=extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
