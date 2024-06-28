package jsonparser;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonParser {

    public static boolean isValidJson(String json, String[] structuredLog) {
        structuredLog[0] = "";

        if (json == null || json.trim().isEmpty()) {
            return false;
        }

        json = json.replace("\n", "").replace("\r", "").replace("\t", "").replace(" ", "");

        if (!isJsonSyntaxValid(json)) {
            return false;
        }

        if (json.startsWith("[") && json.endsWith("]")) {
            return handleArray(json, structuredLog);
        } else if (json.startsWith("{") && json.endsWith("}")) {
            return handleObject(json, structuredLog);
        }

        return false;
    }

    private static boolean handleArray(String json, String[] structuredLog) {
        structuredLog[0] = "";
        json = json.substring(1, json.length() - 1).trim();

        if (json.isEmpty()) {
            structuredLog[0] = "[]";
            return true;
        }

        List<String> values = JsonSplitter.splitArrayValues(json);
        structuredLog[0] = JsonFormatter.formatArrayLog(values);
        return true;
    }

    private static boolean handleObject(String json, String[] structuredLog) {
        structuredLog[0] = "";
        json = json.substring(1, json.length() - 1).trim();

        if (json.isEmpty()) {
            structuredLog[0] = "{}";
            return true;
        }

        List<String> keyValuePairs = JsonSplitter.splitKeyValuePairs(json);
        Map<String, String> parsedContents = new HashMap<>();

        for (String pair : keyValuePairs) {
            int colonIndex = pair.indexOf(':');
            if (colonIndex == -1) return false;

            String key = pair.substring(0, colonIndex).trim();
            String value = pair.substring(colonIndex + 1).trim();

            if (!(key.startsWith("\"") && key.endsWith("\""))) return false;
            key = key.substring(1, key.length() - 1);

            if (!key.isEmpty()) {
                parsedContents.put(key, value);
            }
        }

        structuredLog[0] = JsonFormatter.formatLog(parsedContents);
        return true;
    }

    private static boolean isJsonSyntaxValid(String json) {
        try {
            new JSONTokener(json).nextValue();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
