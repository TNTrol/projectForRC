package ru.redcollar.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.redcollar.store.component.KeycloakAuthorization;
import ru.redcollar.store.exceptions.MailServiceException;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakService {

    private final KeycloakAuthorization authorization;

    @Value("#{${mail.data}}")
    private Map<String, String> authorizationData;

    private String token;

    public String getToken(){
        if(!StringUtils.isNotBlank(token)){
            try {
                token = authorization.getToken(authorizationData).getAccessToken();
                log.info("Mail's client authorization is success");
            }catch (Exception e){
                throw new MailServiceException("Mail's client don't authorization");
            }
        }
        return token;
    }

}
