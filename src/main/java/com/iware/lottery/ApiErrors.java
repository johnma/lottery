package com.iware.lottery;

/**
 * Created by johnma on 2016/11/2.
 */
public final class ApiErrors {

    private static final String PREFIX = "errors.";

    public static final String INVALID_REQUEST = PREFIX + "INVALID_REQUEST";

    private ApiErrors() {
        throw new InstantiationError( "Must not instantiate this class" );
    }
}

