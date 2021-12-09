package ru.redcollar.store.exceptions;

public class ProductDontExistException extends RuntimeException {

    public ProductDontExistException() {
        super("Product don't exist");
    }
}
