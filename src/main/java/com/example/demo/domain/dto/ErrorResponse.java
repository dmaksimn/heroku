package com.example.demo.domain.dto;

public class ErrorResponse {
    private String message;
    private int httpCode;

    public ErrorResponse(String message, int httpCode) {
        this.message = message;
        this.httpCode = httpCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "message='" + message + '\'' +
                ", httpCode=" + httpCode +
                '}';
    }
}
