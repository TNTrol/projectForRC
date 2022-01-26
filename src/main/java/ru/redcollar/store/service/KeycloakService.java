package ru.redcollar.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.redcollar.store.component.KeycloakAuthorization;
import ru.redcollar.store.component.KeycloakData;
import ru.redcollar.store.exceptions.MailServiceException;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakService {

    private final KeycloakAuthorization authorization;
    private final KeycloakData keycloakData;

    public String getToken() {
        try {

            String token = authorization.getToken(keycloakData.getData()).getAccessToken();
            log.info("Mail's client authorization is success");
            return token;
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new MailServiceException("Mail's client don't authorization");
        }
    }

}
