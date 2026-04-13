package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Member extends Person {
    private List<Book> borrowedBooks;

    public Member(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
        this.borrowedBooks = new ArrayList<>();
    }

    public List<Book> getBorrowedBooks() {
        return Collections.unmodifiableList(borrowedBooks);
    }

    public void addBorrowedBook(Book book) {
        borrowedBooks.add(book);
    }

    public void removeBorrowedBook(Book book) {
        borrowedBooks.remove(book);
    }

    @Override
    public String toString() {
        return "Member{" +
                ", baseInfo=" + super.toString() +
                ", borrowedBooksCount=" + borrowedBooks.size() +
                '}';
    }
}
