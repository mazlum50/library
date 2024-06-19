package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;
    private final String inventoryFilePath;
    public Library(String inventoryFilePath) {
        this.books = new ArrayList<>();
        this.inventoryFilePath = inventoryFilePath;
        loadInventory();
    }
    public void addBook(Book book) {
        books.add(book);
        saveInventory();
    }

    public void removeBook(String isbn) {
        books.removeIf(book -> book.getIsbn().equals(isbn));
        saveInventory(); // Save inventory after removing a book
    }
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
                    saveInventory(); // Save inventory after borrowing a book
                } else {
                    System.out.println("Book with ISBN " + isbn + " is not available for borrowing.");
                }
                return;
            }
        }
        System.out.println("Book with ID " + isbn + " not found.");
    }

    public void returnBook(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                if (!book.isAvailable()) {
                    book.toggleAvailability();
                    saveInventory(); // Save inventory after returning a book
                } else {
                    System.out.println("Book with ISBN " + isbn + " is already available.");
                }
                return;
            }
        }
        System.out.println("Book with ISBN " + isbn + " not found.");
    }

    private void loadInventory() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(inventoryFilePath))) {
            books = (List<Book>) ois.readObject();
            System.out.println("Inventory loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("No inventory file found. Starting with an empty inventory.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }
    }

    // Method to save inventory to file
    private void saveInventory() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(inventoryFilePath))) {
            oos.writeObject(books);
            System.out.println("Inventory saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }
}
