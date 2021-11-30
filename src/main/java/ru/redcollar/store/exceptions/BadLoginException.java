package ru.redcollar.store.exceptions;

public class BadLoginException extends Exception{

    public BadLoginException(String str)
    {
        super(str);
    }
}
