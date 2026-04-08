package ui;

import dto.ServiceResult;
import model.Book;
import model.Member;
import service.LibraryManager;

import java.util.InputMismatchException;
import java.util.List;
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

        libraryManager.loadInitialData();

        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║       LIBRARY MANAGEMENT SYSTEM        ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║                                        ║");
            System.out.println("║  [1] Add New Book                      ║");
            System.out.println("║  [2] Register New Member               ║");
            System.out.println("║  [3] Lend a Book                       ║");
            System.out.println("║  [4] Return a Book                     ║");
            System.out.println("║  [5] List All Books                    ║");
            System.out.println("║  [6] List All Members                  ║");
            System.out.println("║  [7] Save System Data                  ║");
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

                        String lendIsbn = inputHelper.getString("Enter Book ISBN");
                        String lendMemberId = inputHelper.getString("Enter Membership Number");

                        ServiceResult lendResult = libraryManager.lendBook(lendIsbn, lendMemberId);

                        if (lendResult.isSuccess()) {
                            System.out.println("✅ " + lendResult.getMessage());
                        } else {
                            System.out.println("❌ " + lendResult.getMessage());
                        }
                        break;

                    case 4:
                        System.out.println("\n--- RETURNING A BOOK ---");

                        String returnIsbn = inputHelper.getString("Enter Book ISBN");
                        String returnMemberId = inputHelper.getString("Enter Membership Number");

                        ServiceResult returnResult = libraryManager.returnBook(returnIsbn, returnMemberId);

                        if (returnResult.isSuccess()) {
                            System.out.println("✅ " + returnResult.getMessage());
                        } else {
                            System.out.println("❌ " + returnResult.getMessage());
                        }
                        break;

                    case 5:
                        System.out.println("\n--- REGISTERED BOOKS ---");

                        List<Book> books = libraryManager.getAllBooks();
                        if (books.isEmpty()) {
                            System.out.println("No books registered.");
                        } else {
                            for (Book b : books) {
                                System.out.println("ISBN: " + b.getIsbn() + " | Title: " + b.getTitle() +
                                        " | Author: " + b.getAuthor() + " | Available: " + b.isAvailable());
                            }
                        }
                        break;

                    case 6:
                        System.out.println("\n--- REGISTERED MEMBERS ---");

                        List<Member> members = libraryManager.getAllMembers();
                        if (members.isEmpty()) {
                            System.out.println("No members registered.");
                        } else {
                            for (Member m : members) {
                                System.out.println("ID: " + m.getId() + " | Membership Number: " + m.getMembershipNumber() +
                                        " | Name: " + m.getFirstName() + " " + m.getLastName());
                            }
                        }
                        break;

                    case 7:
                        System.out.println("\n--- SAVE SYSTEM DATA ---");

                        libraryManager.saveSystemData();
                        break;

                    case 0:
                        System.out.println("Saving data before exiting...");

                        libraryManager.saveSystemData();
                        System.out.println("Exiting the system. Goodbye!");
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
