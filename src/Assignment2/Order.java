package Assignment2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private int orderID;
    private Customer customer;
    private List<Product> products;
    private double orderTotal;
    private LocalDateTime orderDate;
    private OrderStatus status;

    public Order(int orderID, Customer customer, List<Product> products) {
        this.orderID = orderID;
        this.customer = customer;
        this.products = new ArrayList<>(products);
        this.orderTotal = customer.calculateTotal();
        this.orderDate = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
    }

    public String generateOrderSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Order Summary:\n");
        summary.append("Order ID: ").append(orderID).append("\n");
        summary.append("Customer: ").append(customer.getName()).append("\n");
        summary.append("Order Date: ").append(orderDate).append("\n");
        summary.append("Status: ").append(status).append("\n");
        summary.append("Products:\n");

        for (Product product : products) {
            summary.append("- ").append(product.getName())
                    .append(" ($").append(String.format("%.2f", product.getPrice()))
                    .append(")\n");
        }

        summary.append("Total: $").append(String.format("%.2f", orderTotal));
        return summary.toString();
    }

    public void updateStatus(OrderStatus newStatus) {
        this.status = newStatus;
    }

    // Getters
    public int getOrderID() { return orderID; }
    public Customer getCustomer() { return customer; }
    public List<Product> getProducts() { return new ArrayList<>(products); }
    public double getOrderTotal() { return orderTotal; }
    public OrderStatus getStatus() { return status; }

}
