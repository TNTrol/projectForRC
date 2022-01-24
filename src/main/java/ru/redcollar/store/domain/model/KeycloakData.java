package ru.redcollar.store.domain.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class KeycloakData {

    private Map<String, String> authorizationData = new HashMap<>();

    public KeycloakData(String name, String password, String grantType, String clientId){
        authorizationData.put("username", name);
        authorizationData.put("password", password);
        authorizationData.put("grant_type", grantType);
        authorizationData.put("client_id", clientId);
    }
}
