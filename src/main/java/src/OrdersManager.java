package src;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class OrdersManager implements  IOrdersManager {

    private List<Order> orders;


    public void addOrder(Order order) {
        orders.add(order);
    }
    public void removeOrder(Order order) {
        orders.remove(order);
    }

    public OrdersManager() {
        orders = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (Order order : orders) {
            result.append(order.toString()).append(" ");
        }
        return result.toString().trim();
    }
    @Override
    public List<Order> getOrders() {
        return orders;
    }
    @Override
    public Map<String, List<Order>> groupByPayment() {
        Map<String, List<Order>> result = new HashMap<>();

        for (Order order : orders) {
            String paymentMethod = order.getPaymentMethod();
            if (!result.containsKey(paymentMethod)) {
                result.put(paymentMethod, new ArrayList<>());
            }
            result.get(paymentMethod).add(order);
        }
        return result;
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
        Map<String, List<Order>> result = new HashMap<>();

        for (Order order : orders) {
            String customerName = order.getCustomerName();
            if (!result.containsKey(customerName)) {
                result.put(customerName, new ArrayList<>());
            }
            result.get(customerName).add(order);
        }
        return result;
    }

    @Override
    public Map<String, Integer> countSoldProducts() {
        Map<String, Integer> productCountByName = new HashMap<>();

        for (Order order : orders) {
            for (Product product : order.getProducts()) {
                String productName = product.getName();
                int quantity = product.getQuantity();

                productCountByName
                        .put(productName, productCountByName.getOrDefault(productName, 0) + quantity);
            }
        }
        return productCountByName;
    }

    @Override
    public Optional<Order> mostExpensiveOrder() {
        if (orders.isEmpty()) {
            return Optional.empty();
        }

        Order mostExpensive = orders.get(0);

        for (Order order : orders) {
            if (order.getTotalPrice() > mostExpensive.getTotalPrice()) {
                mostExpensive = order;
            }
        }
        return Optional.of(mostExpensive);
    }

    @Override
    public List<Order> sortByTotalPrice() {
        List<Order> sortedOrders = new ArrayList<>(orders);
        sortedOrders.sort(new OrderTotalPriceComparator());
        return sortedOrders;
    }

    @Override
    public String SUniqProductsList () {
        var soldProductsList = countSoldProducts();
        StringBuilder resString = new StringBuilder();
        soldProductsList.forEach((name, num) -> resString.append(name));
        return resString.toString();
    }

    public class OrderTotalPriceComparator implements Comparator<Order> {
        @Override
        public int compare(Order o1, Order o2) {
            return Double.compare(o1.getTotalPrice(), o2.getTotalPrice());
        }
    }
}
