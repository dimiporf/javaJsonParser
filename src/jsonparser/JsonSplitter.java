package jsonparser;

import java.util.ArrayList;
import java.util.List;

public class JsonSplitter {

    public static List<String> splitKeyValuePairs(String objectContent) {
        List<String> keyValuePairs = new ArrayList<>();
        int braceDepth = 0;
        int startIndex = 0;
        boolean insideString = false;

        for (int i = 0; i < objectContent.length(); i++) {
            char c = objectContent.charAt(i);

            if (c == '"' && (i == 0 || objectContent.charAt(i - 1) != '\\')) {
                insideString = !insideString;
            } else if (c == '{' && !insideString) {
                braceDepth++;
            } else if (c == '}' && !insideString) {
                braceDepth--;
            } else if (c == ',' && braceDepth == 0 && !insideString) {
                keyValuePairs.add(objectContent.substring(startIndex, i).trim());
                startIndex = i + 1;
            }
        }

        keyValuePairs.add(objectContent.substring(startIndex).trim());
        return keyValuePairs;
    }

    public static List<String> splitArrayValues(String arrayContent) {
        List<String> values = new ArrayList<>();
        int braceDepth = 0;
        int startIndex = 0;
        boolean insideString = false;

        for (int i = 0; i < arrayContent.length(); i++) {
            char c = arrayContent.charAt(i);

            if (c == '"' && (i == 0 || arrayContent.charAt(i - 1) != '\\')) {
                insideString = !insideString;
            } else if (c == '{' && !insideString) {
                braceDepth++;
            } else if (c == '}' && !insideString) {
                braceDepth--;
            } else if (c == ',' && braceDepth == 0 && !insideString) {
                values.add(arrayContent.substring(startIndex, i).trim());
                startIndex = i + 1;
            }
        }

        values.add(arrayContent.substring(startIndex).trim());
        return values;
    }
}
