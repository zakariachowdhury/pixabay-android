package com.zakariachowdhury.pixabay.event;

/**
 * Created by Zakaria Chowdhury on 11/10/17.
 */

public class ErrorEvent {
    private final String message;

    public ErrorEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
