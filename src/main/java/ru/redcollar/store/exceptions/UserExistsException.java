package ru.redcollar.store.exceptions;

public class UserExistsException extends RuntimeException{
    public UserExistsException(String str)
    {
        super(str);
    }
}
