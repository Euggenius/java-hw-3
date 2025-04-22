package com.zoo.application.exception;

public class InvalidTransferException extends RuntimeException {
     public InvalidTransferException(String message) {
        super(message);
    }
}
