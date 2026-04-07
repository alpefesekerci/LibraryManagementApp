package model;

import java.util.ArrayList;
import java.util.List;

public class Member extends Person{

    private String membershipNumber;
    private List<Book> borrowedBooks;

    public Member(int id, String firstName, String lastName, String address, String phone, String email, String membershipNumber) {
        super(id, firstName, lastName, phone, email);
        this.membershipNumber = membershipNumber;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getMembershipNumber() {
        return membershipNumber;
    }

    public void setMembershipNumber(String membershipNumber) {
        this.membershipNumber = membershipNumber;
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
                "membershipNumber='" + membershipNumber + '\'' +
                ", baseInfo=" + super.toString() +
                ", borrowedBooks=" + borrowedBooks.size() +
                '}';
    }
}
