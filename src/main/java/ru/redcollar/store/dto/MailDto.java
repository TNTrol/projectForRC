package ru.redcollar.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MailDto {

    private String email;
    private String subject;
    private String text;
}
