package ru.redcollar.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.redcollar.store.component.FeignClientInterceptor;
import ru.redcollar.store.component.KeycloakAuthorization;
import ru.redcollar.store.component.SenderMail;
import ru.redcollar.store.domain.model.KeycloakData;
import ru.redcollar.store.domain.model.Mail;
import ru.redcollar.store.exceptions.MailServiceException;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final SenderMail senderMail;
    private final KeycloakAuthorization keycloakAuthorization;
    private final KeycloakData keycloakData;
    private final FeignClientInterceptor feignClientInterceptor;

    public void sendMail(Mail mail) {
        if (feignClientInterceptor.getToken() == null) {
            try {
                feignClientInterceptor.setToken(keycloakAuthorization.getToken(keycloakData.getAuthorizationData()));
                log.info("Mail's client authentication");
            } catch (Exception e) {
                log.error("Mail's client don't authentication");
                throw new MailServiceException("Mail's client don't authentication");
            }
        }
        try {
            senderMail.sendMail(mail);
            log.info("Send check of offer");
        } catch (Exception e) {
            log.error("Mail don't sent!");
            throw new MailServiceException("Mail don't sent!");
        }
    }
}
