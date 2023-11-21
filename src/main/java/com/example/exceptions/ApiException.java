package com.example.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiException {
    private HttpStatus httpStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "ddMMyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;

    private String path;


    public ApiException(){
        this.timestamp = LocalDateTime.now();
    }

    public ApiException(HttpStatus httpStatus){
        this();
        this.httpStatus = httpStatus;
    }

    public ApiException(HttpStatus httpStatus, String message, String path) {
        this();
        this.httpStatus = httpStatus;
        this.message = message;
        this.path = path;
    }

    public ApiException(HttpStatus httpStatus, String message) {
        this();
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public ApiException(HttpStatus httpStatus, LocalDateTime timestamp, String message) {
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
