package jsonparser;

public class JsonValidator {

    public static boolean isValidValue(String value) {
        value = value.trim();

        if (value.startsWith("\"") && value.endsWith("\"")) return true;
        if (value.equals("true") || value.equals("false")) return true;
        if (value.equals("null")) return true;
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            // Not numeric
        }
        if (value.startsWith("{") && value.endsWith("}")) {
            return JsonParser.isValidJson(value, new String[1]);
        }
        if (value.startsWith("[") && value.endsWith("]")) {
            return isValidArray(value);
        }

        return false;
    }

    private static boolean isValidArray(String array) {
        array = array.trim();

        if (array.length() < 2 || array.charAt(0) != '[' || array.charAt(array.length() - 1) != ']') return false;

        String arrayContent = array.substring(1, array.length() - 1).trim();

        if (arrayContent.isEmpty()) return true;

        var values = JsonSplitter.splitArrayValues(arrayContent);

        for (String value : values) {
            if (!isValidValue(value)) return false;
        }

        return true;
    }
}
