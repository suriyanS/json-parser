package com.suriyan_workflow86.jsonparser.handlers;

import com.suriyan_workflow86.jsonparser.exceptions.CustomErrorResponse;
import com.suriyan_workflow86.jsonparser.exceptions.InvalidJsonException;
import com.suriyan_workflow86.jsonparser.exceptions.JsonParsingException;
import com.suriyan_workflow86.jsonparser.service.JsonParserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(JsonParserService.class);
    @ExceptionHandler(JsonParsingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<CustomErrorResponse> handleException(JsonParsingException ex) {
        this.logger.error("Error parsing JSON: " + ex.getMessage(), ex);
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                "Error parsing JSON",
                ex.getJsonString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }
    @ExceptionHandler(InvalidJsonException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomErrorResponse> handleInvalidJsonException(InvalidJsonException ex) {
        logger.error("Invalid JSON format: " + ex.getMessage(), ex);
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                ex.getMessage(),
                ex.getJsonString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<CustomErrorResponse> handleGenericException(Exception ex) {
        this.logger.error("An unexpected error occurred: " + ex.getMessage(), ex);
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                "Unexpected error",
                "An unexpected error occurred. Please contact support.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }
}
