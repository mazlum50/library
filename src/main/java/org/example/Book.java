package org.example;

import java.io.Serial;
import java.io.Serializable;

public class Book   implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    private final String title;
    private final String author;
    private final String isbn;
    private boolean available;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.available = true;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getIsbn() {
        return isbn;
    }
    public boolean isAvailable() {
        return available;
    }
    public void toggleAvailability() {
        available = !available;
    }
}
