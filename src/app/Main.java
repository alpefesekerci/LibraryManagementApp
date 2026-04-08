package app;

import repository.BookRepository;
import repository.MemberRepository;
import service.LibraryManager;
import ui.ConsoleMenu;

public class Main {
    public static void main(String[] args) {
        BookRepository bookRepo = new BookRepository();
        MemberRepository memberRepo = new MemberRepository();

        LibraryManager manager = new LibraryManager(bookRepo, memberRepo);

        ConsoleMenu menu = new ConsoleMenu(manager);
        menu.startMenu();
    }
}
