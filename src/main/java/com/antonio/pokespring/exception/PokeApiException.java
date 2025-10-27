package com.antonio.pokespring.exception;

public class PokeApiException extends RuntimeException {
    public PokeApiException(String message, Throwable cause) {
        super(message, cause);
    }
}