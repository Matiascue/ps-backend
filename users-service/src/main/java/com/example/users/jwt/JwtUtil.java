package com.example.users.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JwtUtil {

    private String secretKey="avv123adeqañ";
    /*
     * Método para generar el token
     */
    public String generateToken(String username,Long userId){
        Algorithm algorithm=Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withSubject(username)
                .withClaim("userId",userId)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60))
                .sign(algorithm);
    }

    /*
     * Validar el token
     */
    public boolean validateToken(String token,String username){
        String tokenUsername=this.extractUsername(token);
        return (username.equals(tokenUsername)&&!isTokenExpired(token));
    }

    /*
     *Metodo para extraer el nombre del usuario desde el token
     */
    public String extractUsername(String token){
        DecodedJWT decodedJWT=JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token);
        return  decodedJWT.getSubject();
    }
    /*
     *Metodo para extraer el userId desde el token
     */
    public Long extractUserId(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token);
        return decodedJWT.getClaim("userId").asLong();
    }
    /*
     *Verificar si el token expiro
     */
    private boolean isTokenExpired(String token){
        DecodedJWT decodedJWT=JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token);
        return decodedJWT.getExpiresAt().before(new Date());
    }
}
