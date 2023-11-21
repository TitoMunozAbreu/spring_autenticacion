package com.example.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice()
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = {UserExistException.class})
    public ResponseEntity<Object> hanldeUserExistException(HttpServletRequest request,
                                                           UserExistException exception){
        String errorMsg = exception.getMessage();
        String path = request.getRequestURI();

        return buildResponseEntity(new ApiException(
                HttpStatus.CONFLICT,
                errorMsg,
                path));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiException apiException) {
        return new ResponseEntity<Object>(apiException, apiException.getHttpStatus());
    }

}
