package com.example.AuthorizatioDemo.utils;

import com.example.AuthorizatioDemo.configuration.JwtConfiguration;
import com.example.AuthorizatioDemo.entity.User;
import com.example.AuthorizatioDemo.repository.UserReposicoty;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
@AllArgsConstructor
public class JwtUtils {
    private UserReposicoty userReposicoty;
    private JwtConfiguration jwtConfiguration;

    public String generateToken(Authentication authentication) {
        User user = userReposicoty.findByUsername(authentication.getName());
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtConfiguration.getExpiration());
        String jwtToken = Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, jwtConfiguration.getSecret())
                .claim("id", user.getId())
                .compact();
        return jwtToken;
    }

    public String parseJWT(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token.startsWith("Bearer ")) {
            String result = token.replaceFirst("Bearer ", "");
            return result;
        }
        return null;
    }

    public boolean validateJWT(String jwt) {
        Jwts.parser()
                .setSigningKey(jwtConfiguration.getSecret())
                .parseClaimsJws(jwt);
        return true;
    }

    public String getName(String token) {
        String ok = Jwts.parser()
                .setSigningKey(jwtConfiguration.getSecret())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return ok;
    }
}
