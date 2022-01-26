package ru.redcollar.store.component;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.redcollar.store.domain.model.KeycloakToken;

import java.util.Map;

@FeignClient(name = "KeycloakClient", url = "${keycloak.address}")
public interface KeycloakAuthorization {

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KeycloakToken getToken(@RequestBody Map<String, ?> data);
}
