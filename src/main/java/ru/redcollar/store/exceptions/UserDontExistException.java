package ru.redcollar.store.exceptions;

public class UserDontExistException extends RuntimeException {

    public UserDontExistException(String login) {
        super("User " + login + " don't exist");
    }
}
