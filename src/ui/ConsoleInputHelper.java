package ui;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleInputHelper {

    private final Scanner scanner;

    public ConsoleInputHelper() {
        this.scanner = new Scanner(System.in);
    }

    public String getString(String prompt) {
        while (true) {
            System.out.print("➤ " + prompt + ": ");
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("❌ Input cannot be empty. Please enter a valid text.");
        }
    }

    public int getInt(String prompt) {
        while (true) {
            try {
                System.out.print("➤ " + prompt + ": ");
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input. Please enter a valid integer.");
                scanner.nextLine();
            }
        }
    }
}
