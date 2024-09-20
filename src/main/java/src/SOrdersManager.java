package src;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class SOrdersManager implements IOrdersManager {
    private List<Order> orders;

    public void addOrder(Order order) {
        orders.add(order);
    }
     public void removeOrder(Order order) {
        orders.remove(order);
    }
    public SOrdersManager() {
        orders = new ArrayList<>();
    }

    @Override
    public String toString() {
        return orders.stream().map(Order::toString).collect(Collectors.joining(" "));
    }

    @Override
    public List<Order> getOrders() {
        return orders;
    }
    @Override
    public Map<String, List<Order>> groupByPayment() {
        return orders.stream().collect(Collectors.groupingBy(Order::getPaymentMethod));
    }

    @Override
    public void loadOrdersFromFile(String filePath) throws IOException {
        orders.clear();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Order.class, new OrderDeserializer())
                .create();

        Type orderListType = new TypeToken<List<Order>>(){}.getType();

        try (FileReader reader = new FileReader(filePath)) {
            orders = gson.fromJson(reader, orderListType);
        }
    }

    @Override
    public Map<String, List<Order>> groupByCustomer() {
        return orders.stream().collect(Collectors.groupingBy(Order::getCustomerName));
    }

    @Override
    public Map<String, Integer> countSoldProducts() {
        List<Product> productsList = orders.stream()
                .map(Order::getProducts)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        Map<String, Integer> productCountByName = productsList.stream()
                .collect(Collectors.groupingBy(
                        Product::getName,
                        Collectors.summingInt(product -> product.getQuantity())
                ));

        return productCountByName;
    }

    @Override
    public List<Order> sortByTotalPrice() {
        return orders.stream()
                .sorted(Comparator.comparingDouble(Order::getTotalPrice))
                .collect(Collectors.toList());
    }

    @Override
    public String SUniqProductsList() {
        return countSoldProducts().entrySet().stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public Optional<Order> mostExpensiveOrder() {
       return orders.stream().max(Comparator.comparingDouble(Order::getTotalPrice));
    }
}
