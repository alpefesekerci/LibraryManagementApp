package service;

import dto.ServiceResult;
import model.Book;
import model.Member;
import repository.BookRepository;
import repository.MemberRepository;
import util.FileUtil;

import java.util.List;
import java.util.stream.Collectors;

public class LibraryManager {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public LibraryManager(BookRepository bookRepository, MemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    public ServiceResult<Void> addBook(Book book) {
        if (bookRepository.getBookByIsbn(book.getIsbn()).isPresent()) {
            return new ServiceResult<>(false, "Book with ISBN " + book.getIsbn() + " already exists.");
        }

        bookRepository.addBook(book);
        return new ServiceResult<>(true, "Book " + book.getTitle() + " added successfully.");
    }

    public ServiceResult<Void> registerMember(Member member) {
        if (memberRepository.getMemberById(member.getId()).isPresent()) {
            return new ServiceResult<>(false, "Member with ID " + member.getId() + " already exists.");
        }

        memberRepository.addMember(member);
        return new ServiceResult<>(true, "Member " + member.getFirstName() + " " + member.getLastName() + " registered successfully.");
    }

    private ServiceResult<Void> validateBookAndMember(String isbn, int memberId) {
        if (bookRepository.getBookByIsbn(isbn).isEmpty()) {
            return new ServiceResult<>(false, "Book with ISBN " + isbn + " not found.");
        }
        if (memberRepository.getMemberById(memberId).isEmpty()) {
            return new ServiceResult<>(false, "Member with ID " + memberId + " not found.");
        }
        return new ServiceResult<>(true, null);
    }

    public ServiceResult<Void> lendBook(String isbn, int memberId) {
        ServiceResult<Void> validationResult = validateBookAndMember(isbn, memberId);
        if (!validationResult.isSuccess()) {
            return validationResult;
        }

        Book book = bookRepository.getBookByIsbn(isbn).orElseThrow();
        Member member = memberRepository.getMemberById(memberId).orElseThrow();

        if (!book.isAvailable()) {
            return new ServiceResult<>(false, "Book with ISBN " + isbn + " is not available.");
        }

        book.setAvailable(false);
        member.addBorrowedBook(book);
        return new ServiceResult<>(true, "Book with ISBN " + isbn + " lent to member " + member.getFirstName() + " " + member.getLastName() + ".");
    }

    public ServiceResult<Void> returnBook(String isbn, int memberId) {
        ServiceResult<Void> validationResult = validateBookAndMember(isbn, memberId);
        if (!validationResult.isSuccess()) {
            return validationResult;
        }

        Book book = bookRepository.getBookByIsbn(isbn).orElseThrow();
        Member member = memberRepository.getMemberById(memberId).orElseThrow();

        if (member.getBorrowedBooks().contains(book)) {
            book.setAvailable(true);
            member.removeBorrowedBook(book);
            return new ServiceResult<>(true, "Book with ISBN " + isbn + " successfully returned by " + member.getFirstName() + ".");
        }

        return new ServiceResult<>(false, "Member with ID " + memberId + " does not have this book.");
    }

    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    public List<Member> getAllMembers() {
        return memberRepository.getAllMembers();
    }

    public List<Book> getAvailableBooks() {
        return bookRepository.getAllBooks().stream().filter(Book::isAvailable).toList();
    }

    public List<Book> searchBooks(String keyword) {
        java.util.Locale trLocale = new java.util.Locale("tr", "TR");
        String lowerCaseKeyword = keyword.toLowerCase();
        return bookRepository.getAllBooks().stream()
                .filter(book -> book.getTitle().toLowerCase(trLocale).contains(lowerCaseKeyword) ||
                        book.getAuthor().toLowerCase(trLocale).contains(lowerCaseKeyword))
                .collect(Collectors.toList());
    }

    public void loadInitialData() {
        List<Book> loadedBooks = util.FileUtil.readBooksFromFile();
        for (Book b : loadedBooks) bookRepository.addBook(b);

        List<Member> loadedMembers = FileUtil.readMembersFromFile(this.bookRepository);
        for (Member m : loadedMembers) memberRepository.addMember(m);

        System.out.println("⚙️ System loaded from database files.");
    }

    public void saveSystemData() {
        util.FileUtil.writeBooksToFile(bookRepository.getAllBooks());
        util.FileUtil.writeMembersToFile(memberRepository.getAllMembers());
        System.out.println("💾 System data successfully saved to TXT files.");
    }

    public void clearSystemData() {
        bookRepository.clearAll();
        memberRepository.clearAll();
        saveSystemData();
    }
}