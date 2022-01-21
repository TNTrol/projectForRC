package ru.redcollar.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.redcollar.store.component.KeycloakAuthorization;
import ru.redcollar.store.component.SenderMail;
import ru.redcollar.store.domain.model.KeycloakData;
import ru.redcollar.store.domain.model.KeycloakToken;
import ru.redcollar.store.domain.model.Mail;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final SenderMail senderMail;
    private final KeycloakAuthorization keycloakAuthorization;
    private final KeycloakData keycloakData;
    private KeycloakToken token;

    public void sendMail(Mail mail) {
        if (token == null) {
            token = keycloakAuthorization.sendMail(keycloakData);
            log.info("Mail's client authentication");
        }
        senderMail.sendMail(token.getAccess_token(), mail);
        log.info("Send check of offer");
    }
}
