package ru.redcollar.store.component;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.redcollar.store.domain.model.Mail;

@FeignClient(name = "MailService", url = "${mail.address}")
public interface SenderMail {

    @PostMapping
    void sendMail(@RequestHeader("AUTH-TOKEN") String token, @RequestBody Mail mail);

}
