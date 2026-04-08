package util;

import model.Book;
import model.Member;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    private static final String BOOKS_FILE = "library.txt";
    private static final String MEMBERS_FILE = "members.txt";

    public static List<Book> readBooksFromFile() {
        List<Book> books = new ArrayList<>();
        File file = new File(BOOKS_FILE);

        if (!file.exists()) {
            return books;
        }

            try (BufferedReader br = new BufferedReader(new FileReader(file))){
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length == 5) {
                        String isbn = data[0];
                        String title = data[1];
                        String author = data[2];
                        int pageCount = Integer.parseInt(data[3]);
                        boolean isAvailable = Boolean.parseBoolean(data[4]);

                        Book book = new Book(isbn, title, author, pageCount);
                        book.setAvailable(isAvailable);
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
                    bw.write(book.getIsbn() + "," + book.getTitle() + "," + book.getAuthor() + "," +
                            book.getPageCount() + "," + book.isAvailable());
                    bw.newLine();
                }
            } catch (IOException e) {
                System.err.println("❌ Error writing to books file: " + e.getMessage());
            }
        }

        public static List<Member> readMembersFromFile() {
            List<Member> members = new ArrayList<>();
            File file = new File(MEMBERS_FILE);

            if (!file.exists()) {
                return members;
            }

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    // id, firstName, lastName, phone, email, membershipNumber
                    if (data.length >= 6) {
                        int id = Integer.parseInt(data[0]);
                        String firstName = data[1];
                        String lastName = data[2];
                        String phone = data[3];
                        String email = data[4];
                        String membershipNumber = data[5];

                        members.add(new Member(id, firstName, lastName, phone, email, membershipNumber));
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
                    bw.write(member.getId() + "," + member.getFirstName() + "," + member.getLastName() + "," +
                            member.getPhone() + "," + member.getEmail() + "," + member.getMembershipNumber());
                    bw.newLine();
                }
            } catch (IOException e) {
                System.err.println("❌ Error writing to members file: " + e.getMessage());
            }
        }
    }

