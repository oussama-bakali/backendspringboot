package tn.enicarthage.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {
    @Value("secret")
    private String secret;
   Map <String,Object>claims;
    public String generateToken(String x,String role,int username) {
        claims = new HashMap<>();
        claims.put("id", username);
        claims.put("role", role);
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(x)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
    Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
}

private Date getExpirationDateFromToken(String token) {
    return Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody()
            .getExpiration();
}

String getUsernameFromToken(String token) {
    return Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
}
public  String getinfofromtoken(String token) {
	Claims claims=Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody();
	return claims.get("role",String.class);
}
public boolean isuser() {
	return "user".equalsIgnoreCase((String)claims.get("role"));
}
}