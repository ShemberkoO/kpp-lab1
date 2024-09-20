package src;
import enums.PaymentMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Order {
    private String orderID;
    private String customerName;
    private List<Product> products;
    private PaymentMethod paymentMethod;
    public Order(String orderID, String customerName, List<Product> products, String paymentMethod) {
        this.orderID = orderID;
        this.customerName = customerName;
        this.products = new ArrayList<>(products);
        this.paymentMethod = PaymentMethod.fromString(paymentMethod);
    }


    public String toString() {
        var productList = products.stream().map(Product::toString).collect(Collectors.joining(" "));

        return String
                .format("Order{ID='%s', customerName=%s, products:\n[\n %s] \n, paymentMethod=%s, totalPrice=%.2f}\n",
                orderID, customerName, productList ,  paymentMethod.toString(), getTotalPrice());
    }

    public void AddProduct(Product product) {
        this.products.add(product);
    }
    public void RemoveProduct(Product product) {
        this.products.remove(product);
    }

    public String getPaymentMethod() {
        return paymentMethod.toString();
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getTotalPrice() {
        return products.stream().mapToDouble(Product::getTotalPrice).sum();
    }
}