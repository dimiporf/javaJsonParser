package jsonparser;
// Main.java
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter the path to the JSON file or 'exit' to quit: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            File file = new File(input);

            if (file.exists() && !file.isDirectory()) {
                try {
                    String jsonContent = new String(Files.readAllBytes(file.toPath()));
                    String[] structuredLog = new String[1];

                    if (JsonParser.isValidJson(jsonContent, structuredLog)) {
                        System.out.println("The JSON in the provided file is valid. Here are the results:");
                        System.out.println(structuredLog[0]);
                    } else {
                        System.out.println("Invalid JSON");
                    }
                } catch (IOException e) {
                    System.out.println("Error reading file: " + e.getMessage());
                }
            } else {
                System.out.println("File not found. Please try again.");
            }

            System.out.println("Press Enter to continue or type 'exit' to quit...");
            if (scanner.nextLine().equalsIgnoreCase("exit")) {
                break;
            }
        }
    }
}
