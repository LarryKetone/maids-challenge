package com.lorenzo.maidschallenge.exception;

public class ResourceNotFoundException extends CustomException {
    public ResourceNotFoundException(String message, int code) {
        super(message, code);
    }
}
