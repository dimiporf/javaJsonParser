package jsonparser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

/**
 * Main class for handling JSON file input and validation.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Main loop to handle user input for JSON file paths
        while (true) {
            System.out.print("Enter the path to the JSON file or 'exit' to quit: ");
            String input = scanner.nextLine();

            // Exit the loop if the user types 'exit'
            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            File file = new File(input);

            // Check if the file exists and is not a directory
            if (file.exists() && !file.isDirectory()) {
                try {
                    // Read the JSON content from the file
                    String jsonContent = new String(Files.readAllBytes(file.toPath()));
                    String[] structuredLog = new String[1]; // Array to store the structured log

                    // Validate the JSON content using JsonParser.isValidJson
                    if (JsonParser.isValidJson(jsonContent, structuredLog)) {
                        System.out.println("The JSON in the provided file is valid. Here are the results:");
                        System.out.println(structuredLog[0]); // Display the structured log
                    } else {
                        System.out.println("Invalid JSON");
                    }
                } catch (IOException e) {
                    System.out.println("Error reading file: " + e.getMessage());
                }
            } else {
                System.out.println("File not found. Please try again.");
            }

            // Prompt the user to press Enter to continue or type 'exit' to quit
            System.out.println("Press Enter to continue or type 'exit' to quit...");
            if (scanner.nextLine().equalsIgnoreCase("exit")) {
                break;
            }
        }

        // Close the scanner when done
        scanner.close();
    }
}
