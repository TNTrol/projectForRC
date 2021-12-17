package ru.redcollar.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.redcollar.store.domain.model.Mail;

@Service
@RequiredArgsConstructor
public class SenderMailService {

    private final RestTemplate restTemplate;

    @Value("${mail.address}")
    private String mailAddress;

    public void sendMail(Mail mail) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Mail> entity = new HttpEntity<>(mail, headers);
        String answer = restTemplate.postForObject(mailAddress, entity, String.class);
    }

}
