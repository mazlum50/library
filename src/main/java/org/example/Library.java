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
                    System.out.println("Buch mit der ISBN " + isbn + " ist nicht zum Ausleihen verfügbar.");
                }
                return;
            }
        }
        System.out.println( "Buch mit der ID " + isbn + " nicht gefunden.");
    }
    public void returnBook(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                if (!book.isAvailable()) {
                    book.toggleAvailability();
                    saveInventory(); // Save inventory after returning a book
                } else {
                    System.out.println("Buch mit der ISBN " + isbn + " ist bereits verfügbar.");
                }
                return;
            }
        }
        System.out.println("Buch mit der ISBN " + isbn + " nicht gefunden.");
    }
    private void loadInventory() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(inventoryFilePath))) {
            books = (List<Book>) ois.readObject();
            System.out.println("Data erfolgreich geladen.");
        } catch (FileNotFoundException e) {
            System.out.println( "Keine Data gefunden. Beginne mit einem leeren Data.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Fehler bei Data  Laden  " + e.getMessage());
        }
    }
    private void saveInventory() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(inventoryFilePath))) {
            oos.writeObject(books);
            System.out.println("Data erfolgreich gespeichert.");
        } catch (IOException e) {
            System.out.println("Fehler bei Data  Speichern : " + e.getMessage());
        }
    }
}
