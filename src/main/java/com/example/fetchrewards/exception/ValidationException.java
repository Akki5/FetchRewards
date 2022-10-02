package com.example.fetchrewards.exception;

import com.example.fetchrewards.constants.ErrorCodes;

public class ValidationException extends FetchRewardsException {

    public ValidationException(ErrorCodes ec, String message) {
        super(ec, message);
    }

    public ValidationException(ErrorCodes ec, String message, Throwable source) {
        super(ec, message, source);
    }
}
