package com.example.demo.advice;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse<T> {
    private LocalDateTime timestamp;
    private T data;

    public ApiResponse(){
        this.timestamp=LocalDateTime.now();
    }

    public ApiResponse(T data){
        this();
        this.data=data;
    }
}
