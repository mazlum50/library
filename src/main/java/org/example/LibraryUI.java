package org.example;
import java.util.List;
import java.util.Scanner;

public class LibraryUI {
    private final Library library;
    private static final String INVENTORY_FILE_PATH = "data.json";
    private final Scanner scanner;
    private boolean isLibrarian;

    public LibraryUI() {
        this.library = new Library(INVENTORY_FILE_PATH);
        this.scanner = new Scanner(System.in);
        this.isLibrarian = false;
    }
    public void displayMenu() {
        System.out.println("\n===== Bibliotheksverwaltungssystem =====");
        if (isLibrarian) {
            System.out.println("1. Ein neues Buch hinzufügen");
            System.out.println("2. Ein Buch entfernen");
            System.out.println("9. Zum Bibliotheksnutzer wechseln");
        } else {
            System.out.println("1. Nach Büchern nach Titel suchen");
            System.out.println("2. Nach Büchern nach Autor suchen");
            System.out.println("3. Alle Bücher anzeigen");
            System.out.println("4. Verfügbare Bücher anzeigen");
            System.out.println("5. Ein Buch ausleihen");
            System.out.println("6. Ein Buch zurückgeben");
            System.out.println("9. Zum Bibliothekar wechseln");
        }
        System.out.println("0. Beenden");
        System.out.print("Geben Sie Ihre Wahl ein: ");

    }
    public void handleUserInput() {
        int choice;
        do {
            displayMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character after nextInt()

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
                    System.out.println("Bibliothekssystem wird beendet. Auf Wiedersehen!");
                    break;
                default:
                    System.out.println("Ungültige Wahl. Bitte geben Sie eine gültige Option ein.");
            }
        } while (choice != 0);
    }
    private void switchUserRole() {
        isLibrarian = !isLibrarian;
        if (isLibrarian) {
            System.out.println("Zum Bibliothekar gewechselt.");
        } else {
            System.out.println("Zum Bibliotheksnutzer gewechselt.");
        }
    }
    private void addBook() {
        System.out.print("Geben Sie den Titel des Buches ein: ");
        String title = scanner.nextLine();
        System.out.print("Geben Sie den Autor des Buches ein: ");
        String author = scanner.nextLine();
        System.out.print("Geben Sie die ISBN des Buches ein: ");
        String uniqueId = scanner.nextLine();

        Book newBook = new Book(title, author, uniqueId);
        library.addBook(newBook);
    }
    private void removeBook() {
        System.out.print("Geben Sie die ISBN des zu entfernenden Buches ein: ");
        String uniqueId = scanner.nextLine();
        library.removeBook(uniqueId);
    }
    private void searchByTitle() {
        System.out.print("Titel eingeben, um zu suchen: ");
        String title = scanner.nextLine();
        List<Book> foundBooks = library.findBooksByTitle(title);
        displayBooks(foundBooks);
    }
    private void searchByAuthor() {
        System.out.print("Autor eingeben, um zu suchen: ");
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
        System.out.print("ISBN des Buches eingeben, um auszuleihen: ");
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
            System.out.println("===== Liste der Bücher =====");
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
        System.out.print("Sind Sie ein Bibliothekar oder ein Bibliotheksnutzer? (Bibliothekar/Nutzer): ");
        String userType = scanner.nextLine();
        isLibrarian = userType.equalsIgnoreCase("Bibliothekar");
        System.out.println("Willkommen im Bibliotheksverwaltungssystem!");
    }
}
