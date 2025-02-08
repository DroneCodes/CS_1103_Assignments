package Assignment2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * In this assignment, you will create a simple e-commerce system for an online store using Java. The system allows customers to browse products, add them to a shopping cart, and place orders. The focus is on organizing the code using Java packages and the import statement for better encapsulation.
 *
 *
 * Scenario:You are tasked with building a simple e-commerce system for an online store. This system should allow customers to browse products, add them to a shopping cart, and place orders. To ensure proper organization and encapsulation, you will be using Java packages and theimport statement.
 *
 * Requirements:
 *
 * Create a Java package named com.ecommerce to encapsulate all classes related to the e-commerce system.
 *
 * Inside the com.ecommerce package, create the following classes:
 * a. Product class: This class should represent a product available for purchase. Include attributes like productID,name,price, and any other relevant fields. Implement the necessary constructors, getters, setters, and any other methods for product-related operations.
 *
 * b. Customer class: This class should represent a customer with attributes like customerID,name, and a shopping cart. Implement methods to add and remove products from the shopping cart, calculate the total cost, and place orders.
 * Create a package named com.ecommerce.orders for managing orders.
 *
 * Inside the com.ecommerce.orders package, create the following classes:
 *
 * a. Orderclass: This class should represent an order placed by a customer. Include attributes like orderID,customer,products, and the order total. Implement methods to generate order summaries, update order status, and manage order information.
 * In the main program (outside of packages), demonstrate the use of packages and theimport statement by:
 * a. Creating instances of products, customers, and orders.
 *
 * b. Allowing customers to browse products, add them to their shopping cart, and place orders.
 *
 * c. Displaying information about products, customers, and orders.
 * Make sure to import the necessary classes from the com.ecommerce and com.ecommerce.orders packages into the main program.
 *
 * Use appropriate access modifiers to ensure proper encapsulation and data hiding.
 */

public class assignment2 {

    private static List<Product> inventory = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Initialize inventory
        setupInventory();

        System.out.println("Welcome to the E-commerce System!");
        System.out.print("Please enter your name: ");
        String customerName = scanner.nextLine();

        Customer customer = new Customer(1, customerName);
        boolean running = true;

        while (running) {
            try {
                displayMenu();
                int choice = getIntInput("Enter your choice: ");

                switch (choice) {
                    case 1:
                        displayProducts();
                        break;
                    case 2:
                        addToCart(customer);
                        break;
                    case 3:
                        viewCart(customer);
                        break;
                    case 4:
                        removeFromCart(customer);
                        break;
                    case 5:
                        placeOrder(customer);
                        break;
                    case 6:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
        System.out.println("Thank you for shopping with us!");
    }

    private static void setupInventory() {
        inventory.add(new Product(1, "Laptop", 999.99, 5));
        inventory.add(new Product(2, "Smartphone", 599.99, 10));
        inventory.add(new Product(3, "Tablet", 299.99, 8));
        inventory.add(new Product(4, "Headphones", 99.99, 15));
        inventory.add(new Product(5, "Smartwatch", 199.99, 12));
    }

    private static void displayMenu() {
        System.out.println("\n=== Menu ===");
        System.out.println("1. Browse Products");
        System.out.println("2. Add Product to Cart");
        System.out.println("3. View Cart");
        System.out.println("4. Remove Product from Cart");
        System.out.println("5. Place Order");
        System.out.println("6. Exit");
    }

    private static void displayProducts() {
        System.out.println("\nAvailable Products:");
        for (Product product : inventory) {
            System.out.println(product);
        }
    }

    private static void addToCart(Customer customer) {
        displayProducts();
        int productId = getIntInput("Enter the Product ID to add to cart (0 to cancel): ");

        if (productId == 0) return;

        Product selectedProduct = findProduct(productId);
        if (selectedProduct != null) {
            try {
                customer.addToCart(selectedProduct);
                System.out.println(selectedProduct.getName() + " added to cart.");
            } catch (IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Product not found.");
        }
    }

    private static void viewCart(Customer customer) {
        List<Product> cart = customer.getShoppingCart();
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("\nYour Shopping Cart:");
        for (Product product : cart) {
            System.out.println(product);
        }
        System.out.printf("Total: $%.2f%n", customer.calculateTotal());
    }

    private static void removeFromCart(Customer customer) {
        List<Product> cart = customer.getShoppingCart();
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("\nYour Shopping Cart:");
        for (Product product : cart) {
            System.out.println(product);
        }

        int productId = getIntInput("Enter the Product ID to remove from cart (0 to cancel): ");
        if (productId == 0) return;

        Product selectedProduct = findProductInCart(productId, cart);
        if (selectedProduct != null) {
            customer.removeFromCart(selectedProduct);
            System.out.println(selectedProduct.getName() + " removed from cart.");
        } else {
            System.out.println("Product not found in cart.");
        }
    }

    private static void placeOrder(Customer customer) {
        if (customer.getShoppingCart().isEmpty()) {
            System.out.println("Your cart is empty. Cannot place order.");
            return;
        }

        Order order = new Order(generateOrderId(), customer, customer.getShoppingCart());
        System.out.println("\n" + order.generateOrderSummary());

        System.out.print("Confirm order (y/n)? ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("y")) {
            order.updateStatus(OrderStatus.PROCESSING);
            customer.clearCart();
            System.out.println("Order placed successfully!");
        } else {
            System.out.println("Order cancelled.");
        }
    }

    private static Product findProduct(int productId) {
        return inventory.stream()
                .filter(p -> p.getProductID() == productId)
                .findFirst()
                .orElse(null);
    }

    private static Product findProductInCart(int productId, List<Product> cart) {
        return cart.stream()
                .filter(p -> p.getProductID() == productId)
                .findFirst()
                .orElse(null);
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static int generateOrderId() {
        return (int) (Math.random() * 10000) + 1;
    }
}
