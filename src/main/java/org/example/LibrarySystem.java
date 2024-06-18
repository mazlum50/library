package org.example;
import java.util.List;
import java.util.Scanner;

public class LibrarySystem {
    private final Library library;
    private final Scanner scanner;
    private boolean isLibrarian;

    public LibrarySystem() {
        this.library = new Library();
        this.scanner = new Scanner(System.in);
        this.isLibrarian = false;
    }


    public void displayMenu() {
        System.out.println("\n===== Library Management System =====");
        if (isLibrarian) {
            System.out.println("1. Add a new book");
            System.out.println("2. Remove a book");
            System.out.println("9. Switch to Library User");
        } else {
            System.out.println("1. Search for books by title");
            System.out.println("2. Search for books by author");
            System.out.println("3. Display all books");
            System.out.println("4. Display available books");
            System.out.println("5. Borrow a book");
            System.out.println("6. Return a book");
            System.out.println("9. Switch to Librarian");
        }
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    public void handleUserInput() {
        int choice;
        do {
            displayMenu();
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    if (isLibrarian) {
                        addBook();
                    } else {
                        searchByTitle();
                    }
                    break;
                case 2:
                    if (isLibrarian) {
                        removeBook();
                    } else {
                        searchByAuthor();
                    }
                    break;
                case 3:
                    if (!isLibrarian) {
                        displayAllBooks();
                    }
                    break;
                case 4:
                    if (!isLibrarian) {
                        displayAvailableBooks();
                    }
                    break;
                case 5:
                    if (!isLibrarian) {
                        borrowBook();
                    }
                    break;
                case 6:
                    if (!isLibrarian) {
                        returnBook();
                    }
                    break;
                case 9:
                    switchUserRole();
                    break;
                case 0:
                    System.out.println("Exiting Library System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 0);
    }

    private void switchUserRole() {
        isLibrarian = !isLibrarian;
        if (isLibrarian) {
            System.out.println("Switched to Librarian role.");
        } else {
            System.out.println("Switched to Library User role.");
        }
    }

    private void addBook() {
        System.out.print("Enter title of the book: ");
        String title = scanner.nextLine();
        System.out.print("Enter author of the book: ");
        String author = scanner.nextLine();
        System.out.print("Enter unique ID of the book: ");
        String uniqueId = scanner.nextLine();

        Book newBook = new Book(title, author, uniqueId);
        library.addBook(newBook);
    }

    // Method to remove a book
    private void removeBook() {
        System.out.print("Enter ISBN of the book to remove: ");
        String uniqueId = scanner.nextLine();
        library.removeBook(uniqueId);
    }

    // Method to search for books by title
    private void searchByTitle() {
        System.out.print("Enter title to search: ");
        String title = scanner.nextLine();
        List<Book> foundBooks = library.findBooksByTitle(title);
        displayBooks(foundBooks);
    }

    private void searchByAuthor() {
        System.out.print("Enter author to search: ");
        String author = scanner.nextLine();
        List<Book> foundBooks = library.findBooksByAuthor(author);
        displayBooks(foundBooks);
    }

    private void displayAllBooks() {
        List<Book> allBooks = library.getAllBooks();
        displayBooks(allBooks);
    }

    private void displayAvailableBooks() {
        List<Book> availableBooks = library.getAvailableBooks();
        displayBooks(availableBooks);
    }

    private void borrowBook() {
        System.out.print("Enter ISBN of the book to borrow: ");
        String isbn = scanner.nextLine();
        library.borrowBook(isbn);
    }

    private void returnBook() {
        System.out.print("Enter ISBN of the book to return: ");
        String isbn = scanner.nextLine();
        library.returnBook(isbn);
    }


    private void displayBooks(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            System.out.println("===== List of Books =====");
            for (Book book : books) {
                System.out.println("Title: " + book.getTitle());
                System.out.println("Author: " + book.getAuthor());
                System.out.println("ISBN: " + book.getIsbn());
                System.out.println("Available: " + (book.isAvailable() ? "Yes" : "No"));
                System.out.println("---------------------------");
            }
        }
    }
    void authenticateUser() {
        System.out.print("Are you a librarian or a library user? (librarian/user): ");
        String userType = scanner.nextLine();
        isLibrarian = userType.equalsIgnoreCase("librarian");
        System.out.println("Welcome to the Library Management System!");
    }
}
