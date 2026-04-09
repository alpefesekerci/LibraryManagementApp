package repository;

import model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private List<Book> bookList;

    public BookRepository() {
        this.bookList = new ArrayList<>();
    }

    public void addBook(Book book) {
        this.bookList.add(book);
    }

    public List<Book> getAllBooks() {
        return bookList;
    }

    public Book getBookByIsbn(String isbn) {
        for (Book book : bookList) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }
}
