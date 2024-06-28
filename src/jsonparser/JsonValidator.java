package jsonparser;

/**
 * The JsonValidator class provides methods to validate individual JSON values.
 */
public class JsonValidator {

    /**
     * Validates a JSON value.
     * @param value The JSON value to validate.
     * @return True if the value is valid, false otherwise.
     */
    public static boolean isValidValue(String value) {
        value = value.trim(); // Trim whitespace characters from the value

        // Check if the value is a string (enclosed in double quotes)
        if (value.startsWith("\"") && value.endsWith("\"")) return true;

        // Check if the value is a boolean (true or false)
        if (value.equals("true") || value.equals("false")) return true;

        // Check if the value is null
        if (value.equals("null")) return true;

        // Check if the value is numeric
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            // Not numeric
        }

        // Check if the value is an object (starts with '{' and ends with '}')
        if (value.startsWith("{") && value.endsWith("}")) {
            return JsonParser.isValidJson(value, new String[1]); // Validate using JsonParser
        }

        // Check if the value is an array (starts with '[' and ends with ']')
        if (value.startsWith("[") && value.endsWith("]")) {
            return isValidArray(value); // Validate the array
        }

        return false; // Invalid value format
    }

    /**
     * Validates a JSON array.
     * @param array The JSON array to validate.
     * @return True if the array is valid, false otherwise.
     */
    private static boolean isValidArray(String array) {
        array = array.trim(); // Trim whitespace characters from the array

        // Check if the array starts with '[' and ends with ']'
        if (array.length() < 2 || array.charAt(0) != '[' || array.charAt(array.length() - 1) != ']') return false;

        // Remove outer brackets to isolate the array content
        String arrayContent = array.substring(1, array.length() - 1).trim();

        // Handle empty array
        if (arrayContent.isEmpty()) return true;

        // Split the array content into individual values
        var values = JsonSplitter.splitArrayValues(arrayContent);

        // Validate each value in the array
        for (String value : values) {
            if (!isValidValue(value)) return false;
        }

        return true; // Array is valid
    }
}
