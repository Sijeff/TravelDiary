package com.example.demo.Repository;

public class TravelloRepositoryException extends RuntimeException {
    public TravelloRepositoryException() {
    }

    public TravelloRepositoryException(String message) {
        super(message);
    }

    public TravelloRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public TravelloRepositoryException(Throwable cause) {
        super(cause);
    }

    public TravelloRepositoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
