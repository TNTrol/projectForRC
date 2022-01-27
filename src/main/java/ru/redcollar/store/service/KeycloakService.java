package ru.redcollar.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.redcollar.store.component.KeycloakAuthorizationClient;
import ru.redcollar.store.exceptions.MailServiceException;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakService {

    private final KeycloakAuthorizationClient authorization;

    @Value("${keycloak.username}")
    private String username;

    @Value("${keycloak.password}")
    private String password;

    @Cacheable("token")
    public String getToken() {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("username", username);
            map.put("password", password);
            map.put("grant_type", "password");
            map.put("client_id", "mail");
            String token = authorization.getToken(map).getAccessToken();
            log.info("Mail's client authorization is success");
            return token;
        } catch (Exception e) {
            log.error("Mail's client don't authorization\n" + e.getMessage());
            throw new MailServiceException("Mail's client don't authorization");
        }
    }

}
