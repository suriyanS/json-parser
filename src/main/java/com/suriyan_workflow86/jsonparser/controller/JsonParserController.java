package com.suriyan_workflow86.jsonparser.controller;

import com.suriyan_workflow86.jsonparser.exceptions.InvalidJsonException;
import com.suriyan_workflow86.jsonparser.model.Classroom;
import com.suriyan_workflow86.jsonparser.service.JsonParserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class JsonParserController {

    private final JsonParserService service;

    public JsonParserController(JsonParserService service) {
        this.service = service;
    }

    /**
     * POST endpoint for parsing JSON input and returning a Classroom object.
     *
     * @param jsonInput The JSON input representing a Classroom.
     * @return A parsed Classroom object.
     */
    @PostMapping("/parseJson")
    public Classroom parseJson(@RequestBody String jsonInput) {
        // Completes a potentially incomplete JSON string by adding missing closing brackets.
        jsonInput = service.completeJson(jsonInput);
        // Validate the JSON syntax and structure
        if (service.isValidJson(jsonInput)) {
            // Delegate the JSON parsing to the JsonParserService
            return service.parseClassroom(jsonInput);
        } else {
            // Throw a new InvalidJsonException if validation fails
            throw new InvalidJsonException("Invalid JSON format", jsonInput);
        }
    }
}
