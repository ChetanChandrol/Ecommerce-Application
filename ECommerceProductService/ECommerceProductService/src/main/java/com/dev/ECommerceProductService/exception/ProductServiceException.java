package com.dev.ECommerceProductService.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@Getter
@Setter
public class ProductServiceException extends RuntimeException {
    private HttpStatus httpStatus;

    public ProductServiceException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
