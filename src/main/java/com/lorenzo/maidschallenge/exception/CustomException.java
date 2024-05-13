package com.lorenzo.maidschallenge.exception;

public class CustomException extends RuntimeException {
    private String message;
    private int status;

    public CustomException(String message, int status) {
        this.message = message;
        this.status = status;
    }

}
