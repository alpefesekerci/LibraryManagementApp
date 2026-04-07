package ui;

import dto.ServiceResult;
import model.Book;
import model.Member;
import service.LibraryManager;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final LibraryManager libraryManager;
    private final ConsoleInputHelper inputHelper;

    public ConsoleMenu(LibraryManager libraryManager) {
        this.libraryManager = libraryManager;
        this.inputHelper = new ConsoleInputHelper();
    }

    public void startMenu() {
        System.out.println("Welcome to the Library Management System!");
        //libraryManager.startApplication();

        boolean keepRunning = true;

        while (keepRunning) {
            for (int i = 1; i <= 3; i++) {
                System.out.println();
            }

            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║       LIBRARY MANAGEMENT SYSTEM        ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║                                        ║");
            System.out.println("║  [1] Add New Book/Member               ║");
            System.out.println("║  [2] Register New Member               ║");
            System.out.println("║  [3] Lend a Book                       ║");
            System.out.println("║  [4] Return a Book                     ║");
            System.out.println("║                                        ║");
            System.out.println("║  [0] Exit System                       ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print("➤ Please enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.println("\n--- ADD NEW BOOK ---");

                        String isbn = inputHelper.getString("Enter Book ISBN");
                        String title = inputHelper.getString("Enter Book title");
                        String author = inputHelper.getString("Enter Author Name");
                        int pageCount = inputHelper.getInt("Enter Page Count");

                        Book book = new Book(isbn, title, author, pageCount);
                        ServiceResult result = libraryManager.addBook(book);

                        if (result.isSuccess()) {
                            System.out.println("✅ " + result.getMessage());
                        } else {
                            System.out.println("❌ " + result.getMessage());
                        }
                        break;
                    case 2:
                        System.out.println("\n--- REGISTER NEW MEMBER ---");

                        int id = inputHelper.getInt("Enter Member ID");
                        String membershipNumber = inputHelper.getString("Enter Membership Number");
                        String firstName = inputHelper.getString("Enter First Name");
                        String lastName = inputHelper.getString("Enter Last Name");
                        String phone = inputHelper.getString("Enter Phone Number");
                        String email = inputHelper.getString("Enter Email Address");

                        Member member = new Member(id, firstName, lastName, phone, email, membershipNumber);
                        ServiceResult memberResult = libraryManager.registerMember(member);

                        if (memberResult.isSuccess()) {
                            System.out.println("✅ " + memberResult.getMessage());
                        } else {
                            System.out.println("❌ " + memberResult.getMessage());
                        }
                        break;
                    case 3:
                        System.out.println("\n--- LENDING A BOOK ---");


                        break;
                    case 4:
                        System.out.println("Returning a book...");
                        break;
                    case 0:
                        keepRunning = false;
                        System.out.println("Exiting the system...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }
}
