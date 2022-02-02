package ru.redcollar.store.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.redcollar.store.dto.MailDto;

@FeignClient(name = "MailClient", url = "${mail.address}")
public interface MailClient {

    @PostMapping
    void sendMail(@RequestBody MailDto mail);

}
