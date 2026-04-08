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

                        printResult(result);

                        /*if (result.isSuccess()) {
                            System.out.println("✅ " + result.getMessage());
                        } else {
                            System.out.println("❌ " + result.getMessage());
                        }*/
                        break;

                    case 2:
                        System.out.println("\n--- REGISTER NEW MEMBER ---");

                        int id = inputHelper.getInt("Enter Member ID");
                        String firstName = inputHelper.getString("Enter First Name");
                        String lastName = inputHelper.getString("Enter Last Name");

                        Member member = new Member(id, firstName, lastName);
                        ServiceResult memberResult = libraryManager.registerMember(member);

                        printResult(memberResult);

                        /*if (memberResult.isSuccess()) {
                            System.out.println("✅ " + memberResult.getMessage());
                        } else {
                            System.out.println("❌ " + memberResult.getMessage());
                        }*/
                        break;

                    case 3:
                        System.out.println("\n--- LENDING A BOOK ---");

                        String lendIsbn = inputHelper.getString("Enter Book ISBN");
                        int lendMemberId = inputHelper.getInt("Enter Member ID");

                        ServiceResult lendResult = libraryManager.lendBook(lendIsbn, lendMemberId);

                        printResult(lendResult);

                        /*if (lendResult.isSuccess()) {
                            System.out.println("✅ " + lendResult.getMessage());
                        } else {
                            System.out.println("❌ " + lendResult.getMessage());
                        }*/
                        break;

                    case 4:
                        System.out.println("\n--- RETURNING A BOOK ---");

                        String returnIsbn = inputHelper.getString("Enter Book ISBN");
                        int returnMemberId = inputHelper.getInt("Enter Member ID");

                        ServiceResult returnResult = libraryManager.returnBook(returnIsbn, returnMemberId);

                        printResult(returnResult);

                        /*if (returnResult.isSuccess()) {
                            System.out.println("✅ " + returnResult.getMessage());
                        } else {
                            System.out.println("❌ " + returnResult.getMessage());
                        }*/
                        break;

                    case 5:
                        System.out.println("\n--- REGISTERED BOOKS ---");

                        List<Book> books = libraryManager.getAllBooks();
                        if (books.isEmpty()) {
                            System.out.println("No books registered.");
                        } else {
                            System.out.println("----------------------------------------------------------------------------------");
                            System.out.printf("| %-10s | %-25s | %-20s | %-6s | %-9s |%n", "ISBN", "TITLE", "AUTHOR", "PAGES", "AVAILABLE");
                            System.out.println("----------------------------------------------------------------------------------");
                            for (Book b : books) {
                                System.out.printf("| %-10s | %-25s | %-20s | %-6d | %-9s |%n",
                                        b.getIsbn(),
                                        truncate(b.getTitle(), 25),
                                        truncate(b.getAuthor(), 20),
                                        b.getPageCount(),
                                        (b.isAvailable() ? "Yes" : "No"));
                            }
                            System.out.println("----------------------------------------------------------------------------------");
                        }
                        break;

                    case 6:
                        System.out.println("\n--- REGISTERED MEMBERS ---");

                        List<Member> members = libraryManager.getAllMembers();
                        if (members.isEmpty()) {
                            System.out.println("No members registered.");
                        } else {
                            System.out.println("-------------------------------------------------------------");
                            System.out.printf("| %-5s | %-15s | %-15s | %-10s |%n", "ID", "FIRST NAME", "LAST NAME", "BOOKS HELD");
                            System.out.println("-------------------------------------------------------------");
                            for (Member m : members) {
                                System.out.printf("| %-5d | %-15s | %-15s | %-10d |%n",
                                        m.getId(),
                                        truncate(m.getFirstName(), 15),
                                        truncate(m.getLastName(), 15),
                                        m.getBorrowedBooks().size());
                            }
                            System.out.println("-------------------------------------------------------------");
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
                        break;

                    default:
                        System.out.println("❌ Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }
    private void printResult(ServiceResult result) {
        if (result.isSuccess()) {
            System.out.println("✅ " + result.getMessage());
        } else {
            System.out.println("❌ " + result.getMessage());
        }
    }
    private String truncate(String text, int maxLength) {
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }
}
