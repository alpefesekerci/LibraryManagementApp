package ui;

import dto.ServiceResult;
import model.Book;
import model.Member;
import service.LibraryManager;

import java.util.List;
import java.util.ArrayList;

public class ConsoleMenu {
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
            System.out.println("║  [8] Search a Book                     ║");
            System.out.println("║                                        ║");
            System.out.println("║  [0] Exit System                       ║");
            System.out.println("╚════════════════════════════════════════╝");

            int choice = inputHelper.readInt("Please enter your choice", 0, 8);

            switch (choice) {
                case 1:
                    System.out.println("\n--- ADD NEW BOOK ---");

                    String isbn = inputHelper.readString("Enter Book ISBN");
                    String title = inputHelper.readString("Enter Book title");
                    String author = inputHelper.readString("Enter Author Name");
                    int pageCount = inputHelper.readPositiveInt("Enter Page Count");

                    Book book = new Book(isbn, title, author, pageCount);
                    ServiceResult result = libraryManager.addBook(book);

                    printResult(result);
                    inputHelper.pressEnterToContinue();
                    break;

                case 2:
                    System.out.println("\n--- REGISTER NEW MEMBER ---");

                    int id = inputHelper.readInt("Enter Member ID");
                    String firstName = inputHelper.readString("Enter First Name");
                    String lastName = inputHelper.readString("Enter Last Name");

                    Member member = new Member(id, firstName, lastName);
                    ServiceResult memberResult = libraryManager.registerMember(member);

                    printResult(memberResult);
                    inputHelper.pressEnterToContinue();
                    break;

                case 3:
                    System.out.println("\n--- LENDING A BOOK ---");

                    int lendMemberId = inputHelper.readPositiveInt("Enter Member ID");

                    List<Book> availableBooks = new ArrayList<>();
                    for (Book b : libraryManager.getAllBooks()) {
                        if (b.isAvailable()) {
                            availableBooks.add(b);
                        }
                    }

                    if (availableBooks.isEmpty()) {
                        System.out.println("❌ No books available to lend right now.");
                    } else {
                        System.out.println("\n[Available Books in Library]:");
                        printBookTable(availableBooks);

                        String lendIsbn = inputHelper.readString("Enter Book ISBN to lend");
                        ServiceResult lendBookResult = libraryManager.lendBook(lendIsbn, lendMemberId);
                        printResult(lendBookResult);
                    }
                    inputHelper.pressEnterToContinue();
                    break;

                case 4:
                    System.out.println("\n--- RETURNING A BOOK ---");

                    int returnMemberId = inputHelper.readPositiveInt("Enter Member ID");

                    Member returnMember = null;
                    for (Member m : libraryManager.getAllMembers()) {
                        if (m.getId() == returnMemberId) {
                            returnMember = m;
                            break;
                        }
                    }

                    if (returnMember == null) {
                        System.out.println("❌ Member with ID " + returnMemberId + " not found.");
                    } else if (returnMember.getBorrowedBooks().isEmpty()) {
                        System.out.println("❌ This member has no borrowed books.");
                    } else {
                        System.out.println("\n[Books currently borrowed by " + returnMember.getFirstName() + "]:");
                        printBookTable(returnMember.getBorrowedBooks());

                        String returnIsbn = inputHelper.readString("Enter Book ISBN to return");
                        ServiceResult returnBookResult = libraryManager.returnBook(returnIsbn, returnMemberId);
                        printResult(returnBookResult);
                    }
                    inputHelper.pressEnterToContinue();
                    break;

                case 5:
                    System.out.println("\n--- REGISTERED BOOKS ---");

                    List<Book> books = libraryManager.getAllBooks();
                    if (books.isEmpty()) {
                        System.out.println("No books registered.");
                    } else {
                        printBookTable(books);
                    }
                    inputHelper.pressEnterToContinue();
                    break;

                case 6:
                    System.out.println("\n--- REGISTERED MEMBERS ---");

                    List<Member> members = libraryManager.getAllMembers();
                    if (members.isEmpty()) {
                        System.out.println("No members registered.");
                    } else {
                        printMemberTable(members);
                    }
                    inputHelper.pressEnterToContinue();
                    break;

                case 7:
                    System.out.println("\n--- SAVE SYSTEM DATA ---");

                    libraryManager.saveSystemData();
                    inputHelper.pressEnterToContinue();
                    break;

                case 8:
                    System.out.println("\n--- SEARCH A BOOK ---");

                    String keyword = inputHelper.readString("Enter Title or Author to search").toLowerCase();
                    List<Book> foundBooks = new ArrayList<>();

                    for (Book b : libraryManager.getAllBooks()) {
                        if (b.getTitle().toLowerCase().contains(keyword) || b.getAuthor().toLowerCase().contains(keyword)) {
                            foundBooks.add(b);
                        }
                    }

                    if (foundBooks.isEmpty()) {
                        System.out.println("❌ No books found matching: " + keyword);
                    } else {
                        System.out.println("\n[Search Results]:");
                        printBookTable(foundBooks);
                    }
                    inputHelper.pressEnterToContinue();
                    break;

                case 0:
                    System.out.println("Saving data before exiting...");

                    libraryManager.saveSystemData();
                    System.out.println("Exiting the system. Goodbye!");
                    keepRunning = false;
                    break;

                default:
                    throw new IllegalStateException("Sistem hatası: Beklenmeyen menü seçimi (" + choice + "). Lütfen sistem yöneticisine başvurun.");
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

    private void printBookTable(List<Book> books) {
        System.out.println("----------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-25s | %-20s | %-6s | %-9s |%n", "ISBN", "TITLE", "AUTHOR", "PAGES", "AVAILABLE");
        System.out.println("----------------------------------------------------------------------------------");
        for (Book book : books) {
            System.out.printf("| %-10s | %-25s | %-20s | %-6d | %-9s |%n",
                    book.getIsbn(), truncate(book.getTitle(), 25), truncate(book.getAuthor(), 20), book.getPageCount(), book.isAvailable());
        }
        System.out.println("----------------------------------------------------------------------------------");
    }

    private void printMemberTable(List<Member> members) {
        System.out.println("----------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-25s | %-20s | %-6s |%n", "ID", "FIRST NAME", "LAST NAME", "BORROWED BOOKS");
        System.out.println("----------------------------------------------------------------------------------");
        for (Member member : members) {
            System.out.printf("| %-10d | %-25s | %-20s | %-6d |%n",
                    member.getId(), truncate(member.getFirstName(), 25), truncate(member.getLastName(), 20), member.getBorrowedBooks().size());
        }
        System.out.println("----------------------------------------------------------------------------------");
    }

    private String truncate(String text, int maxLength) {
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }
}