package services;

import dao.MenuDao;
import dao.OrderDao;
import dao.OrderItemDao;
import models.Order;
import models.OrderItem;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class OrderService {
    protected OrderDao orderDao;
    protected MenuDao menuDao;
    protected OrderItemDao orderItemDao;

    public OrderService(OrderDao orderDao, MenuDao menuDao, OrderItemDao orderItemDao) {
        this.orderDao = orderDao;
        this.menuDao = menuDao;
        this.orderItemDao = orderItemDao;
    }

    public Optional<Order> findOrderByCode(int id) {
        return orderDao.getOrderById(id);
    }

    public boolean findAndPrintOrderDetails(int id) {
        Optional<Order> dbItem =  orderDao.getOrderById(id);
        if (dbItem.isPresent()) {
            System.out.printf("################ Order %s #################%n", id);
            dbItem.get().getItems().forEach(i -> System.out.printf("%s - %s x %s -> %s Lek%n", i.getProductName(), i.getQuantity(), i.getProductPrice(), i.getQuantity() * i.getProductPrice()));
            System.out.printf("Total: %s Lek%n", dbItem.get().getTotal());
            System.out.printf("###########################################%n");
            return true;
        }
        else {
            System.out.printf("################ Order %s #################%n", id);
            System.out.println("Order not found!");
            System.out.printf("###########################################%n");
            return false;
        }
    }

    public int createOrder(List<OrderItem> cart) {
        Order order = new Order();
        order.setTotal(cart.stream().map(o -> o.getQuantity() * o.getProductPrice()).reduce((double) 0, Double::sum));
        order.setDateCreated(Instant.now());
        order = orderDao.create(order);
        Order finalOrder = order;
        cart.forEach(o -> {
            o.setOrderId(finalOrder.getId());
            orderItemDao.create(o);
        });

        return finalOrder.getId();
    }
}
