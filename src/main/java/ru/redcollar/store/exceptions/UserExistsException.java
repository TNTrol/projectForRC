package ru.redcollar.store.exceptions;

public class UserExistsException extends Exception{
    public UserExistsException(String str)
    {
        super(str);
    }
}
