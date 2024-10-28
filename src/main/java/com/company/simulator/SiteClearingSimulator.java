package com.company.simulator;

import com.company.simulator.command.AdvanceCommand;
import com.company.simulator.command.Command;
import com.company.simulator.command.LeftCommand;
import com.company.simulator.command.QuitCommand;
import com.company.simulator.command.RightCommand;
import com.company.simulator.model.Bulldozer;
import com.company.simulator.model.SiteMap;
import com.company.simulator.service.BulldozerService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SiteClearingSimulator {
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Error:Site map file path not passed in Arguments");
            return;
        }

        // Read the site map from the provided file
        char[][] siteMapChars = readSiteMapFromFile(args[0]);
        if (siteMapChars == null) {
            System.out.println("Error reading site map. Exiting.");
            return;
        }

        SiteMap siteMap = new SiteMap(siteMapChars);
        Bulldozer bulldozer = new Bulldozer();
        BulldozerService bulldozerService = new BulldozerService(bulldozer, siteMap);
        Scanner scanner = new Scanner(System.in);
        List<String> userCommands = new ArrayList<>();
        System.out.println("Welcome to the Aconex site clearing simulator. This is a map of the site:");
        siteMap.displayMap();

        while (bulldozerService.isActive()) {
            System.out.print("(l)eft, (r)ight, (a)dvance <n>, (q)uit: ");
            String input = scanner.nextLine();
            char commandChar = input.charAt(0);

            Command command = null;

            switch (commandChar) {
                case 'a':
                    Pattern pattern = Pattern.compile("^a \\d+$");
                    Matcher matcher = pattern.matcher(input);
                    if (matcher.matches()) {
                        int steps = Integer.parseInt(input.substring(2).trim());
                        command = new AdvanceCommand(bulldozerService, steps);
                        userCommands.add("advance " + steps);
                    } else {
                        System.out.println("Invalid input format. Expected format: 'a' followed by a space and a number.");
                        System.exit(0);
                    }

                    break;
                case 'l':
                    command = new LeftCommand(bulldozerService);
                    userCommands.add("turn left");  // Track the left turn
                    break;
                case 'r':
                    command = new RightCommand(bulldozerService);
                    userCommands.add("turn right");  // Track the right turn
                    break;
                case 'q':
                    command = new QuitCommand(bulldozerService);
                    userCommands.add("quit");  // Track the quit command
                    break;
                default:
                    System.out.println("Invalid command. Please try again.");
            }

            if (command != null) {
                command.execute();
            }
        }

        // Display the commands executed by the user
        System.out.println("\nThe simulation has ended at your request. These are the commands you issued:");
        System.out.println(String.join(", ", userCommands));
        bulldozerService.printReport();
        System.out.println("\nThank you for using the Aconex site clearing simulator.");

        scanner.close();
    }

    private static char[][] readSiteMapFromFile(String fileName) {
        try {
            // Read all lines from the file
            List<String> lines = Files.readAllLines(Path.of(fileName), StandardCharsets.UTF_8);

            // Determine the number of rows and columns
            int rows = lines.size();
            if (rows == 0) {
                throw new IllegalArgumentException("Site map file is empty.");
            }
            int cols = lines.get(0).trim().length();

            // Initialize the site map array
            char[][] siteMap = new char[rows][cols];

            // Populate the site map array
            for (int i = 0; i < rows; i++) {
                String line = lines.get(i).trim();
                // Check for consistent line lengths
                if (line.length() != cols) {
                    throw new IllegalArgumentException("Inconsistent line lengths in site map file at line " + (i + 1));
                }
                siteMap[i] = line.toCharArray();
            }

            return siteMap;

        } catch (IOException e) {
            System.out.println("Error reading file: " + fileName);
            return null;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid site map file format: " + e.getMessage());
            return null;
        }
    }
}
