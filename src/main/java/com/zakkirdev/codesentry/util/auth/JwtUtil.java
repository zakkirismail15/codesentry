package com.zakkirdev.codesentry.util.auth;

import com.zakkirdev.codesentry.repository.entity.User;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    private final String SECRET_KEY =  "codesentrysecuritydefense";
    private final long ACCESS_TOKEN_VALIDITY = 60*60*1000;
    private final JwtParser jwtParser;

    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    public JwtUtil() {
        this.jwtParser = Jwts.parser().setSigningKey(SECRET_KEY);
    }

    public String createToken(User user){
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());
        claims.put("roles", user.getRoles().toString());
        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(ACCESS_TOKEN_VALIDITY));
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(tokenValidity)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    private Claims parseJwtClaims(String token){
        return jwtParser.parseClaimsJws(token).getBody();
    }

    /**
     * Resolving the claims from token to identify the validity
     * @param req
     * @return
     */
    public Claims resolveClaims(HttpServletRequest req){
        try{
            String token = resolveToken(req);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        }catch (ExpiredJwtException ex){
            req.setAttribute("expired",ex.getMessage());
            throw ex;
        }catch (Exception ex){
            req.setAttribute("invalid",ex.getMessage());
            throw ex;
        }
    }

    /**
     * Retrieve token from the request header
     * @param request
     * @return token
     */
    public String resolveToken(HttpServletRequest request){

        String bearerToken = request.getHeader(TOKEN_HEADER);
        if(bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)){
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean validateClaims(Claims claims) throws AuthenticationException{
        try{
            return claims.getExpiration().after(new Date());
        }catch (Exception ex){
            throw ex;
        }
    }

    public String getEmail(Claims claims){
        return claims.getSubject();
    }

    private List<String> getRoles(Claims claims){
        return (List<String>) claims.get("roles");
    }
}
