package ru.redcollar.store.component;

import io.jsonwebtoken.Claims;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.redcollar.store.domain.model.AuthJwtUser;
import ru.redcollar.store.domain.model.RoleDto;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class JwtConverter {

    @Value("${jwt.secret}")
    private String secret;

    public AuthJwtUser parseJwt(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            AuthJwtUser user = new AuthJwtUser();
            user.setLogin(body.getSubject());
            Integer id = (Integer) body.get("id");
            user.setId(id.longValue());
            user.setRoles(Arrays.stream(body.get("role").toString().split(",")).map(RoleDto::new).collect(Collectors.toList()));
            return user;

        } catch (JwtException | ClassCastException e) {
            return null;
        }
    }

    public String parseAuthJwtUser(AuthJwtUser user) {
        String jws = Jwts.builder()
                .setSubject(user.getLogin())
                .claim("id", user.getId())
                .claim("role", user.getRoles())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        return jws;
    }
}
