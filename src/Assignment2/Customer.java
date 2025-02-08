package Assignment2;
import java.util.ArrayList;
import java.util.List;

public class Customer {

    private int customerID;
    private String name;
    private List<Product> shoppingCart;

    public Customer(int customerID, String name) {
        this.customerID = customerID;
        this.name = name;
        this.shoppingCart = new ArrayList<>();
    }

    public void addToCart(Product product) {
        if (product.getStockQuantity() > 0) {
            shoppingCart.add(product);
            product.setStockQuantity(product.getStockQuantity() - 1);
        } else {
            throw new IllegalStateException("Product out of stock: " + product.getName());
        }
    }

    public void removeFromCart(Product product) {
        if (shoppingCart.remove(product)) {
            product.setStockQuantity(product.getStockQuantity() + 1);
        }
    }

    public double calculateTotal() {
        return shoppingCart.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    // Getters
    public int getCustomerID() { return customerID; }
    public String getName() { return name; }
    public List<Product> getShoppingCart() { return new ArrayList<>(shoppingCart); }

    public void clearCart() {
        shoppingCart.clear();
    }

}
