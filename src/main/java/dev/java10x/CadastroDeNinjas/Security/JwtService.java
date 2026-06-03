package dev.java10x.CadastroDeNinjas.Security;

import dev.java10x.CadastroDeNinjas.Model.UsuarioModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    public String gerarToken(UsuarioModel usuario) {

        return Jwts.builder()
                .subject(usuario.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getKey())
                .compact();
    }

    public String extrairUsername(String token) {
        return extrairClaims(token).getSubject();
    }

    public boolean tokenExpirado(String token) {

        Date expiracao = extrairClaims(token).getExpiration();

        return expiracao.before(new Date());
    }

    public boolean tokenValido(String token, UserDetails userDetails) {

        String username = extrairUsername(token);

        return username.equals(userDetails.getUsername())
                && !tokenExpirado(token);
    }

    private Claims extrairClaims(String token) {

        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
