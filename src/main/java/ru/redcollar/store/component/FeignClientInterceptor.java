package ru.redcollar.store.component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.Data;
import org.springframework.stereotype.Component;
import ru.redcollar.store.domain.model.KeycloakToken;

@Data
@Component
public class FeignClientInterceptor implements RequestInterceptor {

    private KeycloakToken token;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (token != null) {
            requestTemplate.header("Authorization", String.format("%s %s", "Bearer", token.getAccessToken()));
        }
    }
}