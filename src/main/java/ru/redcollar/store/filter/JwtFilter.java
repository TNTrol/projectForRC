package ru.redcollar.store.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.redcollar.store.service.JwtConverterService;
import ru.redcollar.store.dto.JwtTokenUserDto;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtConverterService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("AUTH-TOKEN");

        if (token != null) {
            JwtTokenUserDto userJwt = jwtService.parseJwt(token);

            List<String> roles = userJwt.getRoles()
                    .stream()
                    .map(role -> "ROLE_" + role.getName().toUpperCase())
                    .toList();

            List<GrantedAuthority> authorities = new ArrayList<>();
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            userJwt.getId(),
                            userJwt.getLogin(),
                            authorities
                    ));
        }
        filterChain.doFilter(request, response);
    }
}
