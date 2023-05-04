package com.vybe.backend.util;


import com.vybe.backend.model.entity.User;
import com.vybe.backend.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {
    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Value("${security.jwt.renew-secret}")
    private String renewSecret;

    @Value("${security.jwt.expirationMinutes}")
    public long expirationMinutes;

    @Resource
    private UserRepository userRepository;

    public String generateJwtToken(UserDetails userDetails, boolean renew) {
        Map<String, Object> claims = new HashMap<>();
        //email is set as username
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMinutes * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, renew ? renewSecret : jwtSecret).compact();
    }

    public User getUserFromToken(String token, boolean renew) {
        final Claims claims = Jwts.parser().setSigningKey(renew ? renewSecret : jwtSecret).parseClaimsJws(token).getBody();
        if (claims.getExpiration().before(new Date())) {
            throw new ExpiredJwtException(null, null, null);
        }

        String username = claims.getSubject();
        return userRepository.findByUsername(username).orElse(null);
    }

    public String generateJwtToken(UserDetails userDetails) {
        return generateJwtToken(userDetails, false);
    }

    public String generateJwtToken(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        return generateJwtToken(user, false);
    }

    public User getUserFromToken(String token) {
        return getUserFromToken(token, false);
    }

}
