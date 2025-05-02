package com.pedropetterini.votacaoctg.exceptions;

public class UserVoteExistsException extends RuntimeException {
    public UserVoteExistsException(String message) {
        super(message);
    }
}
