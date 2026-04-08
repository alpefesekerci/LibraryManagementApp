package model;

import java.util.ArrayList;
import java.util.List;

public class Member extends Person {
    private List<Book> borrowedBooks;

    public Member(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
        this.borrowedBooks = new ArrayList<>();
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    @Override
    public String toString() {
        return "Member{" +
                ", baseInfo=" + super.toString() +
                ", borrowedBooksCount=" + borrowedBooks.size() +
                '}';
    }
}
