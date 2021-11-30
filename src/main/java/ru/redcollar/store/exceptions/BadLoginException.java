package ru.redcollar.store.exceptions;

public class BadLoginException extends RuntimeException{

    public BadLoginException(String str)
    {
        super(str);
    }
}
