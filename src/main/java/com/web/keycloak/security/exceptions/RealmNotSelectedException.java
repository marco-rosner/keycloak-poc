package com.web.keycloak.security.exceptions;

/**
 * Class RealmNotSelectedException
 */
public class RealmNotSelectedException extends RuntimeException {

    public RealmNotSelectedException() {

    }

    public RealmNotSelectedException(final String message) {
        super(message);
    }

}
