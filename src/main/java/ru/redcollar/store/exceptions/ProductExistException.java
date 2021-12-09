package ru.redcollar.store.exceptions;

public class ProductExistException extends RuntimeException {

    public ProductExistException(String name, String type) {
        super("Product " + name + " " + type + " exist");
    }
}
