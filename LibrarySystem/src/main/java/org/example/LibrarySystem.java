package org.example;
import java.util.ArrayList;

class Book {
    String title;
    String author;
    boolean available;

    Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.available = true;
    }
}

public class LibrarySystem {
    private ArrayList<Book> books;

    public LibrarySystem() {
        books = new ArrayList<>();
        books.add(new Book("Game of Thrones", "George R.Martin"));
        books.add(new Book("Hobbit", "J.R Tolkien"));
        books.add(new Book("Charlie and the Chocolate Factory", "Roald Dahl"));
    }

    public void showAvailableBooks() {
        System.out.println("Available Books:");
        for (Book b : books) {
            if (b.available) {
                System.out.println(b.title + " by " + b.author);
            }
        }
    }

    public void issueBook(String title) {
        for (Book b : books) {
            if (b.title.equalsIgnoreCase(title) && b.available) {
                b.available = false;
                System.out.println("Issued: " + b.title);
                return;
            }
        }
        System.out.println("Book not available.");
    }

    public void returnBook(String title) {
        for (Book b : books) {
            if (b.title.equalsIgnoreCase(title) && !b.available) {
                b.available = true;
                System.out.println("Returned: " + b.title);
                return;
            }
        }
        System.out.println("Book not found in borrowed list.");
    }

    public void showAllBooks() {
        System.out.println("All Books in Library:");
        for (Book b : books) {
            String status = b.available ? "Available" : "Borrowed";
            System.out.println(b.title + " by " + b.author + " - " + status);
        }
    }

    public static void main(String[] args) {
        LibrarySystem library = new LibrarySystem();
        library.showAvailableBooks();
        library.issueBook("Hobbit");
        library.showAllBooks();
        library.returnBook("Hobbit");
        library.showAvailableBooks();
    }
}