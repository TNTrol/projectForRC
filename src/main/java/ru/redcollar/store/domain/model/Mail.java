package ru.redcollar.store.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Mail {

    private String email;
    private String subject;
    private String text;
}
