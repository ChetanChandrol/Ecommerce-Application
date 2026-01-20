package com.dev.ECommerceProductService.controller.controllerAdvice;

import com.dev.ECommerceProductService.exception.ProductServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

import static com.dev.ECommerceProductService.util.ProductUtil.buildResponse;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException) {
        String message = methodArgumentNotValidException.getBindingResult().getFieldError("title").getDefaultMessage();
        return new ResponseEntity<>(buildResponse(message), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ProductServiceException.class)
    public ResponseEntity<Map<String ,Object>> productServiceException(ProductServiceException productServiceException)
    {
       return new ResponseEntity<>(buildResponse(productServiceException.getMessage()), productServiceException.getHttpStatus());
    }

}
