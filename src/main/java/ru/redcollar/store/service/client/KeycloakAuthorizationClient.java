package ru.redcollar.store.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.redcollar.store.dto.KeycloakTokenDto;

import java.util.Map;

@FeignClient(name = "KeycloakClient", url = "${keycloak.address}")
public interface KeycloakAuthorizationClient {

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KeycloakTokenDto getToken(@RequestBody Map<String, ?> data);
}
