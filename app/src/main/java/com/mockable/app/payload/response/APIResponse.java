package com.mockable.app.payload.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse<T> {
    private HttpStatus status;
    private boolean success;
    private T data;
    private String message;
}
