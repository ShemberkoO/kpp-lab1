package src;

public class Product {
    private String name;
    private int quantity;
    private double priceForOne;


    public Product(String name, int quantity, double priceForOne) {
        this.name = name;
        this.quantity = quantity;
        this.priceForOne = priceForOne;
    }

    @Override
    public String toString() {
        return String.format("Product{name='%s', quantity=%d, priceForOne=%.2f, totalPrice=%.2f}\n",
                name, quantity, priceForOne, getTotalPrice());
    }

    public double getTotalPrice() {
        return quantity * priceForOne;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPriceForOne() {
        return priceForOne;
    }

    public void setPriceForOne(double priceForOne) {
        this.priceForOne = priceForOne;
    }
}
