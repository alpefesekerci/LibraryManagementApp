package ui;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleInputHelper {

    private final Scanner scanner;

    public ConsoleInputHelper() {
        this.scanner = new Scanner(System.in);
    }

    public String getString(String prompt) {
        System.out.print("➤ " + prompt + ": ");
        return scanner.nextLine().trim();
    }

    public int getInt(String prompt) {
        while (true) {
            try {
                System.out.println("➤ " + prompt + ": ");
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
            }
        }
    }
}
