package Assignment4;

import java.util.Scanner;

public class LibraryCatalogApp {
    private static Scanner scanner = new Scanner(System.in);
    private static Catalog<LibraryItem<?>> catalog = new Catalog<>("Main Library Catalog");

    public static void main(String[] args) {
        boolean running = true;

        // Add some sample items to start with
        addSampleItems();

        System.out.println("Welcome to the Generic Library Catalog System!");

        while (running) {
            displayMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    addItem();
                    break;
                case 2:
                    removeItem();
                    break;
                case 3:
                    displayAllItems();
                    break;
                case 4:
                    searchItem();
                    break;
                case 5:
                    running = false;
                    System.out.println("Thank you for using the Library Catalog. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n--- " + catalog.getCatalogName() + " ---");
        System.out.println("1. Add new item");
        System.out.println("2. Remove item");
        System.out.println("3. Display all items");
        System.out.println("4. Search for an item");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Invalid choice
        }
    }

    private static void addItem() {
        System.out.println("\n--- Add New Item ---");
        System.out.println("Select item type:");
        System.out.println("1. Book");
        System.out.println("2. DVD");
        System.out.println("3. Magazine");
        System.out.print("Enter your choice: ");

        int typeChoice = getUserChoice();

        // Common details for all item types
        System.out.print("Enter Item ID: ");
        String itemID = scanner.nextLine();
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Author/Creator: ");
        String author = scanner.nextLine();

        LibraryItem<?> newItem = null;

        switch (typeChoice) {
            case 1: // Book
                System.out.print("Enter Page Count: ");
                int pages = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter Genre: ");
                String genre = scanner.nextLine();
                System.out.print("Enter ISBN: ");
                String isbn = scanner.nextLine();

                Book book = new Book(pages, genre, isbn);
                newItem = new LibraryItem<>(title, author, itemID, book);
                break;

            case 2: // DVD
                System.out.print("Enter Runtime (minutes): ");
                int runtime = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter Director: ");
                String director = scanner.nextLine();
                System.out.print("Enter Rating: ");
                String rating = scanner.nextLine();

                DVD dvd = new DVD(runtime, director, rating);
                newItem = new LibraryItem<>(title, author, itemID, dvd);
                break;

            case 3: // Magazine
                System.out.print("Enter Issue Date: ");
                String issueDate = scanner.nextLine();
                System.out.print("Enter Publisher: ");
                String publisher = scanner.nextLine();
                System.out.print("Enter Category: ");
                String category = scanner.nextLine();

                Magazine magazine = new Magazine(issueDate, publisher, category);
                newItem = new LibraryItem<>(title, author, itemID, magazine);
                break;

            default:
                System.out.println("Invalid item type. Item not added.");
                return;
        }

        if (catalog.addItem(newItem)) {
            System.out.println("Item added successfully!");
        } else {
            System.out.println("Error: Item with ID " + itemID + " already exists in the catalog.");
        }
    }

    private static void removeItem() {
        System.out.print("Enter the ID of the item to remove: ");
        String itemID = scanner.nextLine();

        try {
            LibraryItem<?> removedItem = catalog.removeItem(itemID);
            System.out.println("Item removed successfully: " + removedItem.getTitle());
        } catch (ItemNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void displayAllItems() {
        System.out.println("\n--- All Items in Catalog ---");

        if (catalog.getItemCount() == 0) {
            System.out.println("The catalog is empty.");
            return;
        }

        for (LibraryItem<?> item : catalog.getAllItems()) {
            System.out.println(item);
        }

        System.out.println("Total items: " + catalog.getItemCount());
    }

    private static void searchItem() {
        System.out.print("Enter the ID of the item to search for: ");
        String itemID = scanner.nextLine();

        try {
            LibraryItem<?> item = catalog.getItem(itemID);
            System.out.println("\n--- Item Details ---");
            System.out.println(item);

            // Display content-specific details
            Object content = item.getContent();
            if (content != null) {
                System.out.println("Content details: " + content);
            }
        } catch (ItemNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void addSampleItems() {
        // Add a sample book
        Book book = new Book(320, "Fiction", "978-3-16-148410-0");
        LibraryItem<Book> bookItem = new LibraryItem<>("The Great Adventure", "John Doe", "B001", book);
        catalog.addItem(bookItem);

        // Add a sample DVD
        DVD dvd = new DVD(118, "Jane Smith", "PG-13");
        LibraryItem<DVD> dvdItem = new LibraryItem<>("Awesome Movie", "Jane Smith", "D001", dvd);
        catalog.addItem(dvdItem);

        // Add a sample magazine
        Magazine magazine = new Magazine("January 2023", "Tech Publishing", "Technology");
        LibraryItem<Magazine> magazineItem = new LibraryItem<>("Tech Today", "Various Authors", "M001", magazine);
        catalog.addItem(magazineItem);
    }
}