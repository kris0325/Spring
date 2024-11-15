package com.google.springboot.model.postgresql;

/**
 * @Author kris
 * @Create 2024-10-31 23:07
 * @Description
 */
public class ApiResponse {
    private String data;

    public ApiResponse(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}