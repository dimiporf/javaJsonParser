package jsonparser;

import java.util.ArrayList;
import java.util.List;

/**
 * The JsonSplitter class provides methods to split JSON strings into key-value pairs or array values.
 */
public class JsonSplitter {

    /**
     * Splits a JSON object content into individual key-value pairs, considering nested structures.
     * @param objectContent The JSON object content to split.
     * @return A list of key-value pairs as strings.
     */
    public static List<String> splitKeyValuePairs(String objectContent) {
        List<String> keyValuePairs = new ArrayList<>();
        int braceDepth = 0; // Track depth of nested braces
        int startIndex = 0; // Start index for extracting substrings
        boolean insideString = false; // Flag to track if inside a string

        for (int i = 0; i < objectContent.length(); i++) {
            char c = objectContent.charAt(i);

            // Check for start or end of strings
            if (c == '"' && (i == 0 || objectContent.charAt(i - 1) != '\\')) {
                insideString = !insideString; // Toggle insideString flag
            } else if (c == '{' && !insideString) {
                braceDepth++; // Increase brace depth for nested objects
            } else if (c == '}' && !insideString) {
                braceDepth--; // Decrease brace depth at end of objects
            } else if (c == ',' && braceDepth == 0 && !insideString) {
                // Split at comma if not inside string and at top level of braces
                keyValuePairs.add(objectContent.substring(startIndex, i).trim());
                startIndex = i + 1; // Update start index for next substring
            }
        }

        // Add the last key-value pair after loop ends
        keyValuePairs.add(objectContent.substring(startIndex).trim());
        return keyValuePairs;
    }

    /**
     * Splits a JSON array content into individual values, considering nested structures.
     * @param arrayContent The JSON array content to split.
     * @return A list of values as strings.
     */
    public static List<String> splitArrayValues(String arrayContent) {
        List<String> values = new ArrayList<>();
        int braceDepth = 0; // Track depth of nested braces
        int startIndex = 0; // Start index for extracting substrings
        boolean insideString = false; // Flag to track if inside a string

        for (int i = 0; i < arrayContent.length(); i++) {
            char c = arrayContent.charAt(i);

            // Check for start or end of strings
            if (c == '"' && (i == 0 || arrayContent.charAt(i - 1) != '\\')) {
                insideString = !insideString; // Toggle insideString flag
            } else if (c == '{' && !insideString) {
                braceDepth++; // Increase brace depth for nested objects
            } else if (c == '}' && !insideString) {
                braceDepth--; // Decrease brace depth at end of objects
            } else if (c == ',' && braceDepth == 0 && !insideString) {
                // Split at comma if not inside string and at top level of braces
                values.add(arrayContent.substring(startIndex, i).trim());
                startIndex = i + 1; // Update start index for next substring
            }
        }

        // Add the last value after loop ends
        values.add(arrayContent.substring(startIndex).trim());
        return values;
    }
}
