package com.suriyan_workflow86.jsonparser.exceptions;

public class JsonParsingException extends RuntimeException {

    private final String jsonString;
    public JsonParsingException(String message, Throwable cause, String jsonString) {
        super(message, cause);
        this.jsonString = jsonString;
    }

    public String getJsonString() {
        return jsonString;
    }
}
