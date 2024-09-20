package src;

import enums.PaymentMethod;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.stream.Stream;

class OrdersManagersTest {

    static Stream<IOrdersManager> ordersManagerProvider() {
        return Stream.of(new SOrdersManager(), new OrdersManager());
    }

    private IOrdersManager ordersManager;
    private Order order1;
    private Order order2;


    void setUp() {
        Product product1 = new Product("Product A", 10, 22);
        Product product2 = new Product("Product B", 15, 11);
        var list = new ArrayList<Product>();
        list.add(product1);
        list.add(product2);
        order1 = new Order("id","Jon", list,"CARD");
        list.add(product1);
        order2 = new Order("id2","Mark", list,"CASH");
        ordersManager.addOrder(order1);
        ordersManager.addOrder(order2);
    }

    @ParameterizedTest
    @MethodSource("ordersManagerProvider")
    void testAddOrder(IOrdersManager manager) {
        ordersManager = manager;

        setUp();
        Order newOrder = new Order("id2","Mark", new ArrayList<>(),"CASH");
        ordersManager.addOrder(newOrder);


        List<Order> orders = ordersManager.getOrders();


        assertEquals(3, orders.size());
        assertTrue(orders.contains(newOrder));
    }

    @ParameterizedTest
    @MethodSource("ordersManagerProvider")
    void testRemoveOrder(IOrdersManager manager) {
        ordersManager = manager;

        setUp();
        ordersManager.removeOrder(order1);


        List<Order> orders = ordersManager.getOrders();


        assertEquals(1, orders.size());
        assertFalse(orders.contains(order1));
    }

    @ParameterizedTest
    @MethodSource("ordersManagerProvider")
    void testGroupByPayment(IOrdersManager manager) {
        ordersManager = manager;

        setUp();


        var res = ordersManager.groupByPayment();


        assertEquals(2, res.size());
        assertEquals(res.keySet(),  Set.of("CASH", "CARD"));
        assertArrayEquals(new Order[]{order1}, res.get("CARD").toArray());
        assertArrayEquals(new Order[]{order2}, res.get("CASH").toArray());
    }

    @ParameterizedTest
    @MethodSource("ordersManagerProvider")
    void testSortByTotalPrice(IOrdersManager manager) {
        ordersManager = manager;

        setUp();


        List<Order> sortedOrders = ordersManager.sortByTotalPrice();


        assertEquals(order2, sortedOrders.get(1));
        assertEquals(order1, sortedOrders.get(0));
    }

    @ParameterizedTest
    @MethodSource("ordersManagerProvider")
    void testMostExpensiveOrder(IOrdersManager manager) {
        ordersManager = manager;

        setUp();


        Optional<Order> mostExpensiveOrder = ordersManager.mostExpensiveOrder();


        assertTrue(mostExpensiveOrder.isPresent());
        assertEquals(order2, mostExpensiveOrder.get());
    }

    @ParameterizedTest
    @MethodSource("ordersManagerProvider")
    void testCountSoldProducts(IOrdersManager manager) {
        ordersManager = manager;

        setUp();


        Map<String, Integer> soldProducts = ordersManager.countSoldProducts();


        assertEquals(2, soldProducts.size());
    }
}

