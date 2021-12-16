package ru.redcollar.store.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.redcollar.store.domain.entity.Product;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = UserDontExistException.class)
    public ResponseEntity<String> handleUserDontExists(UserDontExistException ex)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(value = UserExistsException.class)
    public ResponseEntity<String> handleUserExists(UserExistsException ex)
    {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(value = ProductExistException.class)
    public ResponseEntity<String> handleProductExists(ProductExistException ex)
    {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(value = ProductDontExistException.class)
    public ResponseEntity<String> handleProductDontExists(ProductDontExistException ex)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(value = BadLoginException.class)
    public ResponseEntity<String> handleProductExists(BadLoginException ex)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
