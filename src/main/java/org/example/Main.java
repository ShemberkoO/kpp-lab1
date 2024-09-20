package org.example;


import src.IOrdersManager;
import src.SOrdersManager;
import src.OrdersManager;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Select Class:\n 1 - with StreamAPI\n other - without\n");
        Scanner scanner = new Scanner(System.in);

        IOrdersManager manager = null;
        // Зчитуємо наступний рядок з консолі
        String input_type = scanner.nextLine();

        // Перевіряємо, чи це символ '1'
        if (input_type.equals("1")) {
            System.out.println("Selected class with StreamAPI");
            manager = new SOrdersManager();
        } else {
            System.out.println("Selected class without StreamAPI");
            manager = new OrdersManager();
        }
        String message = "Enter sym to action:\n" +
                "1 - Scan from data.json\n" +
                "2 - Add  Order\n" +
                "3 - Remove Order\n" +
                "4 - Group by Payment\n" +
                "5 - Group by Customer\n" +
                "6 - Count Sold\n" +
                "7 - Sort by Total Price\n" +
                "8 - Get list of Products\n" +
                "9 - Select most expensive Order\n" +
                "10 - Print Order\n" +
                "11 - Exit\n";

        while (true) {
            System.out.println(message);
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    try{
                        manager.loadOrdersFromFile("src/main/resources/data.json");
                        System.out.printf("Scanned ");
                    }catch (Exception e){
                        System.out.printf("Can't scan ");
                    }
                    break;
                case "2":
                    System.out.println("Adding Order...");
                    break;
                case "3":
                    System.out.println("Removing Order...");
                    break;
                case "4":
                    System.out.println(manager.groupByPayment());
                    break;
                case "5":
                    System.out.println(manager.groupByCustomer());
                    break;
                case "6":
                    System.out.println(manager.countSoldProducts());
                    break;
                case "7":
                    System.out.println(manager.sortByTotalPrice());
                    break;
                case "8":
                    System.out.println(manager.SUniqProductsList());
                    break;
                case "9":
                    if(manager.mostExpensiveOrder().isPresent()){
                        System.out.println(manager.mostExpensiveOrder().get());
                    }else{
                        System.out.println("No most expensive order found");
                    }
                    break;
                case "10":
                    System.out.println("Orders:");
                    System.out.println(manager.getOrders());
                   break;
                case "11":
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}