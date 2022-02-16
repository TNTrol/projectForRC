package ru.redcollar.store.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.redcollar.store.dto.DeliveryDto;
import ru.redcollar.store.dto.DeliveryProductDto;
import ru.redcollar.store.entity.Offer;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    @Value("${message.topic.name}")
    private String topic;

    @Value("${store.address}")
    private String address;

    private final AmqpTemplate template;

    public void handDelivery(Offer offer) {
        DeliveryDto delivery = new DeliveryDto();
        delivery.setUserId(offer.getUser().getId());
        delivery.setFromAddress(address);
        delivery.setToAddress(offer.getUser().getAddress());
        delivery.setProducts(offer.getProducts().stream().map(p -> new DeliveryProductDto(p.getProduct().getId())).toList());
        template.convertAndSend(topic, delivery);
    }

}
