package ru.redcollar.store.exceptions;

public class MailServiceException extends RuntimeException {

    public MailServiceException(String message) {
        super(message);
    }
}
