package ru.redcollar.store.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import ru.redcollar.store.domain.model.Mail;

@FeignClient(name = "MailService", url = "${mail.address}")
public interface SenderMailService {

    @PostMapping
    void sendMail(Mail mail);

}
