package com.pedropetterini.votacaoctg.exceptions;

public class VotesExceededException extends RuntimeException {
    public VotesExceededException(String message) {
        super(message);
    }
}
