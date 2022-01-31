package ru.redcollar.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.redcollar.store.component.MailClient;
import ru.redcollar.store.dto.MailDto;
import ru.redcollar.store.exceptions.MailServiceException;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final MailClient senderMail;

    public void sendMail(MailDto mail) {
        try {
            senderMail.sendMail(mail);
            log.info("Send check of offer");
        } catch (Exception e) {
            log.error("Mail don't sent!\n" + e.getMessage());
            throw new MailServiceException("Mail don't sent!");
        }
    }
}
