package jsonparser;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The JsonParser class provides methods for validating and parsing JSON content.
 */
public class JsonParser {

    /**
     * Validates the provided JSON content and generates a structured log of the parsed contents.
     * @param json The JSON string to validate and parse.
     * @param structuredLog An array to store the structured log of the parsed JSON content.
     *                      Index 0 will hold the structured log string.
     * @return True if the JSON is valid, false otherwise.
     */
    public static boolean isValidJson(String json, String[] structuredLog) {
        structuredLog[0] = "";

        // Check if JSON string is null or empty
        if (json == null || json.trim().isEmpty()) {
            return false;
        }

        // Remove unnecessary whitespace characters from JSON string
        json = json.replace("\n", "").replace("\r", "").replace("\t", "").replace(" ", "");

        // Check if JSON syntax is valid
        if (!isJsonSyntaxValid(json)) {
            return false;
        }

        // Determine if JSON is an array or object and handle accordingly
        if (json.startsWith("[") && json.endsWith("]")) {
            return handleArray(json, structuredLog);
        } else if (json.startsWith("{") && json.endsWith("}")) {
            return handleObject(json, structuredLog);
        }

        return false; // JSON format is invalid
    }

    /**
     * Handles parsing and formatting of JSON arrays.
     * @param json The JSON array string to handle.
     * @param structuredLog An array to store the structured log of the parsed JSON content.
     *                      Index 0 will hold the structured log string.
     * @return True if the array was handled successfully, false otherwise.
     */
    private static boolean handleArray(String json, String[] structuredLog) {
        structuredLog[0] = "";
        json = json.substring(1, json.length() - 1).trim(); // Remove outer brackets

        // Handle empty array case
        if (json.isEmpty()) {
            structuredLog[0] = "[]";
            return true;
        }

        // Split array into individual values and format the log
        List<String> values = JsonSplitter.splitArrayValues(json);
        structuredLog[0] = JsonFormatter.formatArrayLog(values);
        return true;
    }

    /**
     * Handles parsing and formatting of JSON objects.
     * @param json The JSON object string to handle.
     * @param structuredLog An array to store the structured log of the parsed JSON content.
     *                      Index 0 will hold the structured log string.
     * @return True if the object was handled successfully, false otherwise.
     */
    private static boolean handleObject(String json, String[] structuredLog) {
        structuredLog[0] = "";
        json = json.substring(1, json.length() - 1).trim(); // Remove outer braces

        // Handle empty object case
        if (json.isEmpty()) {
            structuredLog[0] = "{}";
            return true;
        }

        // Split key-value pairs and parse into a map
        List<String> keyValuePairs = JsonSplitter.splitKeyValuePairs(json);
        Map<String, String> parsedContents = new HashMap<>();

        for (String pair : keyValuePairs) {
            int colonIndex = pair.indexOf(':');
            if (colonIndex == -1) return false; // Invalid pair format

            String key = pair.substring(0, colonIndex).trim();
            String value = pair.substring(colonIndex + 1).trim();

            // Validate key format
            if (!(key.startsWith("\"") && key.endsWith("\""))) return false;
            key = key.substring(1, key.length() - 1); // Remove surrounding quotes

            // Add valid key-value pair to map
            if (!key.isEmpty()) {
                parsedContents.put(key, value);
            }
        }

        // Format the log with parsed contents
        structuredLog[0] = JsonFormatter.formatLog(parsedContents);
        return true;
    }

    /**
     * Checks if the syntax of the JSON string is valid.
     * @param json The JSON string to validate.
     * @return True if the JSON syntax is valid, false otherwise.
     */
    private static boolean isJsonSyntaxValid(String json) {
        try {
            // Attempt to parse the JSON using JSONTokener
            new JSONTokener(json).nextValue();
            return true; // JSON syntax is valid
        } catch (Exception e) {
            return false; // Parsing failed, JSON syntax is invalid
        }
    }
}
