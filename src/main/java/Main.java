import controllers.TerminalController;
import dao.MenuDao;
import dao.OrderDao;
import dao.OrderItemDao;
import models.Menu;
import models.Order;
import models.OrderItem;
import services.MenuService;
import services.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Init dao and services
        MenuDao menuDao = new MenuDao();
        OrderDao orderDao = new OrderDao();
        OrderItemDao orderItemDao = new OrderItemDao();

        MenuService menuService = new MenuService(new MenuDao());
        OrderService orderService = new OrderService(orderDao, menuDao, orderItemDao);

        // Init app interaction controller
        TerminalController terminalController = new TerminalController(menuService, orderService);

        // Init and print menu
        menuService.initMenu();

        // Load the application interaction
        terminalController.printMenu();
        terminalController.loadAppInteraction();
    }
}
