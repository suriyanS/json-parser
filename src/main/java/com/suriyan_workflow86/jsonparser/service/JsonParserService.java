package com.suriyan_workflow86.jsonparser.service;

import com.suriyan_workflow86.jsonparser.exceptions.JsonParsingException;
import com.suriyan_workflow86.jsonparser.model.Classroom;
import com.suriyan_workflow86.jsonparser.model.Group;
import com.suriyan_workflow86.jsonparser.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * This service helps to parse the json string.
 * It's capable to parse the incomplete json string but not the invalid json string.
 * At the moment it only parse the @Classroom json structure
 */
@Service
public class JsonParserService {

    private final Logger logger = LoggerFactory.getLogger(JsonParserService.class);
    // classroom holds the entire parsed json structure
    private static Classroom classroom = new Classroom();

    // jsonStack stores the broken json string for parsing one by one.
    private static Stack<String> jsonStack = new Stack<>();

    private static String currentJsonString = "";

    /**
     * Parses a JSON-formatted string to create and populate a Classroom object.
     *
     * @param jsonString The JSON string representing the Classroom data.
     * @return A Classroom object populated with data from the JSON string.
     */
    public Classroom parseClassroom(String jsonString) {
        classroom = new Classroom();
        jsonStack = new Stack<>();

        this.logger.info("Json parsing started...");
        // Remove all whitespaces from the JSON string for processing
        jsonString = jsonString.replaceAll("\\s", "");

        // Check and remove the leading curly brace if present
        if (jsonString.startsWith("{")) {
            jsonString = jsonString.substring(1);
        }

        // Check and remove the trailing curly brace if present
        if (jsonString.endsWith("}")) {
            jsonString = jsonString.substring(0, jsonString.length() - 1);
        }
        try{
            // Process the modified JSON string using the processJsonString function
            processJsonString(jsonString);
        } catch (Exception ex){
           throw new JsonParsingException(ex.getMessage(),ex.getCause(), currentJsonString);
        }

        this.logger.info("Json parsing completed!");
        return classroom;
    }


    /**
     * Processes a JSON string by pushing it onto a stack and iteratively
     * pops and processes JSON sections until the stack is empty.
     *
     * @param jsonString The JSON string to be processed.
     */
    private void processJsonString(String jsonString) {
        // Push the original JSON string onto the stack for processing
        jsonStack.push(jsonString);

        // Continue processing until the stack is empty
        while (!jsonStack.isEmpty()) {
            // Pop the top JSON string from the stack
            currentJsonString= jsonStack.pop();

            // Extract the outermost square bracket-separated values from the JSON string
            String[] brokenValues = splitOutermostSquareBrackets(currentJsonString);

            // Check if there are multiple values within the outermost square brackets
            if (brokenValues.length >= 1) {
                // Complete Json section: Process each individual value within the brackets
                for (String value : brokenValues) {
                    processJsonValue(value);
                }
            } else {
                // Incomplete Json section: Process the entire JSON string as a single value
                processJsonValue(currentJsonString);
            }
        }
    }


    /**
     * Processes a JSON value by handling leading and trailing commas,
     * identifying array key-value pairs, and processing key-value pairs.
     *
     * @param currentString The JSON value to be processed.
     */
    private void processJsonValue(String currentString) {
        // Check and remove a leading comma if present
        if (currentString.startsWith(",")) {
            currentString = currentString.substring(1);
        }

        // Check and remove a trailing comma if present
        if (currentString.endsWith(",")) {
            currentString = currentString.substring(0, currentString.length() - 1);
        }

        // Check if the value contains an array key-value pair (": [")
        if (currentString.contains(":[")) {
            // Handle array key-value pair and return if successful
            if (handleArrayKeyValue(currentString)) {
                return;
            }
        }

        // Process key-value pairs within the JSON value
        processKeyValuePairs(currentString);
    }



    /**
     * Processes key-value pairs within a JSON string by splitting it into individual key-value pairs
     * and then calling the processKeyValuePair function for each pair.
     *
     * @param currentString The JSON string containing key-value pairs to be processed.
     */
    private void processKeyValuePairs(String currentString) {
        // Split the JSON string into individual key-value pairs
        String[] keyValuePairs = currentString.split(",", 2);

        // Process each individual key-value pair
        for (String keyValue : keyValuePairs) {
            processKeyValuePair(keyValue);
        }
    }

    /**
     * Processes an individual key-value pair within a JSON string,
     * checking for correctness and pushing it onto the stack if necessary.
     *
     * @param keyValue The individual key-value pair to be processed.
     */
    private void processKeyValuePair(String keyValue) {
        if (keyValue.length() > 1) {
            // Split the key-value pair into key and value
            String[] parts = keyValue.split(":");

            // Check for correctness and push onto the stack if necessary
            if (parts.length > 2 || keyValue.split(",").length > 1) {
                jsonStack.push(keyValue);
            } else {
                // Process and set key-value pairs in the Classroom object
                setKeyValues(parts);
            }
        }
    }

    /**
     * Handles a key-value pair where the value is an array,
     * extracts and sets the key-value pair, and returns true if successful.
     *
     * @param jsonString The JSON string containing the key-value pair.
     * @return True if the array key-value pair is successfully handled, false otherwise.
     */
    private boolean handleArrayKeyValue(String jsonString) {
        String[] tempKeyValuePairs = jsonString.split(":", 2);

        if (tempKeyValuePairs[1].startsWith("[")) {
            // Handle array key-value pair and return true if successful
            String[] dummyPairs = splitOutermostSquareBrackets(tempKeyValuePairs[1]);
            tempKeyValuePairs[1] = dummyPairs.length >= 1 ? dummyPairs[0] : tempKeyValuePairs[1];
            setKeyValues(tempKeyValuePairs);
            return true;
        }

        return false;
    }

    /**
     * Sets key-value pairs in the Classroom object based on the provided array.
     *
     * @param keyValuePairs The array containing key and value from a key-value pair.
     */
    private void setKeyValues(String[] keyValuePairs) {
        if (keyValuePairs.length == 2) {
            String key = keyValuePairs[0].replaceAll("\"", "");
            String value = keyValuePairs[1].replaceAll("\"", "");

            switch (key) {
                case "className":
                    classroom.setClassName(value);
                    break;
                case "description":
                    classroom.setDescription(value);
                    break;
                case "numberOfStudents":
                    classroom.setNumberOfStudents(Integer.parseInt(value));
                    break;
                case "groups":
                    classroom.setGroups(parseGroups(value));
                    break;
                case "naughtyList":
                    classroom.setNaughtyList(parseStudents(value));
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Parses a string representation of groups and returns a list of Group objects.
     *
     * @param groupsString The string containing group information in JSON format.
     * @return A list of Group objects.
     */
    private List<Group> parseGroups(String groupsString) {
        List<Group> groups = new ArrayList<>();

        // Check and remove the leading and trailing square brackets if present
        if (groupsString.startsWith("[")) {
            groupsString = groupsString.substring(1);
        }

        if (groupsString.endsWith("]")) {
            groupsString = groupsString.substring(0, groupsString.length() - 1);
        }

        // Split groups string into individual groups
        String[] groupTokens = splitOutermostCurlyBraces(groupsString);

        if (groupTokens.length >= 1) {
            for (int i = 0; i < groupTokens.length; i++) {
                // Check and remove a leading comma if present
                if (groupTokens[i].startsWith(",")) {
                    groupTokens[i] = groupTokens[i].substring(1);
                }
                Group group = new Group();

                // Extract group number
                String groupNumber = groupTokens[i].substring(groupTokens[i].indexOf("group:") + 6, groupTokens[i].indexOf(","));

                group.setGroup(Integer.parseInt(groupNumber));

                // Extract students section
                String studentsSection = groupTokens[i].substring(groupTokens[i].indexOf("students:[") + 10, groupTokens[i].indexOf("]")).trim();
                group.setStudents(parseStudents(studentsSection));

                groups.add(group);
            }
        }

        return groups;
    }

    /**
     * Parses a string representation of students and returns a list of Student objects.
     *
     * @param studentsString The string containing student information in JSON format.
     * @return A list of Student objects.
     */
    private  List<Student> parseStudents(String studentsString) {
        List<Student> students = new ArrayList<>();

        // Split students string into individual students
        String[] studentTokens = splitOutermostCurlyBraces(studentsString);

        for (int i = 0; i < studentTokens.length; i++) {
            // Check and remove a leading comma or square bracket if present
            if (studentTokens[i].startsWith(",") || studentTokens[i].startsWith("[")) {
                studentTokens[i] = studentTokens[i].substring(1);
            }

            // Check and remove a trailing square bracket if present
            if (studentTokens[i].endsWith("]")) {
                studentTokens[i] = studentTokens[i].substring(0, studentTokens[i].length() - 1);
            }

            // Check and remove leading and trailing curly braces if present
            if (studentTokens[i].startsWith("{") && studentTokens[i].endsWith("}")) {
                studentTokens[i] = studentTokens[i].substring(1, studentTokens[i].length() - 1);
            }

            // Get and add Student object to the list
            Student student = getStudent(studentTokens[i]);
            students.add(student);
        }

        return students;
    }

    /**
     * Parses a string representation of a student and returns a Student object.
     *
     * @param studentTokens The string containing student properties in JSON format.
     * @return A Student object.
     */
    private Student getStudent(String studentTokens) {
        if (!studentTokens.isBlank()) {
            // Split student properties into individual key-value pairs
            String[] properties = studentTokens.split(",");
            Student student = new Student();

            // Process each key-value pair and set corresponding properties in the Student object
            for (String property : properties) {
                String[] keyValuePairs = property.split(":", 2);
                String key = keyValuePairs[0].replaceAll("\"", "");
                String value = keyValuePairs[1].replaceAll("\"", "");

                switch (key) {
                    case "name":
                        student.setName(value);
                        break;
                    case "needSupport":
                        student.setNeedSupport(Boolean.parseBoolean(value));
                        break;
                    default:
                        break;
                }
            }

            return student;
        }

        return null;
    }

    /**
     * Splits a string into sections enclosed by outermost square brackets.
     *
     * @param input The input string to be processed.
     * @return An array of strings representing sections enclosed by outermost square brackets.
     */
    private String[] splitOutermostSquareBrackets(String input) {
        List<String> objects = new ArrayList<>();
        int openBraces = 0;
        StringBuilder currentObject = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (c == '[') {
                openBraces++;
            } else if (c == ']') {
                openBraces--;
            }

            currentObject.append(c);

            if (openBraces == 0 && c == ']') {
                objects.add(currentObject.toString().trim());
                currentObject.setLength(0);
            }
        }

        return objects.toArray(new String[0]);
    }

    /**
     * Splits a string into sections enclosed by outermost curly braces.
     *
     * @param input The input string to be processed.
     * @return An array of strings representing sections enclosed by outermost curly braces.
     */
    private String[] splitOutermostCurlyBraces(String input) {
        List<String> objects = new ArrayList<>();
        int openBraces = 0;
        StringBuilder currentObject = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (c == '{') {
                openBraces++;
            } else if (c == '}') {
                openBraces--;
            }

            currentObject.append(c);

            if (openBraces == 0 && c == '}') {
                objects.add(currentObject.toString().trim());
                currentObject.setLength(0);
            }
        }

        return objects.toArray(new String[0]);
    }

    /**
     * Completes a potentially incomplete JSON string by adding missing closing brackets.
     *
     * @param inputJson The potentially incomplete JSON string to be completed.
     * @return The completed JSON string.
     */
    public String completeJson(String inputJson) {
        Stack<Character> stack = new Stack<>();
        StringBuilder completedJson = new StringBuilder();
        if (inputJson.startsWith(",")) {
            inputJson = inputJson.substring(1);
        }
        if (inputJson.endsWith(",")) {
            inputJson = inputJson.substring(0,inputJson.length()-1);
        }
        for (char c : inputJson.toCharArray()) {
            if (c == '[' || c == '{') {
                stack.push(c);
            } else if (c == ']' || c == '}') {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            }

            completedJson.append(c);
        }

        while (!stack.isEmpty()) {
            completedJson.append(stack.pop() == '[' ? ']' : '}');
        }

        return completedJson.toString();
    }

    /**
     * Checks whether the provided string is a valid JSON representation.
     * Note: This is a basic check and may not cover all edge cases.
     *
     * @param jsonString The string to be validated as JSON.
     * @return true if the string is a valid JSON representation, false otherwise.
     */
    public boolean isValidJson(String jsonString) {
        try {
            jsonString = jsonString.trim();
            return jsonString.startsWith("{") && jsonString.endsWith("}")
                    || jsonString.startsWith("[") && jsonString.endsWith("]");
        } catch (Exception e) {
            // If an exception occurs during parsing, consider the input as invalid JSON
            return false;
        }
    }


}
