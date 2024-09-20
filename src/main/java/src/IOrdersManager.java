package src;

import java.io.IOException;
import java.util.*;

public interface IOrdersManager {
     List<Order> getOrders();

     void addOrder(Order order);
     void removeOrder(Order order);

    void loadOrdersFromFile(String filePath) throws IOException;

    Map<String, List<Order>> groupByPayment();
    Map<String, List<Order>> groupByCustomer();
    Map<String, Integer> countSoldProducts();
    List<Order> sortByTotalPrice();
    String SUniqProductsList();
    Optional<Order> mostExpensiveOrder();
}

