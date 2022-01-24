package ru.redcollar.store.component;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import ru.redcollar.store.domain.model.KeycloakData;
import ru.redcollar.store.domain.model.KeycloakToken;

@FeignClient(name = "KeycloakService", url = "${mail.auth-address}")
public interface KeycloakAuthorization {

    @PostMapping(consumes = "application/x-www-form-urlencoded")
    KeycloakToken sendMail(KeycloakData data);
}
