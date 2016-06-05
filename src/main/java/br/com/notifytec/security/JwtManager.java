package br.com.notifytec.security;

import br.com.notifytec.models.UsuarioModel;
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

    public String newToken(UsuarioModel usuario) {
        JwtBuilder builder = Jwts.builder();

        builder.claim("login", usuario.getLogin());
        builder.claim("email", usuario.getEmail());
        builder.claim("dataValidadeToken", usuario.getDataValidadeToken());
        builder.claim("alterouSenha", usuario.isAlterouSenha());
        builder.claim("podeEnviar", usuario.isPodeEnviar());
        builder.claim("tokenRecuperarSenha", usuario.isTokenRecuperarSenha());
        builder.setId(usuario.getId().toString());

        // Adiciona validade de uma hora ao token.
        /*Calendar expiration = Calendar.getInstance();
        expiration.add(Calendar.HOUR, 1);
        builder.setExpiration(expiration.getTime());
    */
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
