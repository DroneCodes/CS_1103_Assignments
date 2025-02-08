package Assignment2;

public class Product {
    private int productID;
    private String name;
    private double price;
    private int stockQuantity;

    public Product(int productID, String name, double price, int stockQuantity) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    // Getters and setters
    public int getProductID() { return productID; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }

    public void setStockQuantity(int quantity) {
        if (quantity >= 0) {
            this.stockQuantity = quantity;
        } else {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
    }

    @Override
    public String toString() {
        return String.format("Product ID: %d, Name: %s, Price: $%.2f, Stock: %d",
                productID, name, price, stockQuantity);
    }
}
