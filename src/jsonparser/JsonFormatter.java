package jsonparser;

import java.util.List;
import java.util.Map;

public class JsonFormatter {

    public static String formatLog(Map<String, String> parsedContents) {
        StringBuilder log = new StringBuilder("{\n");

        for (Map.Entry<String, String> entry : parsedContents.entrySet()) {
            log.append(String.format("  \"%s\": \"%s\",\n", entry.getKey(), entry.getValue()));
        }

        log.setLength(log.length() - 2); // Remove the last comma
        log.append("\n}");
        return log.toString();
    }

    public static String formatArrayLog(List<String> values) {
        StringBuilder log = new StringBuilder("[\n");

        for (String value : values) {
            log.append(String.format("  \"%s\",\n", value));
        }

        log.setLength(log.length() - 2); // Remove the last comma
        log.append("\n]");
        return log.toString();
    }
}
