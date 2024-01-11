# json-parser

**Author:** Suriyan Selvaraj

## Overview

**json-parser** is a Spring Boot application written in Java that focuses on parsing incomplete JSON strings with a specific format. The primary goal is to handle incomplete JSON strings while avoiding parsing invalid JSON.

### JSON Format Supported

The application currently parses JSON strings adhering to the following format:

```json
{
  "className": "string",
  "description": "string",
  "numberOfStudents": 0,
  "groups": [
    {
      "group": 0,
      "students": [
        {
          "name": "string",
          "needSupport": true
        }
      ]
    }
  ],
  "naughtyList": [
    {
      "name": "string",
      "needSupport": true
    }
  ]
}

```

## How to Run

### Prerequisites
- Java 21
- Git
- Maven

### Instructions
1. Clone the repository:
    ```bash
    git clone https://github.com/your-username/json-parser.git
    ```
2. Navigate to the project directory:
    ```bash
    cd json-parser
    ```
3. Build the application:
    ```bash
    ./mvnw clean install
    ```
4. Run the application:
    ```bash
    ./mvnw spring-boot:run
    ```
5. Accessing Application and Swagger API Documentation:

     - **Application URL:** [http://localhost:8080/parseJson](http://localhost:8080/parseJson)
    - **Swagger URL:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
   

## Design Approach

### JSON Parsing

In order to handle incomplete JSON strings effectively, the application employs a `JsonParserService`. The parsing process includes:

- **Completion of Incomplete JSON:**
  - Adds missing closing brackets to potentially incomplete JSON strings.
  
- **Validation of JSON:**
  - Ensures correct JSON syntax and structure.

- **Whitespace Handling:**
  - Removes unnecessary whitespaces for streamlined processing.

- **Curly Braces and Commas:**
  - Manages leading and trailing curly braces and commas appropriately.

- **Array Key-Value Pairs:**
  - Identifies and processes array key-value pairs within the JSON value.

- **Handling Arrays:**
  - Manages key-value pairs with array values by extracting and setting them in the `Classroom` object.

- **Key-Value Pair Processing:**
  - Splits a JSON string into individual key-value pairs.
  - Verifies the correctness of each key-value pair and sets them in the `Classroom` object.

This approach ensures that the JSON parsing is robust, covering various scenarios and enhancing the reliability of the application.


## Future Enhancement

### Improved Handling of Incomplete JSON

One potential future enhancement for the application is to further improve the handling of incomplete JSON strings. The goal would be to enhance the parser's capabilities to gracefully handle a wider range of incomplete JSON structures.

This improvement might involve:

- **Advanced Recovery Mechanisms:**
  - Implementing more sophisticated strategies for recovering from incomplete or malformed JSON, allowing the parser to make intelligent assumptions and complete missing parts.

- **Extended Validation:**
  - Strengthening the JSON validation process to identify and provide more detailed feedback on the specific issues within incomplete JSON strings.

- **User-Friendly Error Messages:**
  - Enhancing error reporting to provide clear and user-friendly messages, aiding users in understanding and correcting issues with their JSON input.

By continuously refining the JSON parsing capabilities, the application can offer a more versatile and user-friendly experience, accommodating a broader range of real-world scenarios.

## Notes
- The application is developed without using external libraries for JSON parsing.
- It is designed to handle incomplete JSON strings based on the specified format.
- Feel free to explore and contribute to enhance the capabilities of this JSON parser!


