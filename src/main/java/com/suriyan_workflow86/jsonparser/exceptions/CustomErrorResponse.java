package com.suriyan_workflow86.jsonparser.exceptions;

import java.time.LocalDateTime;

public class CustomErrorResponse {
    private final String message;
    private final LocalDateTime timestamp;
    private final String jsonString;

    public CustomErrorResponse(String message, String jsonString) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.jsonString = jsonString;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getJsonString() {
        return jsonString;
    }
}
