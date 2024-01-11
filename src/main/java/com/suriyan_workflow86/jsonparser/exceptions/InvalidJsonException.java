package com.suriyan_workflow86.jsonparser.exceptions;


public class InvalidJsonException extends RuntimeException {

    private final String jsonString;
    public InvalidJsonException(String message, String jsonString) {
        super(message);
        this.jsonString = jsonString;
    }
    public String getJsonString() {
        return jsonString;
    }
}