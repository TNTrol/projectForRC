package ru.redcollar.store.component;

import io.jsonwebtoken.Claims;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.redcollar.store.domain.model.JwtTokenUser;
import ru.redcollar.store.domain.model.RoleDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

@Component
public class JwtConverter {

    @Value("${jwt.secret}")
    private String secret;

    public JwtTokenUser parseJwt(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            JwtTokenUser user = new JwtTokenUser();
            user.setLogin(body.getSubject());
            user.setEmail((String) body.get("email"));
            Integer id = (Integer) body.get("id");
            var a = (ArrayList<LinkedHashMap>) body.get("role");
            user.setId(id.longValue());

            user.setRoles(a.stream().map(s -> new RoleDto(((Integer) s.get("id")).longValue(), (String) s.get("name"))).collect(Collectors.toList()));
            return user;
        } catch (JwtException | ClassCastException e) {
            return null;
        }
    }

    public String parseAuthJwtUser(JwtTokenUser user) {
        String jws = Jwts.builder()
                .setSubject(user.getLogin())
                .claim("id", user.getId())
                .claim("role", user.getRoles())
                .claim("email", user.getEmail())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        return jws;
    }
}
