package com.luciano.springboot.ms.products.app.exception;

import com.luciano.springboot.ms.products.app.models.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public Mono<ResponseEntity<ErrorDto>> handleProductNotFound(ProductNotFoundException ex) {
        ErrorDto error = new ErrorDto(ex.getMessage(), "Producto no encontrado",
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());
        return Mono.just(new ResponseEntity<>(error, HttpStatus.NOT_FOUND));
    }
}
