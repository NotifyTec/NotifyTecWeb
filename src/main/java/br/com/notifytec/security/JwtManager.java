package br.com.notifytec.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Base64;
import java.util.Calendar;
import java.util.UUID;
import javax.crypto.spec.SecretKeySpec;

public class JwtManager {

    public String newToken(UUID userID) {
        JwtBuilder builder = Jwts.builder();

        // Insere o nome e o Id do usuário no token para identificação do mesmo.
        builder.setSubject("Name");
        builder.setId(UUID.randomUUID().toString());

        // Adiciona validade de uma hora ao token.
        Calendar expiration = Calendar.getInstance();
        expiration.add(Calendar.HOUR, 1);
        builder.setExpiration(expiration.getTime());

        String s = builder.signWith(SignatureAlgorithm.HS512, getKey()).compact();

        return s;
    }

    public boolean validadeToken(String s) {
        try {
            Claims claims = Jwts.parser().setSigningKey(getKey()).parseClaimsJws(s).getBody();           
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private Key getKey() {
        String encodedKey = "kIc6brxkdAv6/uruQoH35g==";
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }
}
