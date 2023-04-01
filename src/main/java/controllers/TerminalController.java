package controllers;

import models.Menu;
import models.OrderItem;
import services.MenuService;
import services.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class TerminalController {
    protected  OrderService orderService;
    protected MenuService menuService;
    public TerminalController(MenuService menuService, OrderService orderService) {
        this.menuService = menuService;
        this.orderService = orderService;
    }

    public void printMenu() {
        menuService.printMenu();
    }

    public void loadAppInteraction() {
        // Show select options (Order, Check order)
        while (true) {
            Scanner sc = new Scanner(System.in);
            int i = 0;
            while (i != 1 && i != 2) {
                System.out.println("Press 1 to check or 2 to create an order:");
                i = sc.nextInt();
            }

            if (i == 1) {
                // Read the code of the order from console
                // Try to find the order in database and print the order details in console
                while (true) {
                    System.out.println("Please enter the order code or -1 to exit:");
                    int orderId = sc.nextInt();
                    if (orderId == -1)
                        break;
                    orderService.findAndPrintOrderDetails(orderId);
                }
            }
            else {
                // Create the order list with inputs from the user asking for product id and quantity
                // Append each value inserted in the order list until the user preses 10
                // Save the order in database
                // Print the order code in the console
                List<OrderItem> cart = new ArrayList<>();
                while (true) {
                    System.out.println("Please enter 1 to exit, 2 to insert order item or 3 to finish the order:");
                    int action = sc.nextInt();

                    if (action == 1) {
                        break;
                    }

                    if (action == 2) {
                        OrderItem item = new OrderItem();

                        // Get product id from user and set price and description
                        while(true) {
                            System.out.println("Please insert the product id:");
                            int productId = sc.nextInt();
                            Optional<Menu> menu = menuService.getById(productId);
                            if(menu.isPresent()) {
                                item.setProductName(menu.get().getName());
                                item.setProductPrice(menu.get().getPrice());
                                break;
                            }
                            else {
                                System.out.printf("Product with id: %s not found!%n", productId);
                                menuService.printMenu();
                            }
                        }

                        // Get quantity
                        while(true) {
                            System.out.println("Please insert the quantity:");
                            int quantity = sc.nextInt();
                            if (quantity > 0) {
                                item.setQuantity(quantity);
                                break;
                            }
                        }
                        cart.add(item);
                    }

                    if (action == 3) {
                        if (cart.size() == 0) {
                            System.out.println("No items added to cart");
                            break;
                        }
                        // Save cart to database
                        int orderId = orderService.createOrder(cart);
                        System.out.printf("Order created. Code: %s%n", orderId);
                    }
                }
            }
        }
    }
}
