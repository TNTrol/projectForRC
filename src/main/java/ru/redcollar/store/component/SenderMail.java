package ru.redcollar.store.component;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.redcollar.store.domain.model.Mail;

@FeignClient(name = "MailClient", url = "${mail.address}")
public interface SenderMail {

    @PostMapping
    void sendMail(@RequestBody Mail mail);

}
