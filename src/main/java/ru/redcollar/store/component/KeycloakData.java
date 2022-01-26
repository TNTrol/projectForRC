package ru.redcollar.store.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class KeycloakData {

    @Value("${keycloak.username}")
    private String username;

    @Value("${keycloak.grant_type}")
    private String grantType;

    @Value("${keycloak.password}")
    private String password;

    @Value("${keycloak.client_id}")
    private String clientId;

    public Map<String, String> getData(){
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("grant_type", grantType);
        map.put("client_id", clientId);
        map.put("password", password);
        return map;
    }
}
