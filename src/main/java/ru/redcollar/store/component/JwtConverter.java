package ru.redcollar.store.component;

import io.jsonwebtoken.Claims;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.redcollar.store.dto.JwtTokenUserDto;
import ru.redcollar.store.dto.RoleDto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

@Component
public class JwtConverter {

    @Value("${jwt.secret}")
    private String secret;

    public JwtTokenUserDto parseJwt(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            JwtTokenUserDto user = new JwtTokenUserDto();
            user.setLogin(body.getSubject());
            user.setEmail((String) body.get("email"));
            Integer id = (Integer) body.get("id");
            ArrayList<LinkedHashMap> roles = (ArrayList<LinkedHashMap>) body.get("role");
            user.setId(id.longValue());

            user.setRoles(roles.stream().map(s -> new RoleDto(((Integer) s.get("id")).longValue(), (String) s.get("name"))).collect(Collectors.toList()));
            return user;
        } catch (JwtException | ClassCastException e) {
            return null;
        }
    }

    public String parseAuthJwtUser(JwtTokenUserDto user) {
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
