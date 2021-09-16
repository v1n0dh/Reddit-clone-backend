package org.ragnarok.redditclone.exception;

public class ApiException extends RuntimeException {

    public ApiException(String exMessage) {
        super(exMessage);
    }
}
