package com.semillero.ecosistema.security;

import com.semillero.ecosistema.exceptions.ValidateTokenException;
import com.semillero.ecosistema.models.UserModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class TokenUtil {

    @Value("${SECRET_KEY}")
    private String secretKeyConfig;
//    @Autowired
//    public TokenUtil(SecretKeyConfig secretKeyConfig) {
//        this.secretKeyConfig = secretKeyConfig;
//    }
 //   public String getSigningKey() {
//        return secretKeyConfig.getSecretKey();
//   }
    public String generateToken(UserModel user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600000); // 1 hour expiration
        String authorities = user.getRole().toString();
         return Jwts.builder()
                .setSubject((user.getEmail()))
                 .setId((user.getId().toString()))
                .claim("roles", authorities)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secretKeyConfig)
                .compact();
    }

    public String extractTokenFromHeader(String authHeader) {
        if (authHeader.isEmpty()) {
            throw new ValidateTokenException("Token is missing in the request header.");
        } else if (!authHeader.startsWith("Bearer ")){
            throw new ValidateTokenException("Token sent in an invalid format");
        }
        return authHeader.substring(7);
    }

}
