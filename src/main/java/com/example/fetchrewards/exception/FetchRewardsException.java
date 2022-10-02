package com.example.fetchrewards.exception;

import com.example.fetchrewards.constants.ErrorCodes;

public class FetchRewardsException extends Exception {

    private final ErrorCodes errorCode;

    public FetchRewardsException(ErrorCodes ec, String message) {
        super(message);
        errorCode = ec;
    }

    public FetchRewardsException(ErrorCodes ec, String message, Throwable source) {
        super(message, source);
        errorCode = ec;
    }

    public ErrorCodes getErrorCode() {
        return errorCode;
    }
}
