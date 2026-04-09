package ui;

import java.util.Scanner;

public class ConsoleInputHelper {

    private final Scanner scanner;

    public ConsoleInputHelper() {
        this.scanner = new Scanner(System.in);
    }

    public int readInt(String prompt) {
        while (true) {
            System.out.println("➤ " + prompt + ": ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("❌ Input cannot be empty. Please enter a number.");
                continue;
            }

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Please enter a whole number.");
            }
        }
    }

    public int readInt(String prompt, int min, int max) {
        while (true) {
            int value = readInt(prompt);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("❌ Invalid input! Please enter a number between " + min + " and " + max + ".");
        }
    }

    public int readPositiveInt(String prompt) {
        return readInt(prompt, 1, Integer.MAX_VALUE);
    }

    public double readDouble(String prompt) {
        while (true) {
            System.out.println("➤ " + prompt + ": ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("❌ Input cannot be empty. Please enter a value.");
                continue;
            }

            input = input.replace(",", ".");

            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Please enter numbers only (you can use . or , for decimals).");
            }
        }
    }

    public String readString(String prompt) {
        while (true) {
            System.out.println("➤ " + prompt + ": ");
            String value = scanner.nextLine().trim();
            if (value.isEmpty()) {
                System.out.println("❌ Input cannot be empty. Please enter a valid text.");
            } else {
                return value;
            }
        }
    }

    public String readName(String prompt) {
        while (true) {
            System.out.println("➤ " + prompt + ": ");
            String value = scanner.nextLine().trim();
            if (value.isEmpty()) {
                System.out.println("❌ Input cannot be empty. Please enter a valid text.");
            } else if (!value.matches("^[a-zA-ZçÇğĞıİöÖşŞüÜ\\s'-]+$")) {
                System.out.println("❌ Invalid input! Names can only contain letters, spaces, hyphens (-), and apostrophes (').");
            } else {
                return value;
            }
        }
    }

    public String readIsbn(String prompt) {
        while (true) {
            System.out.println("➤ " + prompt + ": ");
            String value = scanner.nextLine().trim();

            if (value.isEmpty()) {
                System.out.println("❌ Input cannot be empty. Please enter a valid ISBN.");
            } else if (!value.matches("^[0-9Xx-]+$")) {
                System.out.println("❌ Invalid input! ISBN can only contain numbers, hyphens (-), and the letter X.");
            } else {
                return value;
            }
        }
    }

    public void pressEnterToContinue() {
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
}
