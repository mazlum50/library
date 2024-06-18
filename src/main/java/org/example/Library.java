package org.example;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private final List<Book> books;

    public Library() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added successfully: " + book.getTitle());
    }

    public void removeBook(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                books.remove(book);
                System.out.println("Book removed successfully: " + book.getTitle());
                return;
            }
        }
        System.out.println("Book with ISBN " + isbn + " not found.");
    }

    // Method to find books by title
    public List<Book> findBooksByTitle(String title) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    public List<Book> findBooksByAuthor(String author) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public List<Book> getAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.isAvailable()) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    public void borrowBook(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                if (book.isAvailable()) {
                    book.toggleAvailability();
                    System.out.println("Book borrowed successfully: " + book.getTitle());
                } else {
                    System.out.println("Book with ISBN " + isbn + " is not available for borrowing.");
                }
                return;
            }
        }
        System.out.println("Book with ISBN " + isbn + " not found.");
    }

    public void returnBook(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                if (!book.isAvailable()) {
                    book.toggleAvailability();
                    System.out.println("Book returned successfully: " + book.getTitle());
                } else {
                    System.out.println("Book with ISBN " + isbn + " is already available.");
                }
                return;
            }
        }
        System.out.println("Book with ISBN " + isbn + " not found.");
    }
}
