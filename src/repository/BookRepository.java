package repository;

import model.Book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BookRepository {
    private List<Book> bookList;

    public BookRepository() {
        this.bookList = new ArrayList<>();
    }

    public void addBook(Book book) {
        this.bookList.add(book);
    }

    public List<Book> getAllBooks() {
        return Collections.unmodifiableList(bookList);
    }

    public Optional<Book> getBookByIsbn(String isbn) {
        return bookList.stream().filter(book -> book.getIsbn().equals(isbn)).findFirst();
    }
    public void clearAll() {
        this.bookList.clear();
    }
}
