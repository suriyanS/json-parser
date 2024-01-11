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
   
## How to Send a Request to `parseJson` Endpoint

To parse a JSON input and obtain the corresponding `Classroom` object, you can send a POST request to the `parseJson` endpoint using a tool like `curl`, Postman, or any HTTP client.

### Example using `curl`

```bash
curl -X POST -H "Content-Type: application/json" -d '{
  "className": "year 1",
  "description": "class for year 1",
  "numberOfStudents": 5,
  "groups": [
    {
      "group": 1,
      "students": [
        {
          "name": "student A",
          "needSupport": false
        },
        {
          "name": "student B",
          "needSupport": true
        },
        {
          "name": "student C",
          "needSupport": false
        }
      ]
    },
    {
      "group": 2,
      "students": [
        {
          "name": "student D",
          "needSupport": false
        },
        {
          "name": "student E",
          "needSupport": true
        }
      ]
    }
  ],
  "naughtyList": [
    {
      "name": "student E",
      "needSupport": true
    }
  ]
}' http://localhost:8080/parseJson
```

### Example using Postman

1. Open Postman.
2. Set the request type to POST.
3. Enter the URL: [http://localhost:8080/parseJson](http://localhost:8080/parseJson).
4. Set the request body to the provided JSON data.
5. Click the "Send" button.

Upon successful parsing, the API will return a response with the details of the parsed `Classroom` object.


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


