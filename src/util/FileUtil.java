package util;

import model.Book;
import model.Member;
import repository.BookRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    private static final String BOOKS_FILE = "library.txt";
    private static final String MEMBERS_FILE = "members.txt";

    public static List<Book> readBooksFromFile() {
        List<Book> books = new ArrayList<>();
        File file = new File(BOOKS_FILE);

        if (!file.exists()) return books;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    Book book = new Book(data[0], data[1], data[2], Integer.parseInt(data[3]));
                    book.setAvailable(Boolean.parseBoolean(data[4]));
                    books.add(book);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("❌ Error reading books from file: " + e.getMessage());
        }
        return books;
    }

    public static void writeBooksToFile(List<Book> books) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BOOKS_FILE))) {
            for (Book book : books) {
                bw.write(String.join(",", book.getIsbn(), book.getTitle(), book.getAuthor(),
                        String.valueOf(book.getPageCount()), String.valueOf(book.isAvailable())));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("❌ Error writing to books file: " + e.getMessage());
        }
    }

    public static List<Member> readMembersFromFile(BookRepository bookRepository) {
        List<Member> members = new ArrayList<>();
        File file = new File(MEMBERS_FILE);

        if (!file.exists()) return members;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length >= 3) {
                    Member member = new Member(Integer.parseInt(data[0]), data[1], data[2]);

                    if (data.length == 4 && !data[3].trim().isEmpty()) {
                        String[] isbns = data[3].split(";");
                        for (String isbn : isbns) {
                            bookRepository.getBookByIsbn(isbn).ifPresent(member::addBorrowedBook);
                        }
                    }
                    members.add(member);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("❌ Error reading members file: " + e.getMessage());
        }
        return members;
    }

    public static void writeMembersToFile(List<Member> members) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MEMBERS_FILE))) {
            for (Member member : members) {
                List<String> borrowedIsbns = new ArrayList<>();
                for (Book b : member.getBorrowedBooks()) {
                    borrowedIsbns.add(b.getIsbn());
                }
                String isbnsString = String.join(";", borrowedIsbns);

                bw.write(String.join(",",
                        String.valueOf(member.getId()),
                        member.getFirstName(),
                        member.getLastName(),
                        isbnsString));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("❌ Error writing to members file: " + e.getMessage());
        }
    }
}