package com.mockable.app.exceptions.handler;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.mockable.app.payload.response.APIResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<Object>> handleAllExceptions(Exception ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new APIResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, false, null, ex.getMessage()));
    }

    //When the request to the API fails
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<APIResponse<Object>> handleHttpClientErrorException(HttpClientErrorException exception) {
        return ResponseEntity
                .status(exception.getStatusCode())
                .body(new APIResponse<>((HttpStatus) exception.getStatusCode(), false, "", exception.getResponseBodyAsString()));
    }

    //When there is a connection issue.
    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<APIResponse<Object>> handleResourceAccessException(ResourceAccessException exception) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new APIResponse<>(HttpStatus.SERVICE_UNAVAILABLE, false, "", "Service Unavailable: " + exception.getMessage()));
    }

    //issue while saving data to the database.
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<APIResponse<Object>> handleDataAccessException(DataAccessException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new APIResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, false, "", "Database Error: " + exception.getMessage()));
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<APIResponse<Object>> handleDataAccessException(JsonProcessingException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new APIResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, false, "", "JSON Processing Error: " + exception.getMessage()));
    }
}
