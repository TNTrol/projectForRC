package ru.redcollar.store.component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.redcollar.store.service.KeycloakService;

@Component
@RequiredArgsConstructor
public class FeignClientInterceptor implements RequestInterceptor {

    private final KeycloakService keycloakService;

    @Override
    public void apply(RequestTemplate requestTemplate) {
            requestTemplate.header("Authorization", "Bearer " + keycloakService.getToken());
    }
}