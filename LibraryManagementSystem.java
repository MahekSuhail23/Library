import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Book class to represent each book in the library
class Book {
    private String title;
    private String author;
    private boolean isAvailable;

    // Constructor to initialize a book
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isAvailable = true;  // Book is available when created
    }

    // Getter methods
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    // Set the book availability
    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", Available: " + (isAvailable ? "Yes" : "No");
    }
}

// User class to represent the library users (borrowers)
class User {
    private String name;
    private List<Book> borrowedBooks;

    public User(String name) {
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    // Borrow a book
    public void borrowBook(Book book) {
        if (book.isAvailable()) {
            book.setAvailable(false);
            borrowedBooks.add(book);
            System.out.println(name + " borrowed: " + book.getTitle());
        } else {
            System.out.println("Sorry, the book is not available.");
        }
    }

    // Return a book
    public void returnBook(Book book) {
        if (borrowedBooks.contains(book)) {
            book.setAvailable(true);
            borrowedBooks.remove(book);
            System.out.println(name + " returned: " + book.getTitle());
        } else {
            System.out.println("You haven't borrowed this book.");
        }
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }
}

// Library class to manage the entire library system
class Library {
    private List<Book> books;
    private List<User> users;

    public Library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
    }

    // Add a new book to the library
    public void addBook(Book book) {
        books.add(book);
    }

    // Register a new user
    public void registerUser(User user) {
        users.add(user);
    }

    // Display all available books
    public void displayBooks() {
        System.out.println("\nAvailable Books:");
        for (Book book : books) {
            if (book.isAvailable()) {
                System.out.println(book);
            }
        }
    }

    // Find a book by title
    public Book findBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    // Display all users
    public void displayUsers() {
        System.out.println("\nRegistered Users:");
        for (User user : users) {
            System.out.println(user.getName());
        }
    }

    // Getter method to access the users list
    public List<User> getUsers() {
        return users;
    }
}

// Main class to run the application
public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        // Adding some books to the library
        library.addBook(new Book("The Catcher in the Rye", "J.D. Salinger"));
        library.addBook(new Book("To Kill a Mockingbird", "Harper Lee"));
        library.addBook(new Book("1984", "George Orwell"));

        // Registering some users
        User user1 = new User("Alice");
        User user2 = new User("Bob");
        library.registerUser(user1);
        library.registerUser(user2);

        boolean running = true;
        while (running) {
            System.out.println("\nLibrary Management System:");
            System.out.println("1. Display Books");
            System.out.println("2. Register User");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Display Users");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume the newline character

            switch (choice) {
                case 1:
                    library.displayBooks();
                    break;
                case 2:
                    System.out.print("Enter user name: ");
                    String userName = scanner.nextLine();
                    User newUser = new User(userName);
                    library.registerUser(newUser);
                    System.out.println("User " + userName + " registered.");
                    break;
                case 3:
                    System.out.print("Enter user name: ");
                    String borrowerName = scanner.nextLine();
                    User borrower = findUserByName(library, borrowerName);
                    if (borrower != null) {
                        System.out.print("Enter book title to borrow: ");
                        String bookTitle = scanner.nextLine();
                        Book book = library.findBookByTitle(bookTitle);
                        if (book != null) {
                            borrower.borrowBook(book);
                        } else {
                            System.out.println("Book not found.");
                        }
                    } else {
                        System.out.println("User not found.");
                    }
                    break;
                case 4:
                    System.out.print("Enter user name: ");
                    String returnerName = scanner.nextLine();
                    User returner = findUserByName(library, returnerName);
                    if (returner != null) {
                        System.out.print("Enter book title to return: ");
                        String returnBookTitle = scanner.nextLine();
                        Book returnBook = library.findBookByTitle(returnBookTitle);
                        if (returnBook != null) {
                            returner.returnBook(returnBook);
                        } else {
                            System.out.println("Book not found.");
                        }
                    } else {
                        System.out.println("User not found.");
                    }
                    break;
                case 5:
                    library.displayUsers();
                    break;
                case 6:
                    running = false;
                    System.out.println("Exiting the library system...");
                    break;
                default:
                    System.out.println("Invalid option, try again.");
            }
        }

        scanner.close();
    }

    // Helper method to find a user by name
    private static User findUserByName(Library library, String userName) {
        for (User user : library.getUsers()) {  // Using the getter method here
            if (user.getName().equalsIgnoreCase(userName)) {
                return user;
            }
        }
        return null;
    }
}
