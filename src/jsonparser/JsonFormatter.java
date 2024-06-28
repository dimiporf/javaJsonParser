package jsonparser;

import java.util.List;
import java.util.Map;

/**
 * The JsonFormatter class provides methods to format parsed JSON content into loggable strings.
 * It includes methods to format JSON objects and JSON arrays.
 */
public class JsonFormatter {

    /**
     * Formats the parsed JSON object contents into a structured log string.
     * @param parsedContents The map containing the parsed JSON key-value pairs.
     * @return A formatted string representing the structured log of the JSON object content.
     */
    public static String formatLog(Map<String, String> parsedContents) {
        StringBuilder log = new StringBuilder("{\n");

        // Iterate over each key-value pair in the parsedContents map
        for (Map.Entry<String, String> entry : parsedContents.entrySet()) {
            // Append key-value pair in the format "  "key": "value",\n"
            log.append(String.format("  \"%s\": \"%s\",\n", entry.getKey(), entry.getValue()));
        }

        // Remove the last comma and newline character
        if (log.length() > 2) {
            log.setLength(log.length() - 2); // -2 to remove the last comma and newline
        }

        // Append closing brace for JSON object
        log.append("\n}");
        return log.toString();
    }

    /**
     * Formats the parsed JSON array contents into a structured log string.
     * @param values The list of values in the JSON array.
     * @return A formatted string representing the structured log of the JSON array content.
     */
    public static String formatArrayLog(List<String> values) {
        StringBuilder log = new StringBuilder("[\n");

        // Iterate over each value in the values list
        for (String value : values) {
            // Append each value in the format "  "value",\n"
            log.append(String.format("  \"%s\",\n", value));
        }

        // Remove the last comma and newline character
        if (log.length() > 2) {
            log.setLength(log.length() - 2); // -2 to remove the last comma and newline
        }

        // Append closing bracket for JSON array
        log.append("\n]");
        return log.toString();
    }
}
