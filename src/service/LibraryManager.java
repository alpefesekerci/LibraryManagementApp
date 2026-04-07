package service;

import dto.ServiceResult;
import model.Book;
import model.Member;
import repository.BookRepository;
import repository.MemberRepository;

public class LibraryManager {
    private BookRepository bookRepository;
    private MemberRepository memberRepository;

    public LibraryManager(BookRepository bookRepository, MemberRepository memberRepository) {
        this.bookRepository = new BookRepository();
        this.memberRepository = new MemberRepository();
    }

    public ServiceResult addBook(Book book) {
        if (bookRepository.getBookByIsbn(book.getIsbn()) != null) {
            return new ServiceResult(false, "Book with ISBN " + book.getIsbn() + " already exists.");
        }

        bookRepository.addBook(book);
        return new ServiceResult(true, "Book " + book.getTitle() + " added successfully.");
    }

    public ServiceResult registerMember(Member member) {
        if (memberRepository.getMemberByMembershipNumber(member.getMembershipNumber()) != null) {
            return new ServiceResult(false, "Member with membership number " + member.getMembershipNumber() + " already exists.");
        }

        if (member.getFirstName() == null || member.getFirstName().trim().isEmpty() ||
                member.getLastName() == null || member.getLastName().trim().isEmpty()) {
            return new ServiceResult(false, "First name and last name are required.");
        }

        memberRepository.addMember(member);
        return new ServiceResult(true, "Member " + member.getFirstName() + " " + member.getLastName() + " registered successfully.");
    }

    public ServiceResult lendBook(String isbn, String membershipNumber) {
        Book book = bookRepository.getBookByIsbn(isbn);
        Member member = memberRepository.getMemberByMembershipNumber(membershipNumber);

        if (book == null) {
            return new ServiceResult(false, "Book with ISBN " + isbn + " not found.");
        }
        if (member == null) {
            return new ServiceResult(false, "Member with membership number " + membershipNumber + " not found.");
        }
        if (!book.isAvailable()) {
            return new ServiceResult(false, "Book with ISBN " + isbn + " is not available.");
        }

        book.setAvailable(false);
        member.getBorrowedBooks().add(book);
        return new ServiceResult(true, "Book with ISBN " + isbn + " lent to member " + member.getFirstName() + " " + member.getLastName() + ".");
    }

    public ServiceResult returnBook(String isbn, String membershipNumber) {
        Book book = bookRepository.getBookByIsbn(isbn);
        Member member = memberRepository.getMemberByMembershipNumber(membershipNumber);

        if (book == null) {
            return new ServiceResult(false, "Book with ISBN " + isbn + " not found.");
        }
        if (member == null) {
            return new ServiceResult(false, "Member with membership number " + membershipNumber + " not found.");
        }
        if (!member.getBorrowedBooks().contains(book)) {
            book.setAvailable(true);
            member.getBorrowedBooks().remove(book);
            return new ServiceResult(false, "Member with membership number " + membershipNumber + " does not have this book.");
        }

        return new ServiceResult(true, "Book with ISBN " + isbn + " returned to library.");
    }
}